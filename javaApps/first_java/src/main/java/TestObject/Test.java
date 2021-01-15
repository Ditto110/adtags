package TestObject;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : ASUS
 * @create 2021/1/12 21:14
 */
public class Test {
    public static void main(String[] args) {
        new TestClass1().printValue();
        new TestClass2().printValue();

        System.out.println(new TestClass2().equals(new TestClass1()));
    }
}
