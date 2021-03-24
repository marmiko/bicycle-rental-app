create or replace trigger USUWANIE_KLIENTA
    before delete
    on KLIENCI
    for each row
declare
        vCount number;
    begin
        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is null and KLIENT_NR_DOWODU=:OLD.NR_DOWODU;
        if vCount != 0 then
            raise_application_error(-20003, 'Klient posiada aktywne wypożyczenie.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is null and KLIENT_NR_DOWODU=:OLD.NR_DOWODU;
        if vCount != 0 then
            raise_application_error(-20003, 'Klient posiada aktywną subskrybcję.');
        end if;

        select count(*) into vCount from WYPOZYCZENIA
        where DATA_ZWROTU is not null and KLIENT_NR_DOWODU=:OLD.NR_DOWODU;
        if vCount != 0 then
            raise_application_error(-20003, 'Klient posiada przeszłe wypożyczenia. Usuń je najpierw.');
        end if;

        select count(*) into vCount from SUBSKRYBCJE
        where DATA_ZAKONCZENIA is not null and KLIENT_NR_DOWODU=:OLD.NR_DOWODU;
        if vCount != 0 then
            raise_application_error(-20003, 'Klient posiada przeszłe subskrybcje. Usuń je najpierw.');
        end if;

    end;
/

