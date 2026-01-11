/* [20 točk] Načrtovana shema tabele Transakcija je:

Transakcija (st_racuna_posiljatelja:A19 → Bancni_racun, st_transakcije:N, znesek_transakcije:N, datum_cas_transakcije:DT, st_racuna_prejemnika:A19 → Bancni_racun).

Z ukazom SHOW CREATE TABLE Transakcija; v MariaDB izvemo, da ima obstoječa tabela stolpce st_racuna_posiljatelja (s podatkovnim tipom CHAR(7)), 
st_transakcije (s podatkovnim tipom INT), opombe (s podatkovnim tipom VARCHAR(100)), pri čemer so vsi stolpci opcijski. 
Na obstoječi tabeli imamo primarni ključ na stolpcu st_racuna_posiljatelja, sicer pa nobenega drugega ključa ter nobene druge integritetne omejitve.
Predpostavi lahko, da tabela Bancni_racun že obstaja, primarni ključ pa je na stolpcu st_racuna.

Z ustreznimi stavki SQL predelaj tabelo Transakcija, da bo natančno sledila podanemu načrtu. Pri tem upoštevaj tudi, 
da mora znesek transakcije biti pozitiven, a ne večji od 1 milijona. Prav tako upoštevaj, da naj podatkovni tipi ne bodo večji, kot je nujno potrebno. 
*/

ALTER TABLE Transakcija
    DROP PRIMARY KEY,
    DROP COLUMN opombe,
    ADD PRIMARY KEY (st_racuna_posiljatelja, st_transakcije),
    ADD COLUMN datum_cas_transakcije DATETIME NOT NULL,
    ADD COLUMN st_racuna_prejemnika VARCHAR(19) NOT NULL,
    MODIFY COLUMN st_racuna_posiljatelja VARCHAR(19) NOT NULL,
    ADD CONSTRAINT fk_racun FOREIGN KEY (st_racuna_posiljatelja) REFERENCES Bancni_racun(st_racuna),
    ADD CONSTRAINT fk_prejemnik FOREIGN KEY (st_racuna_prejemnika) REFERENCES Bancni_racun(st_racuna),
    ADD CONSTRAINT znesek_chk CHECK (znesek_transakcije > 0 AND znesek_transakcije <= 1000000);
    
