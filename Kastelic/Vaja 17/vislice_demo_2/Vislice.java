 

import java.util.Scanner;

public class Vislice {
    
    public static void main(String[] args) {
        
        
        String[] besednjak={"apokalipsa","nikogršnja zemlja","bela kava","str ojnik"};
       
        // vzamemo naključno, normaliziramo
        String geslo = besednjak[(int)(Math.random()*4)].toLowerCase();
        
        // Ustvarimo niz podčrtajev enake dolžine kot geslo,
        // dopolnimo s prvo in zadnjo črko, upoštevamo presledke

        String trenutno = "";
        for (int i=0; i<geslo.length(); i++){
          if ( geslo.charAt(i)==' ') { trenutno +=" "; continue;}
          if ( geslo.charAt(i)==geslo.charAt(0) || geslo.charAt(i)==geslo.charAt(geslo.length()-1))
             trenutno += geslo.charAt(i);
            else
             trenutno +="_";
        }
        
        
        int poskusi = 10;
        Scanner sc = new Scanner(System.in);

        while (poskusi > 0 && trenutno.compareTo(geslo)!=0) {
            System.out.println("\nBeseda: " + trenutno + " (Še " + poskusi + " poskusov)");
            System.out.print("Črka: ");
            String vnos = sc.nextLine().toLowerCase();
            
            if (vnos.isEmpty()) continue;
            char crka = vnos.charAt(0);

            if (geslo.indexOf(crka) >= 0) {
                // Sestavimo nov niz, kjer zamenjamo podčrtaje s pravilno črko
                String novoStanje = "";
                for (int i = 0; i < geslo.length(); i++) {
                    if (geslo.charAt(i) == crka) {
                        novoStanje += crka;
                    } else {
                        novoStanje += trenutno.charAt(i);
                    }
                }
                trenutno = novoStanje;
            } else {
               
                System.out.println("Napačno!");
            }
             poskusi--;
        }

        if (poskusi>=0) {
            System.out.println("Zmaga! Beseda je: " + geslo);
        } else {
            System.out.println("Obup! Beseda je bila: " + geslo);
        }
        sc.close();
    }
}
