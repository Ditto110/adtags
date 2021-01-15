package DataStructure;

import java.util.LinkedList;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2020/12/18 16:47
 * @desc： 主要是针对二叉树算法的测试
 * 二叉树是指每个节点最多只有两个子节点的树结构,满二叉树和完全二叉树
 * 本测试类的作用是将数组中的数据存放到二叉树中，并且按照三种方式进行树的遍历
 * 参考 https://blog.csdn.net/lph188/article/details/108936595
 */
public class Test_BinTreeGen {
    private static int[] arr = {1, 2, 3, 4, 5, 6};
    private static LinkedList<Node> nodeLinkedList;
    private Node root;
    private static void createTree() {
        nodeLinkedList = new LinkedList<Node>();

        for (int value : arr) {
            nodeLinkedList.add(new Node(value));
        }

        int mid = arr.length / 2 - 1;

        for (int i = 0; i < mid; i++) {
            nodeLinkedList.get(i).leftChild = nodeLinkedList.get(i * 2 + 1);
            nodeLinkedList.get(i).rightChild = nodeLinkedList.get(i * 2 + 2);
        }
        nodeLinkedList.get(mid).leftChild = nodeLinkedList.get(2 * mid + 1);
        if (mid % 2 == 1) {
            nodeLinkedList.get(mid).rightChild = nodeLinkedList.get(2 * mid + 2);
        }
    }

    public void insert(int x) {

    }

    private void insert(int x, Node node) {
        if (node == null) {
            root = new Node(x);
        }
        int compare = x - node.data;
        if (compare < 0) {
            insert(x, node.leftChild);
        } else if (compare >= 0) {
            insert(x, node.rightChild);
        }
    }

    private static class Node {
        Node leftChild;
        Node rightChild;
        int data;

        Node(int newData) {
            leftChild = null;
            rightChild = null;
            data = newData;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "leftChild=" + leftChild +
                    ", rightChild=" + rightChild +
                    ", data=" + data +
                    '}';
        }
    }

    /**
     * 通过递归的方式遍历树结构，如果树的节点太多则容易出现栈溢出
     * 中序遍历
     *
     * @param node
     */
    private static void inorderTraverse(Node node) {
        if (node == null) {
            return;
        }
        inorderTraverse(node.leftChild);
        System.out.print(node.data + " ");
        inorderTraverse(node.rightChild);
    }

    /**
     * 先序遍历
     *
     * @param node
     */
    private static void preTraverse(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data + " ");
        preTraverse(node.leftChild);
        preTraverse(node.rightChild);
    }

    /**
     * 后序遍历
     * @param node
     */
    private static void postTravers(Node node) {
        if (node == null) {
            return;
        }
        postTravers(node.leftChild);
        postTravers(node.rightChild);
        System.out.println(node.data + " ");
    }

    public static void main(String[] args) {
        //生成树
        createTree();
        /*
        * 无论是先序遍历，中序遍历还是后序遍历，都是从根节点为出发点
        * 然后针对每一个节点进行递归遍历
        * */
        inorderTraverse(nodeLinkedList.get(0));

    }
}
