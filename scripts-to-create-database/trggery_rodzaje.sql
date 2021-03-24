create trigger USUWANIE_AKCESORIUM
    before delete on AKCESORIA
    for each row
    declare
        vCount number;
    begin
        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is null and WYPOZYCZENIA.ID_AKCESORIUM=:OLD.ID_AKCESORIUM;
        if vCount != 0 then
            raise_application_error(-20003, 'Akcesorium należy do aktywnego wypożyczenia.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is null and SUBSKRYBCJE.ID_AKCESORIUM=:OLD.ID_AKCESORIUM;
        if vCount != 0 then
            raise_application_error(-20003, 'Akcesorium należy do aktywnej subskrybcji');
        end if;

    end;