create trigger NOWA_USTERKA
after insert on USTERKI
for each row
begin
    update ROWERY set STATUS='uszkodzony' where ROWERY.ID_ROWERU=:NEW.ID_ROWERU;
end;

create trigger USUN_USTERKE
after delete on USTERKI
for each row
begin
    update ROWERY set STATUS='wolny' where ROWERY.ID_ROWERU=:OLD.ID_ROWERU;
end;

create trigger NAPRAW_USTERKE
after delete on USTERKI
for each row
begin
    if :OLD.DATA_NAPRAWY IS NULL and :NEW.DATA_NAPRAWY is not null then
        update ROWERY set STATUS='wolny' where ROWERY.ID_ROWERU=:OLD.ID_ROWERU;
    end if;
end;

create trigger NAPRAW_USTERKE
after delete on USTERKI
for each row
begin
    if :OLD.DATA_NAPRAWY IS NULL and :NEW.DATA_NAPRAWY is not null then
        update ROWERY set STATUS='wolny' where ROWERY.ID_ROWERU=:OLD.ID_ROWERU;
    end if;
    if :OLD.DATA_NAPRAWY IS not null and :NEW.DATA_NAPRAWY is null then
        update ROWERY set STATUS='uszkodzony' where ROWERY.ID_ROWERU=:OLD.ID_ROWERU;
    end if;
end;