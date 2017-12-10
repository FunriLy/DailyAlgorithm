package com.qg.exmaple.beautyofprogramming.code109;

/**
 * 编程之美 1-9 : 高效率地安排见面会
 * @author FunriLy
 * Created by FunriLy on 2017/12/9.
 * From small beginnings comes great things.
 */
public class Test109 {

    /*
    原文链接 : http://blog.csdn.net/wangzhiyu1980/article/details/50598346
    这里只是将原作者的 C 语言版本改为 Java 版本，以及添加注释
     */

    /**
     * 见面会次数
     */
    private static final int MEETING = 5;
    /**
     * 最大颜色数
     */
    private static final int MAX_COLOR= 6;
    /**
     * 颜色种类
     */
    private static final char[] COLORS = {'N', 'R', 'G', 'B', 'Y', 'W'};
    /**
     * 学生意向表
     */
    private static final int[][] MEETINGS ={
            {0,1,1,1,1},
            {1,0,1,0,0},
            {1,1,0,1,1},
            {1,0,1,0,1},
            {1,0,1,1,0},
    };
    /**
     * 情况记录
     */
    private static final int[] RESULT = {0,0,0,0,0};

    /**
     * 对当前颜色是否符合要求进行检查
     * @param meeting 表示第几场见面会
     * @param color 当前颜色
     * @return 若颜色满足分配返回true，否则返回false
     */
    private static boolean check(int meeting, int color) {
        for (int i=0; i<MEETING; i++) {
            // 如果同学对于当前此场见面会不感兴趣，或者当前时间已经安排了(颜色相同)
            if ((MEETINGS[i][meeting] != 0) && (color == RESULT[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 对见面会进行安排
     * 即求最少着色问题
     */
    private static void compute() {
        for (int i=0; i<MEETING; i++) {
            if (RESULT[i] == 0) {
                // 如果当前此场见面会还没有安排
                for (int j=1; j<MAX_COLOR; j++) {
                    // 枚举各种颜色进行检查
                    if (!check(i, j)) {
                        continue;
                    } else {
                        RESULT[i] = j;
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        compute();
        for (int i=0; i<MEETING; i++) {
            int k = i+1;
            System.out.print("第"+k+"个点染色为"+COLORS[RESULT[i]]+"  ");
        }
    }

    /*
    扩展问题 1 : 利用贪心算法求出最大相互兼容面试集合(时间两两不重叠，可以在同一个面试点进行)
    注意 : 这种算法的时间复杂度太大了，理论上可以认为是 n^n，不过还是很好理解的
    1、将面试集合中的元素按照其结束时间进行递增顺序排列。
    2、遍历找到最大相互兼容集合整合，并从原集合中删除。
    3、重复第2步操作，找到原集合为空集。
    4、统计求出最大相互兼容集合的个数，即是所求最少面试点个数。
     */
}
