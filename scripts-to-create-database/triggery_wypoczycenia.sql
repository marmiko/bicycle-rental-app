create trigger NOWE_WYPOZYCZENIE
    before insert or update
    on WYPOZYCZENIA
    for each row
DECLARE
        vCheckCount NUMBER;
        vCheckPrzeglad NUMBER;
        vDataPrzegladu PRZEGLADY_TECHNICZNE.DATA_WYKONANIA%TYPE;
        vStatus ROWERY.STATUS%TYPE;
        vStatusAkc AKCESORIA.STATUS%TYPE;
    begin

      SELECT COUNT(*) INTO vCheckCount FROM ROWERY WHERE ID_ROWERU=:NEW.ID_ROWERU;

        IF vCheckCount = 0 then
            RAISE_APPLICATION_ERROR(-20002, 'Podany rower nie istnieje.');
        end if;

        if :OLD.ID_ROWERU IS NULL or :NEW.ID_ROWERU != :OLD.ID_ROWERU or
           (:OLD.DATA_ZWROTU is not null and :NEW.DATA_ZWROTU is null) then
            SELECT STATUS INTO vStatus FROM ROWERY WHERE ID_ROWERU = :NEW.ID_ROWERU;
            IF vStatus != 'wolny' THEN
                RAISE_APPLICATION_ERROR(-20001, 'Rower jest obecnie wypożyczony lub niezdatny do użytku.');
            end if;
        end if;
        if :OLD.ID_ROWERU IS NULL or :NEW.ID_ROWERU != :OLD.ID_ROWERU then
            SELECT COUNT(*) INTO vCheckPrzeglad FROM PRZEGLADY_TECHNICZNE WHERE ROWER=:NEW.ID_ROWERU;
            IF vCheckPrzeglad = 0 then
                RAISE_APPLICATION_ERROR(-20002, 'Rower nie ma ważnego przegladu technicznego.');
            end if;
            SELECT MAX(DATA_WYKONANIA) INTO vDataPrzegladu FROM PRZEGLADY_TECHNICZNE WHERE ROWER=:NEW.ID_ROWERU;
            IF vDataPrzegladu IS NULL OR (CURRENT_DATE - vDataPrzegladu) >180  THEN
                RAISE_APPLICATION_ERROR(-20002, 'Rower nie ma ważnego przegladu technicznego.');
            end if;

        end if;


        IF :NEW.ID_AKCESORIUM IS NOT NULL and (:OLD.ID_AKCESORIUM is NULL or :NEW.ID_AKCESORIUM != :OLD.ID_AKCESORIUM) THEN
            SELECT COUNT(*) INTO vCheckCount FROM AKCESORIA WHERE ID_AKCESORIUM=:NEW.ID_AKCESORIUM;
            IF vCheckCount = 0 then
                RAISE_APPLICATION_ERROR(-20002, 'Podane akcesorium nie istnieje.');
            end if;

            SELECT STATUS INTO vStatusAkc FROM AKCESORIA WHERE ID_AKCESORIUM = :NEW.ID_AKCESORIUM;

            IF vStatusAkc != 'wolne' THEN
                RAISE_APPLICATION_ERROR(-20001, 'Akcesorium jest obecnie wypożyczone lub niezdatne do użytku.');
            END IF;
        END IF;

    end;
/

create trigger USUN_WYPOZYCZENIE
    after delete
    on WYPOZYCZENIA
    for each row
BEGIN
        UPDATE ROWERY SET STATUS = 'wolny' WHERE ID_ROWERU = :OLD.ID_ROWERU;
        if :OLD.ID_AKCESORIUM is not null then
            update AKCESORIA set STATUS = 'wolne' where ID_AKCESORIUM = :OLD.ID_AKCESORIUM;
        end if;
    END;
/

create trigger WYPOZYCZANIE
    after insert
    on WYPOZYCZENIA
    for each row
BEGIN
        UPDATE ROWERY SET STATUS = 'wypożyczony' WHERE ID_ROWERU = :NEW.ID_ROWERU;
        if :NEW.ID_AKCESORIUM is not null then
            update AKCESORIA set STATUS = 'wypożyczone' where ID_AKCESORIUM = :NEW.ID_AKCESORIUM;
        end if;
    END;
/



create trigger ZWRACANIE
    after update
    on WYPOZYCZENIA
    for each row
BEGIN
        IF :NEW.DATA_ZWROTU IS NOT NULL AND :OLD.DATA_ZWROTU IS NULL THEN
            UPDATE ROWERY SET STATUS = 'wolny' WHERE ID_ROWERU = :OLD.ID_ROWERU;
            if :OLD.ID_AKCESORIUM is not null then
                update AKCESORIA set STATUS = 'wolne' where ID_AKCESORIUM = :OLD.ID_AKCESORIUM;
        end if;
        END IF;
    END;
/
