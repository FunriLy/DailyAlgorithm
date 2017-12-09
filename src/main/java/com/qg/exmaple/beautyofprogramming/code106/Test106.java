package com.qg.exmaple.beautyofprogramming.code106;

import java.util.Arrays;
import java.util.Random;

/**
 * 编程之美 1-6 : 饮料供货
 * @author FunriLy
 * Created by FunriLy on 2017/12/8.
 * From small beginnings comes great things.
 */
public class Test106 {

    public class Drinks {
        /**
         * 饮料的满意度
         */
        public int value;
        /**
         * 饮料的容量
         */
        public int capacity;
        /**
         * 饮料的数量
         */
        public int count;

        public Drinks(int value, int capacity, int count) {
            this.value = value;
            this.capacity = capacity;
            this.count = count;
        }

        @Override
        public String toString() {
            return "Drinks{" +
                    "value=" + value +
                    ", capacity=" + capacity +
                    ", count=" + count +
                    '}';
        }
    }

    private static final Random RANDOM = new Random();

    /**
     * 动态规划算法获得最优解
     * 可以使用备忘录变形动态规划算法来提高算法效率
     * @param drinkses 饮料信息记录
     * @param v 饮料的总量
     * @param t 饮料的种类
     * @return 求饮料的最佳分配情况
     */
    private static int cal(Drinks[] drinkses, int v, int t ) {
        if (drinkses == null || drinkses.length <= 0) {
            throw new RuntimeException("非法输入！！！");
        }
        // 非法参数
        if (v <= 0 || t <=0 || t>drinkses.length) {
            return -1;
        }

        // 二维数组，动态规划记录
        int[][] opt = new int[v+1][t+1];
        // (i,j) 代表在容量为i的情况下，从j、j+1、……、n-1种饮料下的最优解
        // 本题的推导公式为 opt[i][j] = max{k*满意度 + opt[i - k*饮料容量][j+1]}
        // 求 opt[v][0]

        // 首先容量为0时，初始化满意度为0
        opt[0][t] = 0;
        // 确定边界条件
        for (int i=1; i<=v; i++) {
            // 设置为-1，确保不会被访问到
            opt[i][t] = -1;
        }

        for (int j=t-1; j>=0; j--) {
            for (int i=0; i<=v; i++) {
                // 遍历到(i,j)元素，先对其进行初始化
                opt[i][j] = -1;
                // k 表示遍历到第 j 种饮料时选取数量为 k
                for (int k=1; k<=drinkses[j].count; k++) {
                    // 如果 数量*单位容量 > 总容量
                    if (i < k*drinkses[j].capacity) {
                        break;
                    }
                    // 获得前一种情况的最优解
                    int x = opt[i - k*drinkses[j].capacity][j+1];
                    if (x != -1) {
                        x += (k * drinkses[j].value);
                        if(x >opt[i][j]) {
                            // 更新本次情况的最优解
                            opt[i][j] = x;
                        }
                    }
                }
            }
        }
        drinkPrint(opt);
        return opt[v][0];
    }


    /**
     * 初始化饮料列表
     * @param v 饮料总量
     * @param t 饮料种类
     * @return 初始化饮料数组
     */
    private  Drinks[] initDrinks(int v, int t) {
        if (v <=0 || t <= 0) {
            throw new RuntimeException("非法输入！！！");
        }
        Drinks[] drinkses = new Drinks[t+1];
        // 随机生成数据
        for (int i=0; i<t; i++) {
            int value = RANDOM.nextInt(t) + 1;
            int capacity = RANDOM.nextInt(t) + 1;
            int count = RANDOM.nextInt(t) + 1;
            Drinks drinks = new Drinks(value, capacity, count);
            // 为方便计算，从1开始计数
            drinkses[i] = drinks;
        }
        for(Drinks d : drinkses) {
            System.out.println(d);
        }
        System.out.println("==================");
        return drinkses;
    }

    /**
     * 二维矩阵打印
     * @param opt 二维矩阵
     */
    private static void drinkPrint(int[][] opt) {
        if (opt != null) {
            for(int[] row : opt) {
                System.out.println(Arrays.toString(row));
            }
        }
    }

    public static void main(String[] args) {
        int t = 3;
        int v = 10;
        Drinks[] drinkses = new Test106().initDrinks(v, t);
        int maxValue =    cal(drinkses, v, t);
        System.out.println("最大满意度 : " + maxValue);
    }
}
