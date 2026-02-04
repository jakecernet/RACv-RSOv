/* pripravite povpraševanja (sql stavke) za: */
/* - vrne število zapisov tabele book */
select
    count(*) as stevilo_zapisov
from
    book;

/*  - vrne število knjig iz tabele book, ki jih je napisal Boris Pahor */
select
    count(*) as stevilo_knjig_boris_pahor
from
    book
where
    author = 'Boris Pahor';

/*  - vrne vse naslove knjih (title) avtorja Lainšček */
select
    title
from
    book
where
    author = 'Feri Lainšček';

/*  - vrne informacije o knjigah, ki so cenejše od 30 evrov */
select
    *
from
    book
where
    price < 30;

/* - ugotovi, ali imamo knjigo avtorice Natalije Pavlič.*/
select
    case
        when exists (
            select
                *
            from
                book
            where
                author = 'Natalija Pavlič'
        ) then 'Knjiga avtorice Natalije Pavlič je na voljo.'
        else 'Knjiga avtorice Natalije Pavlič ni na voljo.'
    end as rezultat;