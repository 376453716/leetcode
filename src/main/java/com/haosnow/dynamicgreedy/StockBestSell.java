package com.haosnow.dynamicgreedy;

import java.util.Arrays;

/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 * <p>
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
 * <p>
 * 注意你不能在买入股票前卖出股票。
 * <p>
 * 示例 1:
 * <p>
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 * 注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格。
 * 示例 2:
 * <p>
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 * @description: 买卖股票的最佳时机
 * @author: xionghao_cd@keruyun.com
 * @create: 2018-11-29 14:00
 **/
public class StockBestSell {
    public static void main(String[] args) {
        int[] prices = {7, 1, 5, 3, 6, 4};
        int[] prices2 = {2, 1, 2, 0, 1};
        System.out.println(maxProfitRecursion(prices));
        System.out.println(maxProfitDynamicGreedy(prices));
        System.out.println(maxProfitRecursion(prices2));
        System.out.println(maxProfitDynamicGreedy(prices2));
    }

    /**
     * 递归查找最大子数组
     *
     * @param prices
     * @return
     */
    public static int maxProfitRecursion(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        int[] profit = convertProfit(prices);
        int max = findMaxSubArray(profit, 0, profit.length - 1);
        return max > 0 ? max : 0;
    }

    /**
     * 转换价格为每天对前一天对收益
     *
     * @param prices
     * @return
     */
    private static int[] convertProfit(int[] prices) {
        int[] profit = new int[prices.length - 1];
        for (int i = 1; i < prices.length; i++) {
            profit[i - 1] = prices[i] - prices[i - 1];
        }
        System.out.println(Arrays.toString(profit));
        return profit;
    }

    /**
     * 分治法：
     * 将数组从中间分为两个子数组，则最大子数组必然出现在以下三种情况之一：
     *     1、完全位于左边的数组中。
     *     2、完全位于右边的数组中。
     *     3、跨越中点，包含左右数组中靠近中点的部分。
     *     递归将左右子数组再分别分成两个数组，直到子数组中只含有一个元素，退出每层递归前，返回上面三种情况中的最大值。
     *
     * @param profit
     * @param low
     * @param hight
     * @return
     */
    private static int findMaxSubArray(int[] profit, int low, int hight) {
//        System.out.println("low==>" + low + ",hight===>" + hight);
        if (low == hight) {
            return profit[low];
        }
        int mid = (low + hight) / 2;
        int hight1 = mid == low ? mid : mid - 1;
        int leftMax = findMaxSubArray(profit, low, hight1);
        int rightMax = findMaxSubArray(profit, mid + 1, hight);
        int crossMidMax = findCrossMax(profit, mid, low, hight);
        int max = 0;
        max = leftMax > max ? leftMax : max;
        max = rightMax > max ? rightMax : max;
        max = crossMidMax > max ? crossMidMax : max;
        return max;
    }

    /**
     * 跨越中点的最大值
     *
     * @param profit
     * @param mid
     * @param low
     * @param hight
     * @return
     */
    private static int findCrossMax(int[] profit, int mid, int low, int hight) {
        int max = 0;
        int subSum = 0;
        //左边最大值，如果小于0，则最大值位0
        for (int i = mid; i >= low; i--) {
            subSum = subSum + profit[i];
            if (subSum > max) {
                max = subSum;
            }
        }
        //右边最大值
        subSum = max;
        for (int i = mid + 1; i <= hight; i++) {
            subSum = subSum + profit[i];
            if (subSum > max) {
                max = subSum;
            }
        }
        return max;
    }

    /**
     * 动态规划
     * 求和，然后判断和是否小于0，因为只要前面的和小于0，那么后面的数加上前面的和就一定比自身小，所以又重新求和，并和之前的最大子序和比较，取最大值。
     *
     * @param prices
     * @return
     */
    public static int maxProfitDynamicGreedy(int[] prices) {
        int[] profit = convertProfit(prices);
        int max = 0;
        int sum = 0;
        for (int i = 0; i < profit.length; i++) {
            sum = sum + profit[i];
            if (sum < 0) {
                sum = 0;
            }
            if (sum > max) {
                max = sum;
            }
        }

        return max;
    }
}