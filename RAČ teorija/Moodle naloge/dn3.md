```sql
CREATE ROLE Blagajnik;
GRANT SELECT, INSERT ON racun TO Blagajnik;
(CREATE ROLE Poslovodja;)
GRANT Blagajnik to Poslovodja;
SET ROLE Blagajnik;

SELECT * FROM mysql.roles_mapping;
SHOW GRANTS FOR 'Poslovodja';
```
