package com.dmf.boot.learn.algorithms;

import org.junit.Test;

/**
 * @author dmf
 * @date 2018/8/9
 */
public class StringTest {

    /**
     * 将空格替换成%20
     * we are happy
     */
    public String replaceSpace(StringBuffer str) {
        return str.toString().replaceAll(" ", "%20");
    }

    @Test
    public void test1() {
        String str = replaceSpace(new StringBuffer("we are happy"));
        System.out.println(str);
    }

    /**
     * java里面\需要用\\表示，所以一开始java\c++\python需要用java\\c++\\python表示
     * 分析replaceAll方法如何实现：
     * 使用正则匹配，通过判断是否找到，截取找到的索引位置，在替换成需要的字符，追加，
     * 记录上一次找到位置，下次从这里开始截取，追加
     */
    @Test
    public void test2() {
        String s = "java\\c++\\python";
        //解析：regex  \\\\ 在程序解析成 \\,然后正则表达式 \ 表示转移符，所以 \\ 表示 \
        //解析：replacement \\\\\\\\  ==> \\\\,然后在matcher里面替换时 \\或者$ 会被转义
        //然后进行字符替换，\\\\,第二和第四\\,然后截取以前的字符+替换后的字符
        //while循环替换

        //String sr1 = s.replaceAll("\\\\", "\\\\\\\\");
        String sr1 = s.replaceAll("\\\\", "$0$0");
        System.out.println(sr1);
    }

    @Test
    public void test3() {
        //将String s = "\\\\" 替换成 Strign s = "\\\\\\";
        /*String s = "\\\\";
        System.out.println(s);
        String s1 = "\\\\\\";
        System.out.println(s1);
        //简单的说就是把两个\\变成\\\
        String s2 = s.replaceAll("\\\\\\\\", "\\\\\\\\\\\\");
        System.out.println(s2);*/

        String s = "aaa\\\\bbb";
        String sr1 = s.replaceAll("\\\\", "\\\\\\\\\\\\");
        System.out.println("sr1:" + sr1);
    }

    @Test
    public void test4() {
        //将String s = "\\" 替换成 String s = "$";
        String s = "\\";
        String s1 = s.replaceAll("\\\\", "\\$");
        System.out.println(s1);
    }

    @Test
    public void test5() {
        //String s = "Jack is Rose's boyfriends." 替换成 String sr = "Rose is Jack's girlfriends." 使用$分组
        String s = "Jack is Rose's boyfriends.";
        String s1 = s.replaceAll("(Jack) is (Rose)'s boyfriends.", "$2 is $1's girlfriends");
        System.out.println(s1);
    }

    @Test
    public void test6() {
        String htmlStr = "aaaa<script>asdsdwerewr</script>bbbbbb";
        String s = htmlStr.replace("a", "b");
        System.out.println(s);
        // 定义script的正则表达式
        /*String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>";
        String str = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE).matcher(htmlStr).replaceAll("");
        System.out.println(htmlStr);*/

    }

    @Test
    public void test7() {
        String s = "java";
        String replace = s.replace("a", "$");
        System.out.println(replace);
    }

}
