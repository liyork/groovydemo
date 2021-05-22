package com.wolf.java;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Description:
 * Created on 2021/4/18 7:35 PM
 *
 * @author 李超
 * @version 0.0.1
 */
public class Test {
    void myMethod() throws IllegalArgumentException {
        throw new IllegalArgumentException();
    }

    public static void main(String[] args) {
        testCollectionRemove();
    }

    private static void testCollectionRemove() {
        ArrayList<String> lst = new ArrayList<String>();
        Collection<String> col = lst;
        lst.add("one");
        lst.add("two");
        lst.add("three");
        lst.remove(0);
        col.remove(0);// 由于参数是Object，装箱导致以为删除Integer(0)
        System.out.println("number of elements is:" + lst.size());
        System.out.println("number of elements is:" + col.size());
    }
}
