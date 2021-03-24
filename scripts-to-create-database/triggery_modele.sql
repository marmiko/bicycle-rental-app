create or replace trigger USUWANIE_MODELU
    before delete on MODELE_ROWEROW
    for each row
    declare
        vCount number;
    begin
        select count(*) into vCount from ROWERY
        where MODEL_ROWERU_NAZWA=:OLD.NAZWA;
        if vCount != 0 then
            raise_application_error(-20003, 'Istnieją reprezentacje modelu w bazie rowerów.');
        end if;

    end;