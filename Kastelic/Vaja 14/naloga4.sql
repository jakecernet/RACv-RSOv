/* Struktura podatkovne baze, kot je podana, je neugodna za uporabnika. Ugotovil je:
 - če je avtor avtor več kot ene knjige, se njegovo ime v tabeli pojavi večkrat,
 - mehanizem ne omogoča zapisa nabavne cene knjige, pri čemer naj bi se upoštevalo, da je ista knjiga lahko
 nabavljena večkrat po različnih cenah, in s tem bi lahko imela tudi različne prodajne cene
 
 restrukturirajte podatkovno zbirko v skladu z opisanimi željami in prepišite vsebino v novo zbirko.
 Postopek restrukturiranja in prepisovanja opišite (sekvenca sql stavkov) */
-- Ustvarimo novo tabelo za avtorje
create table avtor (
    id int primary key auto_increment,
    ime varchar(50)
) AUTO_INCREMENT = 1;

-- Vstavimo avtorje iz obstoječe tabele book v novo tabelo avtor
insert into
    avtor (ime)
select
    distinct author
from
    book;

-- Ustvarimo novo tabelo za knjige
create table knjiga (
    id int primary key auto_increment,
    naslov varchar(50),
    avtor_id int,
    foreign key (avtor_id) references avtor(id)
) AUTO_INCREMENT = 1;

-- Vstavimo knjige iz obstoječe tabele book v novo tabelo knjiga
insert into
    knjiga (naslov, avtor_id)
select
    b.title,
    a.id
from
    book b
    join avtor a on b.author = a.ime;

-- Ustvarimo novo tabelo za cene
create table cena (
    id int primary key auto_increment,
    knjiga_id int,
    nabavna_cena float,
    prodajna_cena float,
    foreign key (knjiga_id) references knjiga(id)
) AUTO_INCREMENT = 1;

-- Vstavimo cene iz obstoječe tabele book v novo tabelo cena
insert into
    cena (knjiga_id, nabavna_cena, prodajna_cena)
select
    k.id,
    b.price * 0.8,
    -- Predpostavimo, da je nabavna cena 80% prodajne cene
    b.price
from
    book b
    join knjiga k on b.title = k.naslov;

-- Po potrebi lahko izbrišemo staro tabelo book
-- drop table if exists book;
-- Prikaz vsebine novih tabel za preverjanje
select
    *
from
    avtor;

select
    *
from
    knjiga;

select
    *
from
    cena;

-- Še joinane tabele za preverjanje celovitosti podatkov
select
    k.naslov,
    a.ime as avtor,
    c.nabavna_cena,
    c.prodajna_cena
from
    knjiga k
    join avtor a on k.avtor_id = a.id
    join cena c on k.id = c.knjiga_id;