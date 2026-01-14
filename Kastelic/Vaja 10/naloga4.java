
/* Metoda zapisiHtml/2 dobi kot argument ime datoteke in niz (hm, generiran v nalogi 3?) in ta niz zapiše na datoteko z danim imenom. */
import java.util.LinkedList;
import java.util.Scanner;

public class naloga4 {
    public static LinkedList<Os> osebe = new LinkedList<>();

    public static void main(String[] args) {
        String[] ime = { "Janez", "Pepa", "Rok", "Bob" };
        String[] priimek = { "Novak", "Jež", "Grm", "Jelen", "Krt", "Strela" };

        for (int i = 0; i < 60; i++) {
            String izbranoIme = ime[(int) (Math.random() * ime.length)];
            String izbranPriimek = priimek[(int) (Math.random() * priimek.length)];
            double letniDohodek = 9100 + Math.random() * (91000 - 9100);
            Os oseba = new Os(izbranoIme, izbranPriimek, letniDohodek);
            osebe.add(oseba);
        }

        String htmlTabela = ll2Html();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Vnesite ime datoteke (brez pripone .html): ");
        String imeDatoteke = scanner.nextLine();
        zapisiHtml( imeDatoteke, htmlTabela);
        scanner.close();
    }

    public static void zapisiHtml(String imeDatoteke, String vsebina) {
        try (java.io.PrintWriter out = new java.io.PrintWriter(imeDatoteke + ".html")) {
            out.println("<!DOCTYPE html><html><head>");
            out.println(
                    "<style>table,td,th{border:1px solid black; padding: 10px; text-align: center;} table{border-collapse:collapse}</style>");
            out.println("</head><body>");
            out.println(vsebina);
            out.println("</body></html>");
            System.out.println("HTML tabela je bila zapisana v datoteko: "+ imeDatoteke+ ".html");
        } catch (java.io.IOException e) {
            System.out.println("Napaka pri pisanju v datoteko: " + e.getMessage());
        }
    }

    public static String ll2Html() {
        StringBuilder htmlTabela = new StringBuilder();
        htmlTabela.append("<table>\n");
        htmlTabela.append("<tr><th>Ime</th><th>Priimek</th><th>Dohodek</th></tr>\n");

        for (Os oseba : osebe) {
            htmlTabela.append("<tr>");
            htmlTabela.append("<td>").append(oseba.ime()).append("</td>");
            htmlTabela.append("<td>").append(oseba.priimek()).append("</td>");
            htmlTabela.append("<td>").append(String.format("%.2f", oseba.letniDohodek())).append("</td>");
            htmlTabela.append("</tr>\n");
        }

        htmlTabela.append("</table>");
        return htmlTabela.toString();
    }
}

final record Os(String ime, String priimek, double letniDohodek) {

}
