package com.qg.exmaple.beautyofprogramming.code116;

import java.util.*;

/**
 * 编程之美 1-16 : 24 点游戏
 * @author FunriLy
 * Created by FunriLy on 2017/12/14.
 * From small beginnings comes great things.
 */
public class Test116 {

    /**
     * 移除浮点数运算精度误差
     */
    private static final double THRESHOLD = 1e-6;
    private static final int CARDS_NUMBER = 4;
    private static final int RESULT_VALUE = 24;
    /**
     * 用于记录数组元素
     */
    private static double[] NUMBER = new double[CARDS_NUMBER];
    /**
     * 用于记录表达式
     */
    private static String[] RESULT = new String[CARDS_NUMBER];

    /*
    解法 1 : 穷举法，计算所有可能，找到符合游戏规则的解。
    理论上，完成完整的遍历，共有 4! * 4^3 * 5 = 7680 种可能
    1、从数组中抽取两个数，分别计算在当前运算符下(+ - * /)的结果;
    2、将选定的数移出数组，并将步骤1计算结果加入数组;
    3、对新数组进行递归调用，若找到正确结果就返回;
    4、将步骤1的结果从数组中移出，并还原数组。
     */

    /**
     * 将集合中n个元素进行四则混合运算
     * @param n 元素个数
     * @return 若四则运算结果能够满足游戏规则返回 true，否则返回 false
     */
    private static boolean pointsGame(int n) {
        if (n < 1) {
            throw new RuntimeException("非法输入！！！");
        }
        if (n == 1) {
            return Math.abs(NUMBER[0] - RESULT_VALUE) < THRESHOLD ;
        }

        // 任意抽取两个数字
        for (int i=0; i<n; i++) {
            for (int j=i+1; j<n; j++) {
                String expA, expB;
                double a, b;

                a = NUMBER[i];
                b = NUMBER[j];
                NUMBER[j] = NUMBER[n-1];

                expA = RESULT[i];
                expB = RESULT[j];
                RESULT[j] = RESULT[n-1];

                // 减支
                if (a > b) {
                    RESULT[i] = "(" + expA + "+" + expB + ")";
                    NUMBER[i] = a + b;
                    if (pointsGame(n - 1)) {
                        return true;
                    }
                }

                RESULT[i] = "(" + expA + "-" + expB + ")";
                NUMBER[i] = a - b;
                if (pointsGame(n-1)) {
                    return true;
                }

                RESULT[i] = "(" + expB + "-" + expA + ")";
                NUMBER[i] = b - a;
                if (pointsGame(n-1)) {
                    return true;
                }

                // 减支
                if (a > b) {
                    RESULT[i] = "(" + expA + "*" + expB + ")";
                    NUMBER[i] = a * b;
                    if (pointsGame(n - 1)) {
                        return true;
                    }
                }

                RESULT[i] = "(" + expA + "/" + expB + ")";
                NUMBER[i] = a / b;
                if (pointsGame(n-1)) {
                    return true;
                }

                RESULT[i] = "(" + expB + "/" + expA + ")";
                NUMBER[i] = b / a;
                if (pointsGame(n-1)) {
                    return true;
                }
                // 还原数组
                NUMBER[i] = a;
                NUMBER[j] = b;
                RESULT[i] = expA;
                RESULT[j] = expB;
            }
        }
        return false;
    }

    /**
     * 解法 1 ：穷举法
     * 将给定数组进行24点计算
     * @param number 给定数组
     */
    private static void  game24A(int[] number) {
        if (number == null || number.length != CARDS_NUMBER) {
            throw new RuntimeException("非法输入！！！");
        }
        // 初始化
        for (int i=0; i<CARDS_NUMBER; i++) {
            NUMBER[i] = number[i];
            RESULT[i] = String.valueOf(number[i]);
        }
        System.out.println(pointsGame(CARDS_NUMBER));
    }


    /*
    解法 2 : 动态规划算法
    参考资料 : http://blog.csdn.net/xiang_ma/article/details/8858214
    将整个整体看做一个集合，将集合分成两个小集合，计算其组成可能性。
    如此递归，找到集合内元素为1.
     */

    private static class Elem {
        /**
         * 运算数据
         */
        private double res;
        /**
         * 运算过程
         */
        private String info;

        private Elem(double res, String info) {
            this.res = res;
            this.info = info;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Elem elem = (Elem) o;
            return (Double.compare(elem.res, res) == 0) && (info != null ? info.equals(elem.info) : elem.info == null);

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(res);
            result = (int) (temp ^ (temp >>> 32));
            result = 31 * result + (info != null ? info.hashCode() : 0);
            return result;
        }
    }

    /**
     * 记录每一种运算的可能结果，防止重复计算
     */
    private static Map<Integer, Set<Elem>> map;
    /**
     * 用于存储解法表达式
     */
    private static Set<String> answers;

    /**
     * 将整个集合分成两个集合计算其可能性结果
     * @param m 元素标记，如 m=6(0110)，代表包含了第2、3个元素
     * @return 返回包含当前元素标记的可能性结果
     */
    private static Set<Elem> fork(int m) {
        Set<Elem> mSet = map.get(m);
        if (mSet == null || mSet.size() > 0) {
            // 如果当前部分已经计算了，直接返回，
            // 防止重复计算
            return mSet;
        } else {
            int middleNum = m / 2;
            // m/2 体现分治，减少运算量
            for (int i=1; i<=middleNum; i++) {
                // 将集合分成任意两部分
                if ((i&m) == i) {
                    // 分别计算两部分的可能性情况
                    Set<Elem> s1 = fork(i);
                    Set<Elem> s2 = fork(m-i);
                    for (Elem e1 : s1) {
                        for (Elem e2 : s2) {

                            String str = "(" + e1.info + "+" + e2.info + ")";
                            mSet.add(new Elem(e1.res+e2.res, str));

                            str = "("+ e1.info+"-"+e2.info+")";
                            mSet.add(new Elem(e1.res-e2.res, str));

                            str = "("+ e2.info+"-"+e1.info+")";
                            mSet.add(new Elem(e2.res-e1.res, str));

                            str = "("+ e1.info+"*"+e2.info+")";
                            mSet.add(new Elem(e1.res*e2.res, str));

                            if(e1.res!=0) {
                                str = "("+ e2.info+"/"+e1.info+")";
                                mSet.add(new Elem(e2.res/e1.res, str));
                            }
                            if(e2.res!=0){
                                str = "("+ e1.info+"/"+e2.info+")";
                                mSet.add(new Elem(e1.res/e2.res, str));
                            }
                        }
                    }
                }
            }
            return mSet;
        }
    }

    private static boolean run() {
        answers.clear();
        // 用二进制数来表示集合和子集，0110表示集合包含第2,3个数
        // 1 << 4 = 10000 模拟15种情况
        int size = 1 << CARDS_NUMBER;
        for (int i=0; i<size; i++) {
            // 初始化
            map.put(i, new HashSet<>());
        }
        // 将最初的四种情况放入辅助 Map 中
        for (int i=0; i<CARDS_NUMBER; i++) {
            Elem e = new Elem(NUMBER[i], NUMBER[i] + "");
            Set<Elem> set = new HashSet<>();
            set.add(e);
            // 0001 0010 0100 1000
            map.put(1<<i, set);
        }

        // 对于每种情况进行计算
        for (int i=0; i<size; i++) {
            fork(i);
        }
        Set<Elem> mSet = map.get(size-1);
        for (Elem e : mSet) {
            if (e.res == RESULT_VALUE) {
                answers.add(e.info);
                // todo : 去掉此处 break 可以获得所有可能解法
                break;
            }
        }
        return answers.size()>0;
    }

    private static void game24B(int[] number) {
        if (number == null || number.length != CARDS_NUMBER) {
            throw new RuntimeException("非法输入！！！");
        }
        // 初始化
        for (int i=0; i<CARDS_NUMBER; i++) {
            NUMBER[i] = number[i];
        }
        map = new HashMap<>(1<<CARDS_NUMBER);
        answers = new HashSet<>();
        System.out.println(run());
    }

    public static void main(String[] args) {
        int[] number = {11, 8, 3, 5};
        game24A(number);
        System.out.println(RESULT[0]);
        System.out.println("=====================");
        game24B(number);
        System.out.println(Arrays.toString(answers.toArray()));
    }
}
