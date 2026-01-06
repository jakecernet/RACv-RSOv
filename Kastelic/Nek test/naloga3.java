/* Metoda objekta D izracunaj/0 izračuna vsoto vrednosti numeričnih delov zapisa v listih drevesa. /8t/
   * demonstracija pravilnosti delovanja metode /2t/
 */
public class naloga3 {
    public static void main(String[] args) {
        D.BinarySearchTree bst = new D.BinarySearchTree();

        // Vstavljanje elementov
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        // Iskanje elementov
        System.out.println("sestevek" + D.izracunaj2(bst));
    }
}

class D {
    int vsota = 0;

    public class BinarySearchTree {

        // Vozlišče drevesa
        class Node {
            int data;
            Node left, right;

            public Node(int item) {
                data = item;
                left = right = null;
            }
        }

        // Koren drevesa
        Node root;

        // Konstruktor
        public BinarySearchTree() {
            root = null;
        }

        // Metoda za vstavljanje novega elementa v drevo
        void insert(int data) {
            root = insertRec(root, data);
        }

        Node insertRec(Node root, int data) {
            if (root == null) {
                root = new Node(data);
                return root;
            }
            if (data < root.data)
                root.left = insertRec(root.left, data);
            else if (data > root.data)
                root.right = insertRec(root.right, data);
            return root;
        }

        public int izracunaj() {
            if (root != null) {
                izracunaj2(root.left);
                vsota += root.data;
                izracunaj2(root.right);
            }
            return vsota;
        }

    }

    public void izracunaj2(D.BinarySearchTree.Node root) {
        izracunaj2(root);
    }
}