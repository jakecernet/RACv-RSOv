Za izvoz baze iz sistema:
```bash
mysqldump -u root -p im_baze > "C:\Users\Vegova\Desktop\baza.sql"
```

Za uvoz baze v sistem:
```bash
mysql -u root -p  im_baze < "C:\Users\Vegova\Desktop\baza.sql"
```