package com.qg.exmaple.beautyofprogramming.code103;

/**
 * 编程之美 1-3 : 一摞烙饼的排序
 * @author FunriLy
 * Created by FunriLy on 2017/12/5.
 * From small beginnings comes great things.
 */
public class Test103 {
    public static void main(String[] args) {
        CPrefixSorting problem = new CPrefixSorting();
        int[] cakeArray = {3, 2, 1, 6, 5, 4};
        problem.run(cakeArray);
        problem.print();
    }
}

class CPrefixSorting {
    /**
     * 饼的个数
     */
    private int mnCake;
    /**
     * 初始化输入
     */
    private int[] mCakeArray;
    /**
     * 最大的交换次数
     */
    private int mnMaxSwap;
    /**
     * 存储交换位置信息数组
     */
    private int[] mSwapArray;
    /**
     * 当前翻转信息数组
     */
    private int[] mReverseCakeArray;
    /**
     * 当前存储交换位置信息数组
     */
    private int[] mReverseCakeArraySwap;
    /**
     * 搜索次数
     */
    private int mnSearch;

    public CPrefixSorting() {
        mnCake = 0;
        mnMaxSwap = 0;
    }

    private void init(int[] pCakeArray) {
        if(pCakeArray == null || pCakeArray.length < 1) {
            throw new RuntimeException("非法输入!!!");
        }
        mnCake = pCakeArray.length;
        mCakeArray = new int[mnCake];
        // 初始化数组
        System.arraycopy(pCakeArray, 0, mCakeArray, 0, mnCake);
        mnMaxSwap = upperBound(mnCake);
        // 初始化各类辅助数组
        mSwapArray = new int[mnMaxSwap + 1];
        mReverseCakeArray = new int[mnCake];
        mReverseCakeArraySwap = new int[mnMaxSwap + 1];

        // 复制数组，初始化中间交换结果信息
        System.arraycopy(mCakeArray, 0, mReverseCakeArray, 0, mnCake);
    }

    /**
     * 最大上界：可以翻转的最大次数
     * @param nCake 烙饼个数
     * @return 最大翻转次数
     */
    private int upperBound(int nCake) {
        return 2 * nCake;
    }

    /**
     * 最小下界：翻转的最小次数
     * @param pCakeArray 翻转的数组
     * @param nCake 烙饼个数
     * @return 翻转最小次数
     */
    private int lowerBound(int[] pCakeArray, int nCake) {
        int t, lower = 0;
        for (int i=1; i<nCake; i++) {
            t = pCakeArray[i] - pCakeArray[i-1];
            if ((t != 1) && (t != -1)) {
                lower++;
            }
        }
        return lower;
    }

    /**
     * 排序主函数
     * @param step 第几步
     */
    private void search(int step) {
        // 最小交换次数估计
        int minEstimate;
        mnSearch++;
        // 估算此次搜索所需虽小交换次数
        minEstimate = lowerBound(mReverseCakeArray, mnCake);
        if (step + minEstimate > mnMaxSwap || step >= mnMaxSwap) {
            return;
        }

        // 如果已经排序好了
        if (isSorted(mReverseCakeArray)) {
            if (step < mnMaxSwap) {
                mnMaxSwap = step;
                for (int i=0; i<mnMaxSwap; i++) {
                    mSwapArray[i] = mReverseCakeArraySwap[i];
                }
            }
            return;
        }

        // 进行翻转
        for (int i=1; i<mnCake; i++) {
            reverse(0, i);
            // 记录每次翻转的位置信息
            mReverseCakeArraySwap[step] = i;
            search(step + 1);
            reverse(0, i);
        }
    }

    /**
     * 判断数组是否已经从小到大排序好了
     * @param pCakeArray 判断数组
     * @return 若数组已经排序返回true，否则返回false
     */
    private boolean isSorted(int[] pCakeArray) {
        for (int i=1; i<pCakeArray.length; i++) {
            if (pCakeArray[i] < pCakeArray[i-1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * 翻转烙饼
     * @param nBegin 起始索引
     * @param nEnd 终止索引
     */
    private void reverse(int nBegin, int nEnd) {
        int t;
        for (int i=nBegin, j=nEnd; i < j; i++, j--) {
            t = mReverseCakeArray[i];
            mReverseCakeArray[i] = mReverseCakeArray[j];
            mReverseCakeArray[j] = t;
        }
    }

    /**
     * 输出函数
     * 1、翻转的位置信息
     * 2、搜索次数
     * 3、翻转次数
     * 4、每一次翻转情况
     */
    public void print() {
        for(int num : mSwapArray) {
            System.out.print(num + " ");
        }
        System.out.println("");
        System.out.println("Search Time : " + mnSearch);
        System.out.println("Total Swap Time : " + mnMaxSwap);

        perReverseArrayOutput(mCakeArray, mSwapArray, mnCake, mnMaxSwap);
    }

    /**
     * 通过记录每次翻转位置的数组，输入每次翻转的数组显示每一步的翻转结果
     * @param cakeArray 最初要排序的数组
     * @param swapArray 记录每次翻转时翻转的位置
     * @param nCake 数组长度
     * @param maxSwap 翻转次数
     */
    private static void perReverseArrayOutput(int[] cakeArray, int[] swapArray, int nCake, int maxSwap) {
        int t;
        System.out.println(maxSwap);
        for (int i=0; i<maxSwap; i++) {
             System.out.print(swapArray[i] + " - ");
            for (int x=0, y=swapArray[i]; x<y; x++, y--) {
                t = cakeArray[x];
                cakeArray[x] = cakeArray[y];
                cakeArray[y] = t;
            }

            for (int k=0; k<nCake; k++) {
                System.out.print(cakeArray[k] + " ");
            }
            System.out.println("");
        }
    }

    public void run(int[] pCakeArray) {
        init(pCakeArray);
        mnSearch = 0;
        search(0);
    }
}
