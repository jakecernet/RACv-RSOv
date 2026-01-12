/* 1. Binarno iskalno drevo sestavljajo vozlišča z referencami na dve (binarno!) poddrevesi in celoštevilsko kriterijsko vrednostjo (vsebino).
    a) Spišite javanski program, ki sestavi drevo tako, da v (prazno) drevo zaporedno vstavi vrednosti 5, 9, 7 in 8. (2t)
    b) Izrišite realizaciji iz a) ustrezen razredni diagram, da bo realizacija pod a) smiselna. (1t)

    Diagram:

    +----------------+
    |      Node      |
    +----------------+
    | - v: int       |
    | - l: Node      |
    | - d: Node      |
    +----------------+
    | + Node(n: int) |
    +----------------+
*/

class Node {
    int v;
    Node l, d;

    public Node(int n) {
        v = n;
        l = d = null;
    }
}

public class naloga1 {
    public static void main(String[] args) {
        Node drevo = new Node(5);
        drevo.d = new Node(9);
        drevo.d.l = new Node(7);
        drevo.d.l.d = new Node(8);
    }
}