/* [10 točk] Ustvari podatkovno bazo NLB. Napiši stavek, ki brez predhodne uporabe stavka USE ustvari tabelo Imetnik, 
v kateri bomo zbirali podatke o številki EMŠO imetnika (na njej mora biti primarni ključ, poleg tega na 8., 9. in 10. mestu sprejema le vrednosti 500 ali 505), 
priimku, imenu, davčni številki (ki je osem mestna, poleg tega mora biti edinstvena, se ne sme ponoviti), državljanstvu (privzeto naj bo slovensko državljanstvo), 
datumu rojstva (imetnik mora imeti vsaj 15 let glede na današnji datum) ter opombah, pri čemer so opombe opcijske. 
*/

CREATE DATABASE NLB;

CREATE TABLE NLB.Imetnik (
    EMSO CHAR(13) PRIMARY KEY,
    Priimek VARCHAR(50) NOT NULL,
    Ime VARCHAR(50) NOT NULL,
    DavcnaStevilka CHAR(8) UNIQUE NOT NULL,
    Drzavljanstvo VARCHAR(50) DEFAULT 'Slovensko',
    DatumRojstva DATE NOT NULL,
    Opombe VARCHAR(100),
    CONSTRAINT emso_chk CHECK (SUBSTRING(EMSO, 8, 3) IN ('500', '505')),
    CONSTRAINT starost_chk CHECK (DATEDIFF(CURDATE(), DatumRojstva) >= 15 * 365)
);