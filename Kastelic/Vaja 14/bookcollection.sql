create database if not exists bookcollection;

use bookcollection;

drop table if exists book;

create table book (
  id int primary key auto_increment,
  title varchar(50),
  author varchar(50),
  price float,
  qty int
) AUTO_INCREMENT = 2000;

insert into
  book
values
  (2001, 'Kurji pastir', 'Feri Lainšček', 11.11, 11);

insert into
  book
values
  (
    2002,
    'Kava s smetano',
    'Natalija Pavlič',
    22.22,
    22
  );

insert into
  book
values
  (
    2003,
    'Pevci pozabljenih let',
    'Boris Pahor',
    33.33,
    33
  );

insert into
  book
values
  (2004, 'Galjot', 'Miško Kranjec', 44.44, 44);

insert into
  book
values
  (2005, 'Prišleki', 'Lojze Kovačič', 55.55, 55);

-- select * from book;