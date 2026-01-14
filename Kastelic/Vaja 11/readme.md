## Teme:
- Besedilne datoteke (Button, Image, ImageView,…)
- Osnovne operacije na besedilnih datotekah
- Dodatna navodila in viri:


### Pavšalen opis rabe /API-ja/:
`MojProgTen brisi -email nek.moj@email.ee` – briše informacijo o uporabniku
<br>
`MojProgTen dodaj -u uporabnik -rezultat 1234` - doda rezultat
<br>
`MojProgTen registriraj -u uporabnik -email nek.moj@email.si` - doda uporabnika
<br>
`MojProgTen getTopTen [-o ime.dat [-t html|txt|xml] ]` - izpiše 10 top uporabnikov

Datoteka rezultati.txt je besedilna datoteka, ki vzdržuje zbirko dosežkov uporabnikov pri neki igri. Strukturirana je kot: uporabnik;email;rezultat;dosežen_dne;število_igranj. Polja v datoteki so ločena s podpičjem, posamezni zapisi v lastnih vrsticah. Datoteka naj ima zaglavje, kot je mastno predhodno zapisano. Zaglavje nastopa kot zapis (je v lastni vrstici)