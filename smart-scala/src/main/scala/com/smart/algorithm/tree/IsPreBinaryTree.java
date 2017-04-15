package com.smart.algorithm.tree;

import java.util.LinkedList;

/**
 * Created by  fc.w on 2017/2/9.
 */
public class IsPreBinaryTree {

    public static void main(String[] args) {
        String str = "9,3,4,#,#,1,#,#,2,#,6,#,#";
        boolean result = isValidSerialization(str);
        System.out.println(result);
    }

    public static boolean isValidSerialization(String preorder) {
        LinkedList<String> stack = new LinkedList<String>();
        String[] arr = preorder.split(",");

        for (int i = 0; i < arr.length; i++) {
            stack.add(arr[i]);
            while (stack.size() >= 3 && stack.get(stack.size() - 1).equals("#") && stack.get(stack.size() - 2).equals("#") && !stack.get(stack.size() - 3).equals("#")) {
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);
                stack.remove(stack.size() - 1);

                stack.add("#");
            }
        }

        if (stack.size() == 1 && stack.get(0).equals("#")) {
            return true;
        } else {
            return false;
        }

    }
}
