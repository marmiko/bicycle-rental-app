create trigger USUWANIE_PRACOWNIKA
    before delete
    on PRACOWNICY_WYPOZYCZALNI
    for each row
declare
        vCount number;
    begin
        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is null and WYPOZYCZENIA.ID_PRAC=:OLD.ID_PRAC;
        if vCount != 0 then
            raise_application_error(-20003, 'Pracownik jest przypisany do aktywnego wypożyczenia.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is null and SUBSKRYBCJE.ID_PRAC=:OLD.ID_PRAC;
        if vCount != 0 then
            raise_application_error(-20003, 'Pracownik jest przypisany do aktywnej subskrybcji.');
        end if;

        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is not null and WYPOZYCZENIA.ID_PRAC=:OLD.ID_PRAC;
        if vCount != 0 then
            raise_application_error(-20003, 'Pracownik jest przypisany do przeszłego wypożyczenia. Usuń je najpierw.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is not null and SUBSKRYBCJE.ID_PRAC=:OLD.ID_PRAC;
        if vCount != 0 then
            raise_application_error(-20003, 'Pracownik jest przypisany do przeszłej subskrybcji. Usuń je najpierw.');
        end if;

    end;
/

