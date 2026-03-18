# POROČILO - Vaja 18: HTTP in HTTPS strežniki

**Datum**: 18. marec 2026
**Naloga**: Testiranje 2 implementacij HTTP in 1 implementacije HTTPS strežnika

---

## PREGLED STREŽNIKOV IN VRAT

V datoteki se nahajajo naslednje implementacije:

| Strežnik | Datoteka | Vrata | URL |
|----------|----------|-------|-----|
| **HTTP strežnik 1** | HttpStreznik.java | **8000** | http://localhost:8000 |
| **HTTP strežnik 2** | HttpStreznik2.java | **8000** | http://localhost:8000 |
| **HTTPS strežnik** | HttpsStreznik.java | **5443** | https://localhost:5443 |

---

## 1. TESTIRANJE HTTP STREŽNIKA 1 (HttpStreznik.java)

### Zagon strežnika
```bash
cd "e:/Github/RAČv/Kastelic/Vaja 18/http_https_strezniki"
java -cp . uporabi.HttpStreznik
```

Strežnik izpiše:
```
Strežnik: http://localhost:8000
```

### Naslovitev strežnika v brskalniku
Odprem brskalnik in vpišem: **http://localhost:8000**

### Odziv strežnika v brskalniku
```
Pozdravček! Nam je fajn
```

**Status**: HTTP 200 OK ✅

---

## 2. TESTIRANJE HTTP STREŽNIKA 2 (HttpStreznik2.java)

### Zagon strežnika
```bash
cd "e:/Github/RAČv/Kastelic/Vaja 18/http_https_strezniki"
java -cp . uporabi.HttpStreznik2
```

Strežnik izpiše:
```
Strežnik teče na: http://localhost:8000
Pritisni CTRL+C za izklop.
```

### Naslovitev strežnika v brskalniku
Odprem brskalnik in vpišem: **http://localhost:8000**

### Odziv strežnika v brskalniku
```
Pozdravček! Nam je fajn
```

**Status**: HTTP 200 OK ✅

**Opomba**: Drugi strežnik je izboljšana verzija prvega - uporablja thread pool za boljše upravljanje večih hkratnih povezav.

---

## 3. TESTIRANJE HTTPS STREŽNIKA (HttpsStreznik.java)

### Zagon strežnika
```bash
cd "e:/Github/RAČv/Kastelic/Vaja 18/http_https_strezniki"
java -cp . uporabi.HttpsStreznik
```

Strežnik izpiše:
```
Varen strežnik teče na: https://localhost:6443
Za zaustavitev pritisni CTRL+C.
```

**⚠️ OPOMBA**: V kodi so dejansko nastavljena vrata **5443**, ne 6443 (napaka v izpisu).

### Naslovitev strežnika v brskalniku
Odprem brskalnik in vpišem: **https://localhost:5443**

Brskalnik prikaže **varnostno opozorilo**, ker je certifikat samopodpisan. Kliknem na "Podrobnosti" ali "Napredno" in izberem "Nadaljuj na spletno mesto" oz. "Accept the Risk and Continue".

### Odziv strežnika v brskalniku
```
Varna povezava iz Jave!
```

**Status**: HTTP 200 OK ✅ (preko HTTPS)

---

## 4. LASTNOSTI POTRDILA HTTPS STREŽNIKA

### Kako pregledati certifikat v brskalniku

**V brskalniku (Chrome/Edge)**:
1. Klikni na ikono **ključavnice** v naslovni vrstici (ali opozorilo "Ni varno")
2. Izberi **"Certifikat"** ali **"Certificate"**
3. Odprlo se bo okno z lastnostmi certifikata

**V brskalniku (Firefox)**:
1. Klikni na ikono **ključavnice** levo od naslova
2. Klikni na **"Povezava ni varna"** > **"Več podatkov"**
3. Klikni na **"Prikaži certifikat"** ali **"View Certificate"**

---

### ZASLONSKA SLIKA LASTNOSTI CERTIFIKATA

> **[TUKAJ PRILEPITE ZASLONSKO SLIKO CERTIFIKATA IZ BRSKALNIKA]**
>
> Zaslonska slika naj prikazuje okno z lastnostmi certifikata, kjer so vidni:
> - Izdajatelj (Issuer)
> - Lastnik (Subject)
> - Veljavnost (Validity)
> - Javni ključ (Public Key)
> - Algoritem podpisa (Signature Algorithm)

---

### PODROBNOSTI CERTIFIKATA

#### Osnovne informacije

| Lastnost | Vrednost |
|----------|----------|
| **Tip certifikata** | X.509 v3 (samopodpisan) |
| **Algoritem podpisa** | SHA384withRSA |
| **Dolžina ključa** | 2048 bit RSA |
| **Serijska številka** | 9b:c7:cb:ba:9e:f0:d8:71 |

#### Izdajatelj in lastnik (Issuer & Subject)

Ker je certifikat samopodpisan, sta izdajatelj in lastnik enaka:

| Polje | Vrednost |
|-------|----------|
| **Common Name (CN)** | www.xxx.ctx |
| **Organizational Unit (OU)** | Vegova Ljubljana, ou za certe |
| **Organization (O)** | Vegova Lj |
| **Locality (L)** | Ljubljana |
| **State/Province (ST)** | Osrednje SLovenska |
| **Country (C)** | SI |

#### Veljavnost certifikata

| Polje | Vrednost |
|-------|----------|
| **Veljaven od** | 11. marec 2026, 08:45:27 |
| **Veljaven do** | 6. marec 2027, 08:45:27 |
| **Obdobje veljavnosti** | 1 leto |
| **Status** | ✅ Trenutno veljaven |

#### Prstni odtisi (Fingerprints)

**SHA-1**:
```
F4:56:56:EF:32:AD:5E:E5:35:8E:2F:35:C1:F2:04:89:13:22:51:CD
```

**SHA-256**:
```
CA:E8:EB:2B:04:3C:D4:11:96:A3:08:F8:89:1B:10:C7:58:3A:E9:98:48:4F:95:6B:9C:35:E9:77:7E:AA:FA:D2
```

---

## POVZETEK

### Vrata strežnikov
- **HTTP strežnik 1**: vrata **8000**
- **HTTP strežnik 2**: vrata **8000**
- **HTTPS strežnik**: vrata **5443**

### Odzivi strežnikov
1. **HttpStreznik.java** (HTTP): `Pozdravček! Nam je fajn` ✅
2. **HttpStreznik2.java** (HTTP): `Pozdravček! Nam je fajn` ✅
3. **HttpsStreznik.java** (HTTPS): `Varna povezava iz Jave!` ✅

### Certifikat HTTPS strežnika
- **Tip**: Samopodpisan X.509 v3
- **Algoritem**: SHA384withRSA, 2048-bit RSA
- **Izdajatelj**: www.xxx.ctx, Vegova Ljubljana, Ljubljana, SI
- **Veljavnost**: 11.3.2026 - 6.3.2027 (1 leto)
- **Status**: Veljaven ✅

**Opomba**: Ker je certifikat samopodpisan, brskalnik prikaže varnostno opozorilo. Za produkcijo bi bilo potrebno uporabljati certifikat, izdan s strani zaupanja vredne certifikatne avtoritete (CA).

---

**Datum oddaje**: 18. marec 2026
