1. Dan je javanski razred:

```java
class Mr implement Runnable { public Mr(int a){ this.a=a; } }
```

Odpravite napake v definiciji razreda oz. ga dopolnite, da bo mogoče narediti primerke takega razreda kot:

```java
Mr obj = new Mr(3);
```

2. Javanski program **Naredi** med svojim izvajanjem kreira 3 niti iste vrste (tipa) in jih zažene. Vsaka izmed niti v svoji življenjski dobi izračuna vsoto 20000 naključnih števil iz obsega od 0 do 10000. Vsoto vsaka nit računa po modulu 256. Ob zaključku izvajanja vsoto izpiše. Spišite tak (javanski) program.

3. Javanska nit vrste **Generirraj** med svojim izvajanjem generira števila v obsegu med 0 in `Long.MAX_VALUE`. Ko prvič generira duplikat (enako vrednost, kot jo je predhodno že generirala), je to za nit znak, da mora generirati še natanko 10 vrednosti in jih izpisati na zaslon. S tem se njeno izvajanje zaključi. Spišite javansko definicijo take niti.

4. Recimo, da v javanskem programu ustvarite in zaženete poljubno število niti vrste **MNit**. Kako dosežete, da se izvajanje vseh niti konča, ko prva izmed njih doseže 7000 tisoč iteracij? Spišite ustrezno javansko kodo.

5. Javanska metoda **napolni** objekta tipa **Naloga5** zapiše v strukturo osnovano na `LinkedList` 100 različnih naključno (zgeneriranih števil). Metoda `napolni()` (0 nima argumentov) ne vrača nobene vrednosti.  
a) Spišite ustrezen javanski program z opisano metodo.  
b) Spišite metodo, ki bo na zaslon izpisala celotno generirano zaporedje števil v obratnem vrstnem redu, kot bi bila ustvarjena.