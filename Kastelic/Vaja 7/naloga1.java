/* Dan je  enostaven demo programček:
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
public class Test {
    public static void main(String[] args) {
        Class<?> clazz = MojRazred.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            System.out.println("Metoda: " + m.getName());

            Parameter[] params = m.getParameters();
            for (Parameter p : params) {
                System.out.println("  Argument: " + 
                                   p.getType().getName() + " " + 
                                   p.getName());
            }
        }
    }
}

Dodajte demonstraciji lasten lasten, v katerem opredelite konstruktor, geterje in seteerji pa še kakšno metodo. Dodajte še par lastnosti, nekaj objektnih pa kakšno razredno. 
Poimenujte ga kot MojRazred in zaženite demo program. Analizirajte odgovor in preko delovanja ugotovite, kaj program počne. Mimogrede; vrstica kode Class<?> clazz = MojRazred.class; 
dejansko definira referenco na objekt vrste MojRazred, pri tem objekta ne naredi, omogoča pa vse, kar počne zgornji program, pa še kaj več.

Program pokaže imena vseh metod razreda MojRazred in za vsako metodo izpiše še imena in tipe njenih argumentov.
*/
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class naloga1 {
    public static void main(String[] args) {
        Class<?> clazz = MojRazred.class;

        Method[] methods = clazz.getDeclaredMethods();

        for (Method m : methods) {
            System.out.println("Metoda: " + m.getName());

            Parameter[] params = m.getParameters();
            for (Parameter p : params) {
                System.out.println("  Argument: " + 
                                   p.getType().getName() + " " + 
                                   p.getName());
            }
        }
    }

    public static class MojRazred {
        private String ime;
        private int starost;
        private static String vrsta = "Človek";

        public MojRazred(String ime, int starost) {
            this.ime = ime;
            this.starost = starost;
        }

        public String getIme() {
            return ime;
        }

        public void setIme(String ime) {
            this.ime = ime;
        }

        public int getStarost() {
            return starost;
        }

        public void setStarost(int starost) {
            this.starost = starost;
        }

        public static String getVrsta() {
            return vrsta;
        }

        public void predstaviSe() {
            System.out.println("Sem " + ime + " in imam " + starost + " let.");
        }
    }   
}
