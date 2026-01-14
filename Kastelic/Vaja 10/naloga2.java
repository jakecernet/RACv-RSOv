/* Na osnovi dveh slovarjev

ime=["Janez", "Pepa", "Rok", "Bob",
priimek =["Novak", "Jež", "Grm", "Jelen", "Krt", "Strela",

kreirajte 60 različnih kombinacij ime-priimek, s pomočjo vsake kreirajte primerek vrste Os in slednjega zapišite v seznam (LinkedList). 
Dohodek osebam generirajte kot ne-celo številko iz obsega 9100 – 91000. */
import java.util.LinkedList;

public class naloga2 {
    public static void main(String[] args) {
        String[] ime = {"Janez", "Pepa", "Rok", "Bob"};
        String[] priimek = {"Novak", "Jež", "Grm", "Jelen", "Krt", "Strela"};
        LinkedList<Os> osebe = new LinkedList<>();

        for (int i = 0; i < 60; i++) {
            String izbranoIme = ime[(int) (Math.random() * ime.length)];
            String izbranPriimek = priimek[(int) (Math.random() * priimek.length)];
            double letniDohodek = 9100 + Math.random() * (91000 - 9100);
            Os oseba = new Os(izbranoIme, izbranPriimek, letniDohodek);
            osebe.add(oseba);
        }

        for (Os o : osebe) {
            System.out.println(o);
        }
    }
}

final record Os(String ime, String priimek, double letniDohodek) {

}
