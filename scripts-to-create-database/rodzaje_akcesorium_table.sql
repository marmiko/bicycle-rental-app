create table RODZAJE_AKCESORIUM
(
    NAZWA           VARCHAR2(20 char)       not null
        constraint RODZAJE_AKCESORIUM_PK
            primary key,
    CENA_ZA_DZIEN   NUMBER(5, 2)
        constraint CHK_RODZAJE_AKCESORIUM_CENA_ZA_DZIEN
            check (cena_za_dzien >= 0),
    CENA_ZA_MIESIAC NUMBER(5, 2)
        constraint CHK_RODZAJE_AKCESORIUM_CENA_ZA_MIESIAC
            check (cena_za_miesiac >= 0),
    KAUCJA          NUMBER(5, 2) default 20 not null
        constraint CHK_RODZAJE_AKCEOSRIUM_KAUCJA
            check (kaucja >= 0)
)
/

