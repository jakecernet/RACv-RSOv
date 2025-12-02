# Teme:
- Večopravilnost, procesi, niti
- Java, razred Thread,  minimum sinhronizacije : fork, join, nadzor nad številom sočasno izvajanih procesov
- Nekaj malega o refleksiji (Reflection)

Cilj vaje je implementacija in demonstracija obnašanja prometa na/po cesti. Privzamemo: odsek ceste dolžine dC[m],  povprečno dolžino osebnega vozila 4,8m, največjo dolžino vozila, ki lahko vozi po tej cesti 12m, Vozil nad povprečno dolžino v prometu je do 0,2% (2 odtisočka).

Cesto realiziramo kot LinkedList  in uporabimo vmesnik Queue/vrsta s kombinacijo metod add/remove(izjeme) ali offer/poll(null/true/false). Element je vozilo, katerega privzeta minimalna dolžina je 1,5m, med vozili v stoječi vrsti naj bo vsaj 0,75m razdalje.

Vozilo je minimalno opredeljeno z identifikacijo aka številka registracije/tablica, zaporedno številko vozila in svojo dolžino. Zaporedna številka: štejete generirane od 1 naprej po 1.
