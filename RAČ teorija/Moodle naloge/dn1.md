```sql
CREATE TABLE racun(
    izdelek integer AUTO_INCREMENT NOT NULL,
    st_racuna integer NOT NULL,
    znesek integer NOT NULL,
    datum_cas_izdaje date NOT NULL,
    nacin_placila varchar(10),
    CONSTRAINT pk_racun PRIMARY KEY (izdelek, st_racuna),
    CONSTRAINT fk_izdelek FOREIGN KEY (izdelek) REFERENCES izdelek(st_izdelka)
);

ALTER TABLE racun
ADD CONSTRAINT chk_nacin_placila CHECK (nacin_placila = 'kartica' or nacin_placila = 'gotovina');

ALTER TABLE racun
ADD CONSTRAINT chk_cena_izdelka CHECK (znesek <= 500);

ALTER TABLE racun
MODIFY COLUMN znesek decimal(5, 2);
```
