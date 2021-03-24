create table USTERKI
(
    DATA_ZGLOSZENIA        DATE default TO_DATE(TO_CHAR(CURRENT_DATE, 'dd/mm/yyyy'), 'dd/mm/yyyy') not null,
    DATA_NAPRAWY           DATE,
    PRACOWNIK_WPISUJACY    NUMBER                                                                  not null
        constraint USTERKI_PRACOWNIK_FK
            references PRACOWNICY_WYPOZYCZALNI,
    ID_ROWERU              NUMBER                                                                  not null
        constraint USTERKI_ROWER_FK
            references ROWERY,
    OPIS                   VARCHAR2(100 char),
    PRACOWNIK_NAPRAWIAJACY NUMBER,
    constraint USTERKA_PK
        primary key (ID_ROWERU, PRACOWNIK_WPISUJACY, DATA_ZGLOSZENIA),
    constraint CHK_USTERKI_DATY
        check (data_naprawy >= data_zgloszenia OR data_naprawy IS NULL)
)
/

