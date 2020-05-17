package com.dmf.boot.learn.test;

import org.junit.Test;

/**
 * @author dmf
 * @date 2018/8/7
 *
 * 值传递和引用传递本质都是拷贝，值传递拷贝的是实际值，引用传递拷贝的地址值
 * 所以当我们修改值传递的值，传递过来的值是不会发生变化的
 * 引用传递是地址拷贝，当我们修改地址里面的内容，地址里面的内容会发生改变
 * 而直接改变传递过来的地址是不会影响原来的对象地址
 * 思考：为什么java不想c语言直接传递引用地址过来呢？
 *
 * java只有值传递，这句话的意思从底层来看，就是栈上的东西永远会被复制。int存于栈上所以传参的时候会被复制。
 * 对于object，他的引用存于栈上，但是实例在托管堆上，复制的是它的引用，实例本身并没有被复制，所以效果相当于引用传值。
 * 在子函数里面，对实例本身的任何操作，比如调用其方法，都会影响外面，但是如果你重新new了一次，不会影响到外面的。
 */
public class ReferenceTest {


    @Test
    public void test3(){
        Person person = new Person("a");
        System.out.println(person);
        change(person);
        System.out.println(person);
        change1(person);
        System.out.println(person);
    }

    private static void change(Person person) {
        person = new Person("bb");
    }
    private static void change1(Person person) {
        person.name="cc";
    }

    @Test
    public void tees1(){
        int a = 10;
        add(a);
        System.out.println(a);
    }

    private static void add(int a) {
        ++a;
    }


    @Test
    public void test2(){
        char[] c1 = {'a','b'};
        System.out.println("c1:"+c1[0]+" "+c1[1]);
        changer1(c1);
        System.out.println("c1 change1 after:"+c1[0]+" "+c1[1]);
        changer2(c1);
        System.out.println("c1 change2 after:"+c1[0]+" "+c1[1]);
    }

    private static void changer1(char[] c1) {
        char[] c2 = {'e','f'};
        c1 = c2;
    }

    private static void changer2(char[] c1) {
        char[] c2 = {'e','f'};
        c1[0] = c2[0];
    }

    static class Person{
        String name;

        public Person(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
