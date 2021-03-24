create trigger USUWANIE_ROWERU
    before delete
    on ROWERY
    for each row
declare
        vCount number;
    begin
        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is null and WYPOZYCZENIA.ID_ROWERU=:OLD.ID_ROWERU;
        if vCount != 0 then
            raise_application_error(-20003, 'Rower należy do aktywnego wypożyczenia.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is null and SUBSKRYBCJE.ID_ROWERU=:OLD.ID_ROWERU;
        if vCount != 0 then
            raise_application_error(-20003, 'Rower należy do aktywnej subskrybcji');
        end if;

        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is not null and WYPOZYCZENIA.ID_ROWERU=:OLD.ID_ROWERU;
        if vCount != 0 then
            raise_application_error(-20003, 'Rower należy do przeszłego wypożyczenia. Usuń je najpierw.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is not null and SUBSKRYBCJE.ID_ROWERU=:OLD.ID_ROWERU;
        if vCount != 0 then
            raise_application_error(-20003, 'Rower należy do przeszłej subskrybcji. Usuń ją najpierw.');
        end if;

    end;
/

create trigger USUWANIE_USTEREK_PRZEGLADOW_ROWERU
    after delete
    on ROWERY
    for each row
begin
        delete from USTERKI where USTERKI.ID_ROWERU=:OLD.ID_ROWERU;
        delete from PRZEGLADY_TECHNICZNE where ROWER=:OLD.ID_ROWERU;
    end;
/