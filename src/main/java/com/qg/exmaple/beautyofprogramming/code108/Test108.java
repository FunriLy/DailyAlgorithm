package com.qg.exmaple.beautyofprogramming.code108;

/**
 * 编程之美 1-8 : 小飞的电梯调度算法
 * @author FunriLy
 * Created by FunriLy on 2017/12/9.
 * From small beginnings comes great things.
 */
public class Test108 {

    /**
     * 解法1：采用两重循环统计每层的总数，取最小值
     * @param nPerson 乘客数组，nPerson[i-1]代表到第i层乘客数目
     * @return 最佳楼层
     */
    private static int getStayFloor1(int[] nPerson) {
        if (nPerson == null || nPerson.length<1) {
            return -1;
        }

        int n = nPerson.length;

        int minStep = Integer.MAX_VALUE;
        int targetFloor = 0;
        for (int i=0; i<n; i++) {
            // 记录所需的步数
            int stepNum = 0;
            for (int j=0; j<i; j++) {
                // 若停留楼层下方所需步数
                stepNum += nPerson[j] * (i-j);
            }
            for (int j=i; j<n; j++) {
                // 若停留楼层上方所需步数
                stepNum += nPerson[j] * (j-i);
            }
            // 如果当前目标楼层是-1(即还未更新)，或者有更优解的情况下，更新数据
            if (targetFloor == -1 || minStep > stepNum) {
                minStep = stepNum;
                targetFloor = i;
            }
        }
        return targetFloor+1;
    }

    /**
     * 解法2：采用推理公式计算最佳楼层
     * @param nPerson 乘客数组，nPerson[i-1]代表到第i层乘客数目
     * @return 最佳楼层
     */
    private static int getStayFloor2(int[] nPerson) {
        if (nPerson == null || nPerson.length<1) {
            return -1;
        }
        int n = nPerson.length;
        // 表示第i层以下乘客总数
        int n1 = 0;
        // 表示第i层乘客总数
        int n2 = nPerson[0];
        // 表示第i层以上乘客总数
        int n3 = 0;
        int minStep = 0;
        // 目标楼层
        int targetFloor = 0;

        // 初始化n3
        for (int i=1; i<n ; i++) {
            n3 += nPerson[i];
            minStep += nPerson[i]*(i-1);
        }

        // 从第2楼开始推理
        for (int i=1; i<n; i++) {
            // 详细推理内容见书上解析
            if (n1 + n2 < n3) {
                targetFloor = i;
                minStep += (n1 + n2 -n3);
                n1 += n2;
                n2 = nPerson[i];
                n3 -= nPerson[i];
            } else {
                break;
            }
        }
        System.out.println("所需步数" + minStep);
        return targetFloor+1;
    }

    /**
     * 解法3：采用求解数组中位数的方法
     * @param nPerson 乘客数组，nPerson[i-1]代表到第i层乘客数目
     * @return 最佳楼层
     */
    private static int getStayFloor3(int[] nPerson) {
        if (nPerson == null || nPerson.length<1) {
            return -1;
        }

        int n = nPerson.length;
        int[] copyPerson = new int[n];
        System.arraycopy(nPerson, 0, copyPerson, 0, n);

        int left = 0, right = n-1;
        while (right-left > 1) {
            while(copyPerson[left] == 0){
                left++;
            }
            copyPerson[left] --;
            while(copyPerson[right] == 0){
                right--;
            }
            copyPerson[right] --;
        }
        if (copyPerson[right] > copyPerson[left]) {
            return right+1;
        } else {
            return left+1;
        }
    }




    public static void main(String[] args) {
        int[] nPerson = {1,2,3,4,4,6};
        System.out.println(getStayFloor1(nPerson));
        System.out.println(getStayFloor2(nPerson));
        System.out.println(getStayFloor3(nPerson));
    }
}
