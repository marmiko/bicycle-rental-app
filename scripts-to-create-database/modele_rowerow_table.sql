create table MODELE_ROWEROW
(
    NAZWA           VARCHAR2(20 char) not null
        constraint MODELE_ROWEROW_PK
            primary key,
    TYP             VARCHAR2(20 char) not null,
    ROZMIAR         VARCHAR2(20 char) not null,
    CENA_ZA_DZIEN   NUMBER(5, 2) default 20
        constraint CHK_MODELE_ROWEROW_CENA_ZA_DZIEN
            check (cena_za_dzien >= 0),
    OPIS            VARCHAR2(100 char),
    CENA_ZA_MIESIAC NUMBER(5, 2) default 150
        constraint CHK_MODELE_ROWEROW_CENA_ZA_MIESIAC
            check (cena_za_miesiac >= 0)
)
/