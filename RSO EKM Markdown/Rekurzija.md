# 1.1 Rekurzija

## Ponavljanja

- **Iteracijsko:** `for`, `while`, `do-while`
- **Programsko:**
    - **Iterativno:**
        ```java
        for (int i = 0; i < 6; i++)
                System.out.print(i);
        ```
    - **Rekurzivno:** kličemo imenovan blok (metoda)
        ```java
        void ponovi(int i) {
                System.out.print(i);
                ponovi(i); // ponavlja v neskončnost: prekoračitev obsega sklada ("stack overflow")
        }
        ```
        ```java
        void ponovi2(int i) {
                if (i > 0) {
                        System.out.print(i);
                        ponovi2(i - 1);
                }
        }
        ```
        - *Rekurzivna:* vsebuje klic same sebe
        - Vsebuje enostaven pogoj za zaustavitev (`if`!)

        **Izpis:**
        ```
        654321   // izpis pri vstopu v rekurzijo
        ```

        ```java
        void ponovi3(int i) {
                if (i > 0) {
                        ponovi3(i - 1);
                        System.out.print(i);
                }
        }
        ```
        **Izpis:**
        ```
        123456   // izpis pri izstopu iz rekurzije (back-tracking)
        ```

        **Podtakni:**
        ```java
        void ponovi4(int i) {
                if (i > 0) {
                        ponovi3(i - 1);
                        System.out.print(i);
                        ponovi3(i - 1);
                }
        }
        ```

        **Palindrom:**
        ```java
        void ponovi5(int i) { // palindrom
                if (i > 0) {      // izpis pri vstopu in izstopu
                        System.out.print(i);
                        ponovi3(i - 1);
                        System.out.print(i);
                }
        }
        ```

- *Enostavne sledi za 1, 2, 3, 5*

## Lastnosti

- Počasna; shranjevanje in restavriranje stanja in naslovov za vračanje
- Trošenje pomnilnika; naslovi, stanje
- Mnogokrat intuitivnejša, lažja in naravnejša implementacija
- Včasih ne gre brez, tedaj sta obe negativni lastnosti prednosti