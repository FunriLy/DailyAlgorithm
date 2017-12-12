package com.qg.exmaple.beautyofprogramming.code111;

import java.util.ArrayList;
import java.util.List;

/**
 * 编程之美 1-13 : NIM(3) 两堆石头的游戏及其扩展问题
 * @author FunriLy
 * Created by FunriLy on 2017/12/12.
 * From small beginnings comes great things.
 */
public class Test113 {

    /**
     * 压缩列表空间
     * @param list 给定列表
     * @param n 压缩数
     */
    private static void shrinkArray(List<Integer> list, int n) {
        for (int i=0; i<list.size(); i++) {
            if (list.get(i) > n) {
                for (int j=0; j<n; j++) {
                    list.remove(j);
                }
            }
        }
    }

    /*
    解法 1 : 推理定义
    1、a1 = 1, b1 = 2;
    2、若a1、b1、……a(n-1)、b(n-1)已经求得，则a(n)为未出现在这个队列中的最小整数;
    3、b(n) = a(n) + n。
     */

    /**
     * 解法 1 : 利用自顶向下的推论计算坐标是否安全
     * @param x 坐标 x
     * @param y 坐标 y
     * @return 若坐标安全返回 true，负责返回 false
     */
    private static boolean nim1(int x, int y) {
        if (x < 0 || y < 0) {
            throw new RuntimeException("非法输入！！！");
        }
        if (x == y) {
            // 安全局面
            return true;
        }
        if (x > y && y !=0) {
            int temp = x;
            x = y;
            y = temp;
        }
        int a1 = 1, b1 = 2;
        if (x == a1 && y == b1) {
            return false;
        }

        // 用于存储 y 值的列表
        List<Integer> bList = new ArrayList<>();
        // 代表当前推理 x 值
        int n = 1;
        int delta = 1;
        bList.add(2);

        while (x > n) {
            while (bList.indexOf(++n) != -1){
                if (n > x) {
                    throw new RuntimeException("运行错误！！！");
                }
            }
            delta++;
            bList.add(n + delta);
            if (bList.size() > 2 && bList.size() > 100) {
                // 删除列表规模
                shrinkArray(bList, 0);
            }
        }
        // 如果推论数不等于 x，或者数组中不存在当前 y 值
        return x != n || (bList.indexOf(y) == -1) ;
    }

    /*
    解法 2 的求证详细见书中证明！
     */

    /**
     * 解法 2 : 利用公式进行求解坐标是否安全
     * @param x 坐标 x
     * @param y 坐标 y
     * @return 若坐标安全返回 true，负责返回 false
     */
    private static boolean nim2(int x, int y) {
        if (x < 0 || y < 0) {
            throw new RuntimeException("非法输入！！！");
        }

        double a, b;
        a = (1 + Math.sqrt(5.0)) / 2;
        b = (3 + Math.sqrt(5.0)) / 2;
        if (x == y) {
            return true;
        }
        if (x > y) {
            int temp = x;
            x = y;
            y = temp;
        }
        int n = x;
        while (n >= 1) {
            // 序号n一定要小于等于min(x, y)
            if ((int)Math.floor(a*n) == x && (int)Math.floor(b*n) == y) {
                return false;
            }
            n--;
        }
        return true;
    }

    /**
     * 扩展问题 1 : 由目前的坐标，求出下一步必胜走法
     * @param x 坐标x
     * @param y 坐标y
     * @param result 存储下一步的走法
     */
    private static void getResult(int x, int y, int[] result) {
        if (x < 0 || y < 0 || result.length != 2) {
            throw new RuntimeException("非法输入！！！");
        }
        int min, max;
        if (x >= y) {
            min = y;
            max = x;
        } else {
            min = x;
            max = y;
        }

        if (x == y) {
            result[0] = 0;
            result[1] = 0;
            return;
        }
        // 如果当前 x、y 为安全坐标
        if (nim2(x, y)) {
            // 从 1- min 开始取数，得到第一个不安全局面
            for (int i=1; i<=min; i++) {
                // 从两堆石头中取共同的数
                if (!nim2(x-i, y-i)) {
                    result[0] = x - i;
                    result[1] = y - i;
                    return;
                }
                // 从一堆石头中取任意的数
                if (!nim2(x-i, y)) {
                    result[0] = x - i;
                    result[1] = y;
                    return;
                }
                if (!nim2(x, y-i)) {
                    result[0] = x;
                    result[1] = y - i;
                    return;
                }
            }

            // 从 min+1 ~ max 开始取数，得到第一个不安全局面
            for (int i=min+1; i<=max; i++) {
                // 从數量多的一堆石头中取任意的数
                if (x > y) {
                    if (!nim2(x-i, y)) {
                        result[0] = x - i;
                        result[1] = y;
                        return;
                    }
                } else {
                    if (!nim2(x, y-i)) {
                        result[0] = x;
                        result[1] = y - i;
                        return;
                    }
                }
            }
        } else {
            result[0] = -1;
            result[1] = -1;
        }
    }

    /*
    扩展问题 2 : 书中提示使用 Fibonacci 数列
     */

    /**
     * 扩展问题 2 : 由给定的石头块数，根据新规则求解有无必胜的策略
     * @param n 石头块数
     * @return 若有必胜的策略返回true，否则返回false
     */
    private static boolean nim(int n) {
        int f1=1, f2=1;
        int f3;
        if (n <= 1) {
            throw new RuntimeException("非法输入！！！");
        }

        do {
            f3 = f1 + f2;
            f1 = f2;
            f2 = f3;
        } while (f3 < n);

        return f3 != n;
    }


    public static void main(String[] args) {
        // 解法 1、2的对比
        System.out.println("解法 1、2的对比 :");
        System.out.println("x=3, y=6 :" + nim1(3, 6) + " " + nim2(3, 6));
        System.out.println("x=4, y=7 :" + nim1(4, 7) + " " + nim2(4, 7));
        System.out.println("===========================");
        // 扩展问题 1
        System.out.println("扩展问题 1 :");
        int[] result1 = {-1, -1};
        getResult(3, 6, result1);
        System.out.println("x=3, y=6 -> x=" + result1[0] + ", y=" + result1[1]);
        int[] result2 = {-1, -1};
        getResult(4, 7, result1);
        System.out.println("x=4, y=7 -> x=" + result2[0] + ", y=" + result2[1]);
        System.out.println("===========================");
        System.out.println("扩展问题 2 :");
        System.out.println("2 : " + nim(2));
        System.out.println("3 : " + nim(3));
        System.out.println("4 : " + nim(4));
        System.out.println("5 : " + nim(5));
        System.out.println("10 : " + nim(10));
    }
}
