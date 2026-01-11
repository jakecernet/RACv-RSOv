/* [10 točk] Napiši ustrezne stavke, s katerimi:
- obstoječi vlogi Blagajnik dodeliš pravice branja na vseh bazah danega sistema,
- obstoječemu računu Iztok (na lokalnem računalniku), geslo nastaviš na iztok987,
- se vpišeš v Iztokov račun ter vklopiš (uveljaviš/aktiviraš) vlogo Blagajnik,
- računu Iztok odvzameš vlogo blagajnika,
- pobrišeš vlogo Blagajnik. 
*/

GRANT SELECT ON *.* TO 'Blagajnik'@'%';
SET PASSWORD FOR 'Iztok'@'localhost' = PASSWORD('iztok987');
mysql -u Iztok -piztok987
SET ROLE Blagajnik;
REVOKE 'Blagajnik' FROM Iztok@localhost;
DROP ROLE Blagajnik; 