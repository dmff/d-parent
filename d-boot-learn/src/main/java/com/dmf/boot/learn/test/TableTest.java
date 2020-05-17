package com.dmf.boot.learn.test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Map;

/**
 * @author dmf
 * @date 2019/7/21
 *
 * guava中table表格的使用用法：https://blog.csdn.net/qq_34310242/article/details/76222015
 *
 */
public class TableTest {

    public static void main(String[] args) {
        Table<Integer,Integer,String> table = HashBasedTable.create();
        table.put(1, 1, "table1");
        table.put(1, 2, "table2");
        table.put(1, 3, "table3");
        table.put(2, 1, "table4");
        table.put(2, 2, "table5");
        table.put(2, 3, "table6");
        table.put(3, 1, "table7");
        table.put(3, 2, "table8");
        table.put(3, 3, "table9");

        //通过X、Y坐标获取指定元素
        System.out.println("通过X、Y坐标获取指定元素 ："+table.get(2, 2));

        //所有的列 的 键
        System.out.println("所有的列 的 键："+table.columnKeySet());

        Map<Integer, Map<Integer, String>> columnMap = table.columnMap();
        System.out.println("将每一列划分为一个map:"+columnMap);

        //获取具体的某一列的map
        Map<Integer,String> column2Map = table.column(2);
        System.out.println("获取具体的某一列的map:"+column2Map);

        //所有的行 的 键
        System.out.println("所有的行 的 键:"+table.rowKeySet());

        //将每一行划分为一个map
        Map<Integer,Map<Integer,String>> rowMaps = table.rowMap();
        System.out.println("将每一行划分为一个map:"+rowMaps);

        //获取集体的某一行的map
        Map<Integer, String> row2Map = table.row(2);
        System.out.println("获取集体的某一行的map:"+row2Map);

    }





}
