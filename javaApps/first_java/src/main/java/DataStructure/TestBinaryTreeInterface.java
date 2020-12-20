package DataStructure;

/**
 * Created by IntelliJ IDEA.
 * 定义通用的二叉树接口
 * @Author : ASUS
 * @create 2020/12/19 13:45
 */
public interface TestBinaryTreeInterface<E> {
    void insert(E x);

    void remove(E x);

    boolean contains(E x);

    boolean isEmpty();

    void makeEmpty();

    void printTree();
}
