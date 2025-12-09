# MySQL / MariaDB SQL komande

## Navigacija

-   `mysql -u root -p` - Prijava v MySQL/MariaDB kot root uporabnik
-   `SHOW DATABASES;` - Prikaz vseh baz podatkov
-   `CREATE DATABASE ime_baze;` - Ustvarjanje nove baze podatkov
-   `USE ime_baze;` - Izbira baze podatkov za uporabo
-   `SHOW TABLES;` - Prikaz vseh tabel v izbrani bazi podatkov
-   `DESCRIBE ime_tabele;` - Prikaz strukture tabele
-   `SHOW CREATE TABLE ime_tabele;` - Prikaz tabele bolj podrobno
-   `DROP DATABASE ime_baze;` - Brisanje baze podatkov
-   `DROP TABLE ime_tabele;` - Brisanje tabele iz baze podatkov
-   `SELECT * FROM ime_tabele;` - Prikaz vseh podatkov iz tabele
-   `SHOW WARNINGS;` - Prikaz opozoril po zadnji SQL komandi

## Delo s tabelami

-   `CREATE TABLE ime_tabele (stolpec1 TIP, stolpec2 TIP, ...);` - Ustvarjanje nove tabele

    Primer:

    ```sql
    CREATE TABLE sola (
        naziv_sole VARCHAR(60) PRIMARY KEY,
        naslov VARCHAR(70) NOT NULL,
        email VARCHAR(70) NOT NULL UNIQUE
    );

    CREATE TABLE oddelek (
        oznaka_oddelka CHAR(3) PRIMARY KEY,
        razrednik VARCHAR(40) NOT NULL UNIQUE,
        sola VARCHAR(60) NOT NULL
    );
    ```

-   `INSERT INTO ime_tabele (stolpec1, stolpec2, ...) VALUES (vrednost1, vrednost2, ...);` - Vstavljanje podatkov v tabelo

    Primer:

    ```sql
    INSERT INTO sola (naziv_sole, naslov, email) VALUES ('Vegova', 'Vegova ulica 4, Ljubljana', 'infoa@vegova.si');

    INSERT INTO oddelek (oznaka_oddelka, razrednik, sola) VALUES ('G4A', 'Igor Vulić', 'Vegova Ljubljana');
    ```

-   `CONSTRAINT ime_omejitve CHECK (pogoj);` - Dodajanje omejitve CHECK na stolpec

    Primer:

    ```sql
    CONSTRAINT oddelek_oznaka_ck CHECK (oznaka_oddelka REGEXP '[GRE][1234][ABCD]')
    ```

-   `CONSTRAINT ime_omejitve FOREIGN KEY (stolpec) REFERENCES druga_tabela(drugi_stolpec);` - Dodajanje forein keya

    Primer:

    ```sql
    CONSTRAINT oddelek_sola_fk FOREIGN KEY (sola) REFERENCES sola(naziv_sole)

    ALTER TABLE razred ADD CONSTRAINT razred_razrednik_fk FOREIGN KEY (razrednik) REFERENCES ucitelj(emso);
    ```

-   `ALTER TABLE ime_tabele DROP PRIMARY KEY;` - Odstranitev primarnega ključa iz tabele
-   `ALTER TABLE ime_tabele ADD PRIMARY KEY (stolpec);` - Dodajanje primarnega ključa v tabelo
-   `ALTER TABLE ime_tabele ADD COLUMN ime_stolpca TIP;` - Dodajanje novega stolpca v tabelo
-   `ALTER TABLE ime_tabele MODIFY COLUMN ime_stolpca NOV_TIP;` - Spreminjanje tipa obstoječega stolpca

    Primer:

    ```sql
    ALTER TABLE izdelek MODIFY COLUMN zapisna_st SMALLINT;
    ```

-   `ALTER TABLE ime_tabele CHANGE stari_stolpec novi_stolpec TIP;` - Preimenovanje in spreminjanje tipa stolpca

    Primer:

    ```sql
    ALTER TABLE izdelek CHANGE zapisna_st st_izdelka SMALLINT NOT NULL;
    ```

## DCL ukazi (Data control)

-   `CREATE USER uporabnisko_ime@localhost` - Ustvarjanje novega uporabnika
-   `MYSQL -u uporabnisko_ime -p` - Prijava z novim uporabnikom
-   `GRANT IMENA_PRAVIC ON ime_baze.* TO uporabnisko_ime@localhost;` - Dodeljevanje pravic uporabniku

    Primer:

    ```sql
    GRANT SELECT ON vegas.tab1 TO bogdan@localhost;

    GRANT SELECT, INSERT, UPDATE ON moja_baza.* TO ana@localhost;
    ```

-   `REVOKE IMENA_PRAVIC ON ime_baze.* FROM uporabnisko_ime@localhost;` - Odvzem pravic uporabniku
-   `CREATE ROLE ime_vloge;` - Ustvarjanje nove vloge
-   `GRANT ime_vloge TO uporabnisko_ime@localhost;` - Dodeljevanje vloge uporabniku

    Primer:

    ```sql
    GRANT ucitelj TO bogdan@localhost;
    ```

-   `GRANT IMENA_PRAVIC ON ime_baze.* TO ime_vloge;` - Dodeljevanje pravic vlogi

    Primer:

    ```sql
    GRANT SELECT ON *.* TO ucitelj;
    ```

-   `GRANT izvirna_vloga TO druga_vloga;` - Dodeljevanje pravic obstoječe vloge drugi vlogi

    Primer:

    ```sql
    GRANT ucitelj TO dijak;
    ```

-   `SET ROLE ime_vloge;` - Aktiviranje vloge za trenutnega uporabnika
-   `SET DEFAULT ROLE ime_vloge TO uporabnisko_ime@localhost;` - Nastavitev privzete vloge za uporabnika
-   `EXIT;` - Izhod iz MySQL/MariaDB konzole
-   `CREATE USER uporabnisko_ime@localhost IDENTIFIED BY 'geslo';` - Ustvarjanje uporabnika z geslom

    Primer:

    ```sql
    CREATE USER dorian@localhost IDENTIFIED BY 'dorian123';
    ```

-   `SET PASSWORD FOR uporabnisko_ime@localhost = PASSWORD('novo_geslo');` - Spreminjanje ali nastavljanje gesla za uporabnika

    Primer:

    ```sql
    SET PASSWORD FOR maj@localhost = PASSWORD('maj');
    ```

-   `SELECT * FROM mysql.roles_mapping;` - Prikaz vseh uporabnikov in njihovih vlog
-   `DROP USER uporabnisko_ime@localhost;` - Brisanje uporabnika

    Primer:

    ```sql
    DROP USER dorian@localhost;
    ```

-   `DROP ROLE ime_vloge;` - Brisanje vloge
-   `REVOKE ime_vloge FROM uporabnisko_ime@localhost;` - Odvzem vloge uporabniku

    Primer:

    ```sql
    REVOKE dijak FROM maj@localhost;
    ```
