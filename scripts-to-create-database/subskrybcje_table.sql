create table SUBSKRYBCJE
(
    DATA_ROZPOCZECIA DATE default TO_DATE(TO_CHAR(CURRENT_DATE, 'dd/mm/yyyy'), 'dd/mm/yyyy') not null,
    KLIENT_NR_DOWODU VARCHAR2(10 char)                                                       not null
        constraint SUBSKRYBCJE_KLIENT_FK
            references KLIENCI,
    ID_PRAC          NUMBER                                                                  not null
        constraint SUBSKRYBCJE_PRACOWNIK_FK
            references PRACOWNICY_WYPOZYCZALNI,
    ID_ROWERU        NUMBER                                                                  not null
        constraint SUBSKRYBCJE_ROWER_FK
            references ROWERY,
    ID_AKCESORIUM    NUMBER
        constraint SUBSKRYBCJE_AKCESORIUM_FK
            references AKCESORIA,
    DATA_ZAKONCZENIA DATE,
    constraint SUBSKRYBCJA_PK
        primary key (ID_ROWERU, KLIENT_NR_DOWODU, ID_PRAC, DATA_ROZPOCZECIA),
    constraint SUBSKRYBCJE_CHK1
        check (DATA_ZAKONCZENIA >= DATA_ROZPOCZECIA)
)
/

