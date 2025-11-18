/* Spišite metodo, ki na zaslon izpiše celotno iskalno pot do iskanega elementa v binarnem iskalnem drevesu(2) s korenom v 'koren'. 
Vrednost iskanega elementa podajte kot argument metodi, kot verjetno tudi koren drevesa. Izrišite razredni diagram vozlišča drevesa(2) 
(da bo vaša metoda dobila smisel). */

public class naloga2 {
    public static void main(String[] args) {

        Node koren = new Node(10);
        koren.left = new Node(5);
        koren.right = new Node(15);
        koren.left.left = new Node(3);
        koren.left.right = new Node(7);
        koren.right.right = new Node(20);

        int targetValue = 20;
        System.out.print("Path to " + targetValue + ": ");
        printPathToValue(koren, targetValue);
    }

    public static boolean printPathToValue(Node node, int value) {
        if (node == null) {
            return false;
        }

        System.out.print(node.value + " ");

        if (node.value == value) {
            return true;
        }

        if (value < node.value) {
            if (printPathToValue(node.left, value)) {
                return true;
            }
        } else {
            if (printPathToValue(node.right, value)) {
                return true;
            }
        }

        return false;
    }
}

class Node {
    int value;
    Node left;
    Node right;

    public Node(int value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }
}