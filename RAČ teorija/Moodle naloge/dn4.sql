-- (Že postavljeno)
mysql -u root -p;

CREATE DATABASE vlak;
USE vlak;

CREATE TABLE Postaja(
    Naziv_postaje varchar(60) PRIMARY KEY,
    Postajna_stavba char(2) NOT NULL,
    Prodaja_vozovnic char(2) NOT NULL,
    Cakalnica char(2) NOT NULL
);

CREATE TABLE Voznja_vlaka(
    Oznaka_voznje varchar(10) PRIMARY KEY,
    Datum_odhoda date NOT NULL,
    Cas_odhoda time(6) NOT NULL,
    Datum_prihoda date NOT NULL,
    Cas_prihoda time(6) NOT NULL,
    Linija varchar(7) NOT NULL
);

CREATE TABLE Postanek_vlaka(
    Stevilka_postanka bigint(19) PRIMARY KEY,
    Datum_prihoda date NOT NULL,
    Cas_prihoda time(6) NOT NULL,
    Datum_odhoda date NOT NULL,
    Cas_odhoda time(6) NOT NULL,
    Postaja varchar(60) NOT NULL,
    Voznja varchar(10) NOT NULL
);

-- 1. a)

ALTER TABLE Postanek_vlaka
ADD CONSTRAINT fk_postaja FOREIGN KEY (Postaja) REFERENCES Postaja(Naziv_postaje);

ALTER TABLE Postanek_vlaka
ADD CONSTRAINT fk_voznja FOREIGN KEY (Voznja) REFERENCES Voznja_vlaka(Oznaka_voznje);

-- 1. b)

-- če poznate ime tuje omejitve (fk_postaja):
ALTER TABLE Postanek_vlaka DROP FOREIGN KEY fk_postaja;

-- če ne poznate imena, pridobite ga npr. tako:
SHOW CREATE TABLE Postanek_vlaka;

-- 1. c)

ALTER TABLE Voznja_vlaka
ADD COLUMN Sprevodnik integer NOT NULL;


-- 2. naloga

CREATE ROLE sprevodnik;
GRANT SELECT ON * TO sprevodnik;
GRANT UPDATE ON Voznja_vlaka TO sprevodnik;

-- 3. naloga

CREATE USER Zdravko@'%';
GRANT sprevodnik TO Zdravko@'%';

-- 4. naloga

SELECT * FROM mysql.roles_mapping;