create or replace function PoliczWolneAkcesoria
    (pRodzaj in varchar2)
    return natural is
    vWolneAkcesoria natural;
begin
    select count(*) into vWolneAkcesoria
    from AKCESORIA where status='wolne' and RODZAJ_AKCESORIUM_NAZWA=pRodzaj;

    return vWolneAkcesoria;
end;
/

create or replace function PoliczWolneRowery
    (pModel in varchar2)
    return natural is
    vWolneRowery natural;
begin
    select count(*) into vWolneRowery
    from ROWERY where status='wolny' and MODEL_ROWERU_NAZWA=pModel;

    return vWolneRowery;
end;
/