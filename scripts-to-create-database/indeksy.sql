
create index ROWERY_STATUS_NAZWA
    on ROWERY (STATUS, MODEL_ROWERU_NAZWA)
/

create index AKCESORIA_STATUS_NAZWA
    on AKCESORIA (STATUS, RODZAJ_AKCESORIUM_NAZWA)
/