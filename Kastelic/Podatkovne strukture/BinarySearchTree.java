// Primer implementacije binarnega iskalnega drevesa v Javi
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

    /**
     * Rekurzivna metoda za vstavljanje novega elementa v drevo
     * 
     * @param root Koren drevesa
     * @param data Podatek za vstavljanje
     * @return Posodobljen koren drevesa
     */
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

    // Metoda za iskanje elementa v drevesu
    boolean search(int data) {
        return searchRec(root, data);
    }

    /**
     * Rekurzivna metoda za iskanje elementa v drevesu
     * 
     * @param root Koren drevesa
     * @param data Podatek za iskanje
     * @return True, če je podatek najden, sicer false
     */
    boolean searchRec(Node root, int data) {
        if (root == null) {
            return false;
        }
        if (root.data == data) {
            return true;
        }
        return data < root.data
                ? searchRec(root.left, data)
                : searchRec(root.right, data);
    }

    // Odstranjevanje elementa iz drevesa (neobvezno)
    void delete(int data) {
        root = deleteRec(root, data);
    }

    /**
     * Rekurzivna metoda za odstranjevanje elementa iz drevesa
     * 
     * @param root Koren drevesa
     * @param data Podatek za odstranitev
     * @return Posodobljen koren drevesa
     */
    Node deleteRec(Node root, int data) {
        if (root == null) {
            return root;
        }
        if (data < root.data) {
            root.left = deleteRec(root.left, data);
        } else if (data > root.data) {
            root.right = deleteRec(root.right, data);
        } else {
            // Vozlišče z enim otrokom ali brez otrok
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            // Vozlišče z dvema otrokoma: dobi najmanjšega v desnem poddrevesu
            root.data = minValue(root.right);

            // Izbriši najmanjšega v desnem poddrevesu
            root.right = deleteRec(root.right, root.data);
        }
        return root;
    }

    /**
     * Metoda za iskanje najmanjše vrednosti v drevesu
     * 
     * @param root Koren drevesa
     * @return Najmanjša vrednost v drevesu
     */
    int minValue(Node root) {
        int minv = root.data;
        while (root.left != null) {
            minv = root.left.data;
            root = root.left;
        }
        return minv;
    }

    /**
     * Metoda za iskanje največje vrednosti v drevesu
     * 
     * @param root Koren drevesa
     * @return Največja vrednost v drevesu
     */
    int maxValue(Node root) {
        int maxv = root.data;
        while (root.right != null) {
            maxv = root.right.data;
            root = root.right;
        }
        return maxv;
    }

    /**
     * Inorder traversing
     * 
     * @param root Koren drevesa
     */
    void inorderRec(Node root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.print(root.data + " ");
            inorderRec(root.right);
        }
    }

    /**
     * Postorder traversing
     * 
     * @param root Koren drevesa
     */
    void postorderRec(Node root) {
        if (root != null) {
            postorderRec(root.left);
            postorderRec(root.right);
            System.out.print(root.data + " ");
        }
    }

    /**
     * Preorder traversing
     * 
     * @param root Koren drevesa
     */
    void preorderRec(Node root) {
        if (root != null) {
            System.out.print(root.data + " ");
            preorderRec(root.left);
            preorderRec(root.right);
        }
    }

    /**
     * Vrne višino drevesa
     * 
     * @param root Koren drevesa
     * @return Višina drevesa
     */
    int height(Node root) {
        if (root == null) {
            return 0;
        } else {
            int leftHeight = height(root.left);
            int rightHeight = height(root.right);
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }

    // Test
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        // Vstavljanje elementov
        bst.insert(50);
        bst.insert(30);
        bst.insert(20);
        bst.insert(40);
        bst.insert(70);
        bst.insert(60);
        bst.insert(80);

        // Iskanje elementov
        System.out.println(bst.search(40)); // true
        System.out.println(bst.search(90)); // false
    }
}
