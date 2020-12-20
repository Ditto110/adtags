package DataStructure;

import java.util.NoSuchElementException;

/**
 * Created by IntelliJ IDEA.
 * 搜索二叉树
 * @Author : ASUS
 * @create 2020/12/19 14:56
 * 要求E实现了comparable接口
 */
public class BinarySearchTree<E extends Comparable<? super E>> implements TestBinaryTreeInterface<E> {

    private Node<E> root;

    private Comparable<E> comparator;


    //通过定义的泛型可知，E 必须是Comparable的子类
    private int compare(E left, E right) {
        return left.compareTo(right);
    }

    @Override
    public void insert(E x) {
        root = insert(x, root);
    }

    /**
     * @param x
     * @param node
     * @return
     */
    private Node<E> insert(E x, Node<E> node) {
        if (node == null) {
            return new Node<>(x);
        }
        int compare = compare(x, node.data);
        if (compare < 0) {
            node.leftChild = insert(x, node.leftChild);
            ;
        } else if (compare > 0) {
            node.rightChild = insert(x, node.rightChild);
        }
        return node;
    }

    public E findMin() {
//        return findMin(root).data;
        return findMin2(root).data;
    }

    private Node<E> findMin(Node<E> node) {
        checkEmpty();
        while (node.leftChild != null) {
            node = node.leftChild;
        }
        return node;
    }
    private Node<E> findMin2(Node<E> node) {
        checkEmpty();
        if (node.leftChild == null) {
            return node;
        }
        return findMin2(node.leftChild);
    }

    public E findMax() {
        checkEmpty();
        return findMax(root).data;
    }
    private Node<E> findMax(Node<E> node) {
        while (node.rightChild != null) {
            node = node.rightChild;
        }
        return node;
    }
    private Node<E> findMax2(Node<E> node) {
        if (node.rightChild == null) {
            return node;
        }
        return findMax(node.rightChild);
    }
    @Override
    public void remove(E x) {
        checkEmpty();
        root = remove(x, root);
    }

    private Node<E> remove(E x, Node<E> node) {
        int compare = compare(x, node.data);
        if (compare < 0) {
            remove(x, node.leftChild);
        } else if (compare > 0) {
            remove(x, node.rightChild);
        }else {
            if (node.leftChild != null && node.rightChild != null) {
                node.leftChild = null;
            }
        }

        return node;
    }

    @Override
    public boolean contains(E x) {
        return contains(x, root);
    }

    /**
     * 重构contains方法
     * 由于搜索二叉树的性质已经决定了，左子树的值都小于根节点的值，因此采用递归的方式遍历
     *
     * @param x
     * @param node
     * @return
     */
    private boolean contains(E x, Node<E> node) {
        if (node == null) {
            return false;
        }
        int compare = compare(x, node.data);
        if (compare == 0) {
            return true;
        } else if (compare < 0) {
            return contains(x, node.leftChild);
        } else {
            return contains(x, node.rightChild);
        }
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    @Override
    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public void makeEmpty() {
        this.root = null;
    }

    @Override
    public void printTree() {
        preOrder(root);
        System.out.println(" ");
        inOrder(root);
    }

    /**
     * 先序遍历:先访问根节点，再访问左子树，最后访问右子树
     *
     * @param node
     */
    private void preOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        System.out.print(node.data + " ");
        preOrder(node.leftChild);
        preOrder(node.rightChild);
    }

    /**
     * 中序遍历： 先访问根节点，再访问左子树，最后访问右子树
     * @param node
     */
    private void inOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        inOrder(node.leftChild);
        System.out.print(node.data + " ");
        inOrder(node.rightChild);
    }

    /**
     * 后序遍历：先访问左子树，再访问右子树，最后访问根节点
     * @param node
     */
    private void postOrder(Node<E> node) {
        if (node == null) {
            return;
        }
        postOrder(node.leftChild);
        postOrder(node.rightChild);
        System.out.println(node.data);
    }
    public int size() {
        return size(root);
    }

    private int size(Node<E> node) {
        if (node == null) {
            return 0;
        }
        return 1 + size(node.leftChild) + size(node.rightChild);
    }

    /**
     * 非静态内部类可以访问到外部类的成员变量，因此Node 可以不定义成泛型
     * 此处内部类的泛型e和外部类的E不同
     */
    private static class Node<e> {
        private e data;
        private Node<e> leftChild;
        private Node<e> rightChild;

        public Node(e data) {
            this(data, null, null);
        }

        public Node(e data, Node<e> leftChild, Node<e> rightChild) {
            this.data = data;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "data=" + data +
                    ", leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    '}';
        }
    }

}
