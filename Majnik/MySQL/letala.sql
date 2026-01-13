DROP DATABASE IF EXISTS letala;
CREATE DATABASE letala;
USE letala;

-- =====================================================
-- Tabela: Letalska družba
-- =====================================================
DROP TABLE IF EXISTS Letalska_druzba;
CREATE TABLE Letalska_druzba (
    Ime VARCHAR(30) PRIMARY KEY,
    Naslov VARCHAR(40) NOT NULL,
    Lastnik VARCHAR(20) NOT NULL,
    Spletna_stran VARCHAR(20) NULL
);

-- =====================================================
-- Tabela: letališče
-- =====================================================
DROP TABLE IF EXISTS letalisce;
CREATE TABLE letalisce (
    LetalisceID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Ime VARCHAR(30) NOT NULL,
    drzava VARCHAR(56) NOT NULL,
    longtitude DECIMAL(10,6) NOT NULL,
    latitude DECIMAL(10,6) NOT NULL,
    CONSTRAINT long_mask CHECK (longtitude BETWEEN -180 AND 180),
    CONSTRAINT lat_mask CHECK (latitude BETWEEN -90 AND 90)
);

-- =====================================================
-- Tabela: Uslužbenec
-- =====================================================
DROP TABLE IF EXISTS Usluzbenc;
CREATE TABLE Usluzbenc (
    EMSO BIGINT(13) PRIMARY KEY,
    Letalska_druzba VARCHAR(30) NOT NULL,
    ime VARCHAR(20) NOT NULL,
    priimek VARCHAR(20) NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    vloga VARCHAR(20) NOT NULL,
    CONSTRAINT fk_letalska_druzba_usluzbenec FOREIGN KEY (Letalska_druzba) REFERENCES Letalska_druzba(Ime),
    CONSTRAINT emso_mask CHECK (EMSO REGEXP '^[0-9]{7}50[0-9]{4}$')
);

-- =====================================================
-- Tabela: Letalo
-- =====================================================
DROP TABLE IF EXISTS Letalo;
CREATE TABLE Letalo (
    Registracija VARCHAR(15) PRIMARY KEY,
    Ime VARCHAR(50) NOT NULL,
    Proizvajalec VARCHAR(30) NOT NULL,
    Datum_izdelave DATE NOT NULL,
    Sedezi SMALLINT(5) NOT NULL,
    Nosilnost INT(10) NOT NULL,
    Tip VARCHAR(20) NOT NULL,
    Letalska_druzba VARCHAR(30) NOT NULL,
     CONSTRAINT fk_letalska_druzba_letalo FOREIGN KEY (Letalska_druzba) REFERENCES Letalska_druzba(Ime)
);

-- =====================================================
-- Tabela: Vzdrževanje
-- =====================================================
DROP TABLE IF EXISTS Vzdrzevanje;
CREATE TABLE Vzdrzevanje (
    IDVzdrzevanja INT(10) PRIMARY KEY AUTO_INCREMENT,
    LetaloRegistracija VARCHAR(15) NOT NULL,
    Datum DATE NOT NULL,
    Opis VARCHAR(200) NOT NULL,
    Vrsta VARCHAR(20) NOT NULL,
    CONSTRAINT fk_letalo_vzdrzevanje FOREIGN KEY (LetaloRegistracija) REFERENCES Letalo(Registracija)
);

-- =====================================================
-- Tabela: Uslužbenec_Vzdrževanje (vmesna tabela)
-- =====================================================
DROP TABLE IF EXISTS Usluzbenc_Vzdrzevanje;
CREATE TABLE Usluzbenc_Vzdrzevanje (
    UsluzbencEMSO BIGINT(13),
    VzdrzevanjeIDVzdrzevanja INT(10),
    PRIMARY KEY (UsluzbencEMSO, VzdrzevanjeIDVzdrzevanja),
    CONSTRAINT fk_usluzbenc_vzdrzevanje_usluzbenc FOREIGN KEY (UsluzbencEMSO) REFERENCES Usluzbenc(EMSO),
    CONSTRAINT fk_usluzbenc_vzdrzevanje_vzdrzevanje FOREIGN KEY (VzdrzevanjeIDVzdrzevanja) REFERENCES Vzdrzevanje(IDVzdrzevanja),
    CONSTRAINT emso_mask_vzdrzevanje CHECK (UsluzbencEMSO REGEXP '^[0-9]{7}50[0-9]{4}$')
);

-- =====================================================
-- Tabela: let
-- =====================================================
DROP TABLE IF EXISTS let;
CREATE TABLE let (
    IDLeta VARCHAR(20) PRIMARY KEY,
    Letalska_druzba VARCHAR(30) NOT NULL,
    LetaloRegistracija VARCHAR(15) NOT NULL,
    Datum DATE NOT NULL,
    Cas_odhoda TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Cas_prihoda TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    StartLetalisceID INT(10) NOT NULL,
    KonecLetalisceID INT(10) NOT NULL,
    CONSTRAINT fk_letalska_druzba_let FOREIGN KEY (Letalska_druzba) REFERENCES Letalska_druzba(Ime),
    CONSTRAINT fk_letalo_let FOREIGN KEY (LetaloRegistracija) REFERENCES Letalo(Registracija),
    CONSTRAINT fk_start_letalisce_let FOREIGN KEY (StartLetalisceID) REFERENCES letalisce(LetalisceID),
    CONSTRAINT fk_konec_letalisce_let FOREIGN KEY (KonecLetalisceID) REFERENCES letalisce(LetalisceID)
);

-- =====================================================
-- Tabela: Posadka (vmesna tabela)
-- =====================================================
DROP TABLE IF EXISTS Posadka;
CREATE TABLE Posadka (
    letIDLeta VARCHAR(20) NOT NULL,
    UsluzbencEMSO BIGINT(13) NOT NULL,
    PRIMARY KEY (letIDLeta, UsluzbencEMSO),
    CONSTRAINT fk_let_posadka FOREIGN KEY (letIDLeta) REFERENCES let(IDLeta),
    CONSTRAINT fk_usluzbenc_posadka FOREIGN KEY (UsluzbencEMSO) REFERENCES Usluzbenc(EMSO),
    CONSTRAINT emso_mask_posadka CHECK (UsluzbencEMSO REGEXP '^[0-9]{7}50[0-9]{4}$')
);

-- =====================================================
-- Tabela: Potnik
-- =====================================================
DROP TABLE IF EXISTS Potnik;
CREATE TABLE Potnik (
    potnikID INT(10) PRIMARY KEY AUTO_INCREMENT,
    Ime VARCHAR(20) NOT NULL,
    Priimek VARCHAR(30) NOT NULL,
    Datum_rojstva DATE NOT NULL
);

-- =====================================================
-- Tabela: Rezervacija (vmesna tabela)
-- =====================================================
DROP TABLE IF EXISTS Rezervacija;
CREATE TABLE Rezervacija (
    potnikID INT(10) NOT NULL,
    IDLeta VARCHAR(20) NOT NULL,
    Sedez CHAR(4) NOT NULL,
    Datum_rezervacije DATE NOT NULL, 
    Meni VARCHAR(20) NOT NULL,
    PRIMARY KEY (potnikID, IDLeta),
    FOREIGN KEY (potnikID) REFERENCES Potnik(potnikID),
    FOREIGN KEY (IDLeta) REFERENCES let(IDLeta),
    CONSTRAINT sedez_mask CHECK (Sedez REGEXP '^[0-9]+[A-Z]$'),
    CONSTRAINT meni_mask CHECK (Meni IN ('Vegetarijanski', 'Mesni', 'Veganski', 'Otroški'))
);

-- =====================================================
-- Tabela: Tovorni Nalog
-- =====================================================
DROP TABLE IF EXISTS Tovorni_Nalog;
CREATE TABLE Tovorni_Nalog (
    IDNaloga INT(10) PRIMARY KEY AUTO_INCREMENT,
    IDLeta VARCHAR(20) NOT NULL,
    Masa SMALLINT(5) NOT NULL,
    Opis VARCHAR(200) NOT NULL,
    Narocnik VARCHAR(30) NOT NULL,
    FOREIGN KEY (IDLeta) REFERENCES let(IDLeta)
);



-- =====================================================
-- Podatki za tabele (ne za ta črne (haha))
-- =====================================================

-- Letalska družba
INSERT INTO Letalska_druzba (Ime, Naslov, Lastnik, Spletna_stran) VALUES
('Adria Airways', 'Letališka cesta 30, Ljubljana', 'Jože Potrebuješ', 'www.adriaairways.si'),
('Ryanair', 'Irska', 'Michael OLeary', 'www.ryanair.com');

-- Letališče
INSERT INTO letalisce (LetalisceID, Ime, drzava, longtitude, latitude) VALUES
(123, 'Letališče Jožeta Pučnika Ljubljana', 'Slovenija', 14.4576, 46.2230),
(456, 'Frankfurt Airport', 'Nemčija', 8.5706, 50.0379),
(789, 'Letališče Edvarda Rusjana Maribor', 'Slovenija', 15.6450, 46.4850);

-- Letalo
INSERT INTO Letalo (Registracija, Ime, Proizvajalec, Datum_izdelave, Sedezi, Nosilnost, Tip, Letalska_druzba) VALUES
('S5-AAA', 'Airbus A320', 'Airbus', '2015-06-15', 180, 20000, 'Potniško', 'Adria Airways'),
('D-AIAB', 'Boeing 737', 'Boeing', '2018-09-20', 160, 18000, 'Potniško', 'Adria Airways'),
('S5-BBB', 'Pilatus PC-12', 'Pilatus Aircraft', '2020-03-10', 9, 2000, 'Poslovno', 'Adria Airways'),
('D-ABCD', 'Mig-25', 'Mikoyan-Gurevich', '1985-11-05', 1, 10000, 'Vojaško', 'Ryanair');

-- Uslužbenec
INSERT INTO Usluzbenc (EMSO, Letalska_druzba, ime, priimek, email, vloga) VALUES
(1234567501234, 'Adria Airways', 'Jože', 'Štefan', 'joze.stefan@gmail.com', 'Pilot'),
(2345677502345, 'Ryanair', 'Štefka', 'Rupnik', 'stefka.rupnik@ryanair.com', 'Stevardesa'),
(3456787503456, 'Ryanair', 'Barak', 'Obama', 'barak.obama@gov.com', 'Mehanik');

-- Let
INSERT INTO let (IDLeta, Letalska_druzba, LetaloRegistracija, Datum, Cas_odhoda, Cas_prihoda, StartLetalisceID, KonecLetalisceID) VALUES
('RA123', 'Adria Airways', 'S5-AAA', '2024-07-01', '2024-07-01 10:00:00', '2024-07-01 12:00:00', 123, 456),
('LH456', 'Ryanair', 'D-AIAB', '2024-07-02', '2024-07-02 14:00:00', '2024-07-02 16:30:00', 456, 123);

-- Vzdrževanje
INSERT INTO Vzdrzevanje (IDVzdrzevanja, LetaloRegistracija, Datum, Opis, Vrsta) VALUES
(1, 'S5-AAA', '2024-05-15', 'Redni servis moto rjev', 'Redno'),
(2, 'D-AIAB', '2024-06-10', 'Popravilo pristajalne naprave', 'Izredno');

-- Uslužbenec_Vzdrževanje
INSERT INTO Usluzbenc_Vzdrzevanje (UsluzbencEMSO, VzdrzevanjeIDVzdrzevanja) VALUES
(3456787503456, 1),
(2345677502345, 2);

-- Posadka
INSERT INTO Posadka (letIDLeta, UsluzbencEMSO) VALUES
('RA123', 1234567501234),
('RA123', 2345677502345),
('LH456', 3456787503456);

-- Potnik
INSERT INTO Potnik (potnikID, Ime, Priimek, Datum_rojstva) VALUES
(1, 'Štefan', 'Hadalin', '1990-05-15'),
(2, 'Mici', 'Brinar', '1985-08-22'),
(3, 'Ivan', 'Cankar', '1978-11-30'),
(4, 'Damjan', 'Murko', '1995-02-10');

-- Rezervacija
INSERT INTO Rezervacija (potnikID, IDLeta, Sedez, Datum_rezervacije, Meni) VALUES
(1, 'RA123', '12A', '2024-06-01', 'Mesni'),
(2, 'RA123', '14B', '2024-06-02', 'Vegetarijanski'),
(3, 'LH456', '10C', '2024-06-03', 'Veganski'),
(4, 'LH456', '11D', '2024-06-04', 'Otroški');

-- Tovorni nalog
INSERT INTO Tovorni_Nalog (IDLeta, Masa, Opis, Narocnik) VALUES
('RA123', 500, 'Elektronika', 'TechCorp'),
('LH456', 300, 'Oblačila', 'FashionInc');