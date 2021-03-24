create table KLIENCI
(
    NR_DOWODU   VARCHAR2(10 char) not null
        constraint KLIENCI_PK
            primary key,
    IMIE        VARCHAR2(20 char) not null,
    NAZWISKO    VARCHAR2(20 char) not null,
    NR_TELEFONU NUMBER            not null
        constraint CHK_KLIENCI
            check (nr_telefonu > 0)
)
/

