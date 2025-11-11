# Dinamične strukture

-   Npr. tabela:
    -   Naredi pointer
    -   Rezervira fiksen prostor
    -   Hitrost: optimalna
    -   Izboljšava: prilagoditev strukture številu podatkov

<br>

```java
class Element{
    int vr;
    Element naslednji;
}


int[] tab4 = {4, 9, 1, 8}

Element prvi = null;
prvi = new Element();
prvi.vr = 4;
prvi.naslednji = null;

Element drugi = new Element();
drugi.vr = 9;
drugi.naslednji = null;

prvi.naslednji = drugi;
```

<br>

-   Operacije:
    -   Traversing (dostop do vseh podatkov; sprehajanje po tabeli)

<br>

```java
// Primer traversinga
Element te = prvi

while(te != null){
    System.out.println(te.vr);
    te = te.naslednji;
}
```

<br>

## Rekurzija in rekurzivni postopki

```java
void abc(){
    System.out.println("abc");
    bcd();
}

void bcd(){
    System.out.println("bcd");
    cde();
}

void cde(){
    System.out.println("cde");
}
```

<br>
<pre>
[abc]--<---| 
  |        |
  --->---[bcd]
          ^  |
          |  |-->---[cde]
          ^           |
          --<--<---<---
</pre>
Naslov za vračanje in stanje sta na stacku.

<br>
<h3> Primer rekurzivne metode:</h3>

```java
void abc(){
    System.out.println("abc");
    abc();
}
```

-   Lastnosti:

    -   Počasno
    -   Večja poraba pomnilnika

-   In sčasoma:
    -   Veliko ponovitev
    -   Prekoračitev sklada (stack overflow)

<br>
<br>

```java
void izvedi(int k){
    System.out.println(k);
    if (k>0) {              //Pogoj zaustavitve
        izvedi(k-1);        //Korak rekurzije
    }
}
```

```java
void izvedi2(int k){
    if (k>0) {                  //Pogoj zaustavitve
        System.out.println(k);  //Vstop
        izvedi2(k-1);           //Korak rekurzije
        System.out.println(k);  //Vračanje (backtracking)
    }
}
```

```java
int a(int n){
    if (n>1) {                  //Pogoj zaustavitve
        return a(n-1) + a(n-2); //Korak rekurzije
    } else {
        return n;
    }
}
```

```java
class Naloga{
    int[] tab = new int[62];

    void nafilaj(){
        for (int i=0; i<tab.length; i++){
            tab[i] = i+ 7;
        }
    }

    void rekurzivna(int i){
        if (i < tab.length){
            tab[i] = i + 7;
            rekurzivna(i+1);
        }
    }
}
```

```java
class El{
    int v;
    El n;

    public El(int v){
        this.v = v;
        n = null;
    }

    El zacetek = new El(1);
    El k = zacetek;

    for (int i=0; i<30; i++){
        k.n = new El(i+10);
        k = k.n;
    }
}

void izpisiRikverc(El k){
    if (k != null){
        izpisiRikverc(k.n);
        System.out.println(k.v);
    }
}
```
