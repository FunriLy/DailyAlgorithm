package com.qg.exmaple.beautyofprogramming.code105;

/**
 * 编程之美 1-5 : 快速找出故障机器
 * @author FunriLy
 * Created by FunriLy on 2017/12/7.
 * From small beginnings comes great things.
 */
public class Test105 {

    /*
    问题描述:
    给定一个数组，数组中的数一般是成对出现的，找出其中之出现一次的数。
    若数组只出现一次的数只有一个？有两个所求的数？有多个符合条件的数？
     */

    /*
    解法一: 时间复杂度O(n)、空间复杂度O(n)
    利用一个数组记录每个数出现的次数，遍历完毕后就能找到所求的数。

    解法二: 时间复杂度O(n)、空间复杂度O(1~n)
    利用Map来记录元素出现次数，若元素出现超过两次就将其从集合中删除以减少所需空间。

    解法三: 时间复杂度O(n)、空间复杂度O(1)
    若所求元素只有一个:将所有元素进行异或操作，最终得到所求元素。
    若所求元素不止一个:先将所有元素进行异或操作，利用结果进行分组。若有多少个所求元素就有多少个分组，最终归结到只有一个元素的解法、
     */


    /*
    解法四: 时间复杂度O(n)、空间复杂度O(1)
    注意:这里只使用于本题，需要知道原来和输出的数组。
    所有ID的和为一个不变量，对现在剩下ID求和。所有ID的和与剩下ID的和之差即为所求ID。
     */

    // 参考资料 :  《剑指 offer》数组中只出现一次的数字(升级版)
    // https://github.com/FunriLy/Sword_Offer/blob/master/src/main/java/com/qg/funrily/offer40/Test40.java
}
