package DataStructure;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2020/12/19 13:55
 */
public class Test_BinTreeStruct {

    private static class Node<E> {
        private E data;
        private Node<E> leftChild;
        private Node<E> rightChild;

        public Node(E data) {
            this(data, null, null);
        }

        public Node(E data, Node<E> leftChild, Node<E> rightChild) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }



}
