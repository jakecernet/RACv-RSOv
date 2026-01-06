/*     Metoda objekta D je opredeljena kot preveri/1 . Pregleda z objektom opredeljeno binarno iskalno drevo in vrne true v primeru, 
da se z argumentom podana iskana vrednost nahaja kot del vsebine v enem izmed vozlišč drevesa.  
Vozlišče je vsebinsko sestavljeno iz niza in celoštevilske vrednosti. Primer iskanja:
         boolean tf = preveri("stohastika");  /10t/
   * demonstracija pravilnosti delovanja metode /10t/
 */

public class naloga1 {
  public static void main(String[] args) {
    D bst = new D();

    // Vstavljanje elementov
    bst.insert(50, "petdeset");
    bst.insert(30, "trideset");
    bst.insert(20, "dvajset");
    bst.insert(40, "štirideset");
    bst.insert(70, "sedemdeset");

    // Iskanje elementov
    System.out.println(bst.preveri(40, "štirideset")); // true
    System.out.println(bst.preveri(90, "devetdest")); // false
    System.out.println(bst.preveri(20, "dvajset"));
  }
}

class D {
  class Node {
    int cifra;
    String niz;
    Node left, right;

    public Node(int item, String item2) {
      cifra = item;
      niz = item2;
      left = right = null;
    }
  }

  Node root;

  boolean preveri(int data, String nizdata) {
    if (root == null) {
      return false;
    }
    if (root.cifra == data) {
      return true;
    }
    return data < root.cifra
        ? preveriSub(root.left, data)
        : preveriSub(root.right, data);
  }

  boolean preveriSub(Node root, int data) {
    if (root == null) {
      return false;
    }
    if (root.cifra == data) {
      return true;
    }
    return data < root.cifra
        ? preveriSub(root.left, data)
        : preveriSub(root.right, data);
  }

  void insert(int data, String niz) {
    root = insertRec(root, data, niz);
  }

  Node insertRec(Node root, int data, String nizdata) {
    if (root == null) {
      root = new Node(data, nizdata);
      return root;
    }
    if (data < root.cifra)
      root.left = insertRec(root.left, data, nizdata);
    else if (data > root.cifra)
      root.right = insertRec(root.right, data, nizdata);
    return root;
  }
}
