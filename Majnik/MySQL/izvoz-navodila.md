Za izvoz baze iz sistema:
```bash
mysqldump -u root -p ime_baze > "C:\Users\Vegova\Desktop\baza.sql"
```

Za uvoz baze v sistem:
```bash
mysql -u root -p  ime_baze < "C:\Users\Vegova\Desktop\baza.sql"
```

Izvoz baze z vlogami:
```bash
mysqldump -u root --system=users --insert-ignore > "C:\Users\Vegova\Desktop\baza_z_userji.sql"
```

Za uvoz baze z vlogami
```bash
mysql -u root -p  ime_baze < "C:\Users\Vegova\Desktop\baza.sql"
```
