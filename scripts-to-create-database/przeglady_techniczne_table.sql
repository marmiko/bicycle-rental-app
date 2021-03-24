create table PRZEGLADY_TECHNICZNE
(
    DATA_WYKONANIA DATE default TO_DATE(TO_CHAR(CURRENT_DATE, 'dd/mm/yyyy'), 'dd/mm/yyyy') not null,
    OPIS           VARCHAR2(100 char),
    ROWER          NUMBER                                                                  not null,
    PRACOWNIK      NUMBER                                                                  not null
        constraint PRZEGLADY_TECHNICZNE_PRACOWNIK_FK
            references PRACOWNICY_WYPOZYCZALNI,
    constraint PRZEGLADY_TECHNICZNE_PK
        primary key (ROWER, PRACOWNIK, DATA_WYKONANIA)
)
/

create unique index PRZEGLĄDY_TECHNICZNE_PK
    on PRZEGLADY_TECHNICZNE (ROWER, PRACOWNIK, DATA_WYKONANIA)
/