CREATE TABLE izdelek(
    st_izdelka integer AUTO_INCREMENT PRIMARY KEY,
    kategorija varchar(20) NOT NULL,
    naziv_izdelka varchar(40) NOT NULL,
    crtna_koda varchar(20) NOT NULL UNIQUE,
    opis varchar(100),
    na_zalogi_kosov integer NOT NULL,
    opombe varchar(100)
);