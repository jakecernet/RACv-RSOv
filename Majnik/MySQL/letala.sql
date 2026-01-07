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
    longtitude DOUBLE NOT NULL,
    latitude DOUBLE NOT NULL
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
    CONSTRAINT fk_letalska_druzba_usluzbenec FOREIGN KEY (Letalska_druzba) REFERENCES Letalska_druzba(Ime)
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
    CONSTRAINT fk_usluzbenc_vzdrzevanje_vzdrzevanje FOREIGN KEY (VzdrzevanjeIDVzdrzevanja) REFERENCES Vzdrzevanje(IDVzdrzevanja)
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
    CONSTRAINT fk_usluzbenc_posadka FOREIGN KEY (UsluzbencEMSO) REFERENCES Usluzbenc(EMSO)
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
    Meni VARCHAR(10) NOT NULL,
    PRIMARY KEY (potnikID, IDLeta),
    FOREIGN KEY (potnikID) REFERENCES Potnik(potnikID),
    FOREIGN KEY (IDLeta) REFERENCES let(IDLeta)
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