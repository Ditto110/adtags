package DataStructure;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2020/12/20 10:50
 */
public class BinarySearchTree2<E extends Comparable<? super E>> implements TestBinaryTreeInterface<E> {
    /**
     * 根节点
     */
    private Node<E> root;

    /**
     * 比较器，可以通过构造函数注入自定义比较器
     */
    private Comparator<E> comparator;

    /**
     * 若comparator不为空，则通过它比较；否则通过left.compareTo(right)比较
     *
     * @param left
     * @param right
     * @return
     */
    private int compare(E left, E right) {
        if (comparator != null) {
            return comparator.compare(left, right);
        }
        return left.compareTo(right);
    }

    private void checkEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
    }

    public E findMin() {
        checkEmpty();
        return findMin(root).data;
    }

    /**
     * 一直向左走就能找到最小值
     *
     * @param node
     * @return
     */
    private Node<E> findMin(Node<E> node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public E findMax() {
        checkEmpty();
        return findMax(root).data;
    }

    private Node<E> findMax(Node<E> node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    @Override
    public void insert(E x) {
        root = insert(x, root);
    }

    /**
     * 类似contains方法实现的插入
     *
     * @param x
     * @param node
     * @return
     */
    private Node<E> insert(E x, Node<E> node) {
        if (node == null) {
            //若待查找节点为空，则返回以值为x构造的新节点
            return new Node<>(x);
        }
        //将当前节点的值与x进行比较
        int cmp = compare(x, node.data);
        if (cmp < 0) {
            //x < current node data
            node.left = insert(x, node.left);
        } else if (cmp > 0) {
            node.right = insert(x, node.right);
        }
        // cmp == 0 直接返回
        return node;
    }


    @Override
    public void remove(E x) {
        checkEmpty();
        root = remove(x, root);
    }


    private Node<E> remove(E x, Node<E> node) {
        /**
         *
         * 如果待删除节点是叶子节点，则可以直接被删除
         * 如果节点只有一个孩子，让孩子节点顶替它的位置即可(让待删除节点的父节点指向其子节点)
         * 如果有两个孩子，让其右子树的最小节点的数据代替该节点的数据并递归地删除该最小节点。
         */
        if (node == null) {
            return null;
        }
        //将当前节点的值与x进行比较
        int cmp = compare(x, node.data);
        if (cmp < 0) {
            //x < current node data
            node.left = remove(x, node.left);
        } else if (cmp > 0) {
            node.right = remove(x, node.right);
        } else {
            //找到了待删除节点
            if (node.left != null && node.right != null) {
                //如果有两个孩子
                //其右子树的最小节点的数据代替该节点的数据
                node.data = findMin(node.right).data;
                //递归地删除右子树的最小节点，同时node.right指向更新后的节点
                node.right = remove(node.data,node.right);
            } else {
                node = node.left != null ? node.left : node.right;
            }
        }
        return node;
    }

    /**
     * 删除二叉树中最小节点
     */
    public void removeMin() {
        root = removeMin(root);
    }

    /**
     * 删除node节点最小子树节点
     *
     * @param node
     * @return 新的子树根节点，而不是返回删除的节点
     */
    private Node<E> removeMin(Node<E> node) {
        //找到了最小左子树节点，返回其右子树
        //目的是让其父节点的left指向其右子树
        if (node.left == null) {
            return node.right;
        }
        //更新引用
        node.left = removeMin(node.left);
        return node;
    }


    /**
     * 判断树中是否含有值为x的节点
     *
     * @param x
     * @return
     */
    @Override
    public boolean contains(E x) {
        return contains(x, root);
    }


    private boolean contains(E x, Node<E> node) {
        if (node == null) {
            //递归跳出条件
            return false;
        }

        //将当前节点的值与x进行比较
        int cmp = compare(x, node.data);
        if (cmp == 0) {
            return true;
        } else if (cmp < 0) {
            //x < current node data
            //若x小于当前节点，则比较左子树
            return contains(x, node.left);
        } else {
            return contains(x, node.right);
        }
    }

    /**
     * 返回树中的节点数
     *
     * @return
     */
    public int size() {
        return size(root);
    }

    private int size(Node<E> node) {
        if (node == null) {
            return 0;
        }
        //当前节点(1) + 左子树节点数 + 右子树节点数
        return 1 + size(node.left) + size(node.right);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void makeEmpty() {
        root = null;
    }

    /**
     * 先序遍历(先根遍历):先访问根节点，再访问左右子节点
     */
    public void preOrder() {
        preOrder(root);
        System.out.println();
    }

    private void preOrder(Node<E> node) {
        if (node != null) {
            System.out.print(node.data + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序遍历=:先访问左子节点，再访问根节点，最后访问右子节点
     */
    public void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node<E> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    /**
     * 后序遍历:先访问左子节点，再访问右子节点，最后访问根节点
     */
    public void postOrder() {
        postOrder(root);
        System.out.println();
    }

    private void postOrder(Node<E> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.print(node.data + " ");
        }
    }


    @Override
    public void printTree() {
        if (isEmpty()) {
            System.out.println("Empty tree");
        } else {
            print(root);
        }
    }

    private void print(Node<E> node) {
        System.out.println(node);
    }

    private static class Node<E> {
        E data;
        Node<E> left;
        Node<E> right;

        Node(E data) {
            this(data, null, null);
        }

        Node(E data, Node<E> left, Node<E> right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }


        private void fillString(String prefix, boolean isTail, StringBuilder sb) {
            if (right != null) {
                right.fillString(prefix + (isTail ? "│   " : "    "), false, sb);
            }
            sb.append(prefix).append(isTail ? "└── " : "┌── ").append(data).append("\n");
            if (left != null) {
                left.fillString(prefix + (isTail ? "    " : "│   "), true, sb);
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            this.fillString("", true, sb);
            return sb.toString();
        }
    }
}

