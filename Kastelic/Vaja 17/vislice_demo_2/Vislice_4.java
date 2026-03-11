 

import java.util.Scanner;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;

interface Vir {
    public String getGeslo();
}

class VirBesednjak implements Vir{
    public String getGeslo(){
        String[] besednjak={"apokalipsa","nikogršnja zemlja","bela kava","str ojnik"};
        return besednjak[(int)(Math.random()*4)];
       }
}

class VirSiStat implements Vir{
    public String getGeslo(){
        String rval="";
        try{
            URL url = new URL("https://pxweb.stat.si/SiStatData/api/v1/sl/Data/H232S.px");
            Scanner sc= new Scanner(url.openStream(), "UTF-8").useDelimiter("\\\\A");
            rval = sc.next(); 
            sc.close();
       } catch(Exception e){}   
        System.out.println(rval); //parse, return mesto
        return "";
       }
}

class VirDatoteka implements Vir{
    
    public String getGeslo(){
        String rval="";
        File datoteka = new File("gesla.txt");        
        try  {
            Scanner sc = new Scanner(datoteka, "UTF-8").useDelimiter("\\A");
            String vsebina = sc.next();
            System.out.println(vsebina);
            sc.close();
        } catch (FileNotFoundException e) {}
        return rval;
    }
}

public class Vislice_4 {
    
  
    
    public static void main(String[] args) {
        
        var vir = new VirBesednjak();
        //var vira = new VirSiStat(); vira.getGeslo();
        
        //String[] besednjak={"apokalipsa","nikogršnja zemlja","bela kava","str ojnik"};
       
        // vzamemo naključno, normaliziramo
        //String geslo = besednjak[(int)(Math.random()*4)].toLowerCase();
        String geslo = vir.getGeslo().toLowerCase();
        
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
