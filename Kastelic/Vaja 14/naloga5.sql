/* Arhivirajte končno vsebino podatkovne zbirke, ukaz za izvedbo in arhiv naj bosta del poročila, slednji kot 
priloga v lastni datoteki. */

mysqldump -u root bookcollection > book_backup.sql

mysql -u root bookcollection < book_backup.sql