package DataStructure;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : SDT14325
 * @create 2020/10/12 9:19
 */
public class TestBinTree {
    public static void main(String[] args) {

        BinarySearchTree<Integer> tree = new BinarySearchTree<>();

        tree.insert(2);

        tree.insert(10);

        tree.insert(5);

        tree.insert(1);

        tree.printTree();

        System.out.println(" ");
        System.out.println(String.format("contains:%s", tree.contains(1)));
        System.out.println(String.format("tree size:%s", tree.size()));
        System.out.println(String.format("tree min:%s", tree.findMin()));
        System.out.println(String.format("tree max:%s", tree.findMax()));
    }
}
