/* Spišite metodo ll2Html/0, ki iz vsebine seznama generira niz z vsebovano pravilno kreirano html tabelo. Metoda v nizu generirano tabelo vrne klicočemu programu.
Tabela naj bo formirana kot:

Ime Priimek Dohodek
Pepa Grm 13233.31
Bob Strela 9801.13

Za

Html tabela oblike

A B C
D E F

je podana s sintakso

<table> <tr><td>A</td><td>B</td><td>C</td></tr><tr><td>D</td><td>E</td><td>F</td></tr> </table>

če želite še okvirje, dodate :

<style>table,td,th{border:1px solid black} table{border-collapse:collapse}</sytle> */
import java.util.LinkedList;

public class naloga3 {
    public static LinkedList<Os> osebe = new LinkedList<>();

    public static void main(String[] args) {
        String[] ime = {"Janez", "Pepa", "Rok", "Bob"};
        String[] priimek = {"Novak", "Jež", "Grm", "Jelen", "Krt", "Strela"};

        for (int i = 0; i < 60; i++) {
            String izbranoIme = ime[(int) (Math.random() * ime.length)];
            String izbranPriimek = priimek[(int) (Math.random() * priimek.length)];
            double letniDohodek = 9100 + Math.random() * (91000 - 9100);
            Os oseba = new Os(izbranoIme, izbranPriimek, letniDohodek);
            osebe.add(oseba);
        }

        String htmlTabela = ll2Html();

        try (java.io.PrintWriter out = new java.io.PrintWriter("tabela.html")) {
            out.println("<!DOCTYPE html><html><head>");
            out.println("<style>table,td,th{border:1px solid black; padding: 10px; text-align: center;} table{border-collapse:collapse}</style>");
            out.println("</head><body>");
            out.println(htmlTabela);
            out.println("</body></html>");
            System.out.println("HTML tabela je bila zapisana v datoteko: tabela.html");
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
