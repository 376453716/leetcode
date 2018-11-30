package com.haosnow.dynamicgreedy;

/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 * <p>
 * 输入: [-2,1,-3,4,-1,2,1,-5,4],
 * <p>
 * 输出: 6
 * <p>
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 * @description:
 * @author: xionghao_cd@keruyun.com
 * @create: 2018-11-29 17:29
 **/
public class MaxSubArraySum {
    public static void main(String[] args) {
        int[] nums = new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.println(maxSubArrayRecursion(nums));
        System.out.println(maxSubArrayDynamicGreedy(nums));
    }

    /**
     * 最大子数组和，分治法
     *
     * @param nums
     * @return
     */
    public static int maxSubArrayRecursion(int[] nums) {
        // return findMaxSubArray(nums,0,nums.length-1);
        int max = Integer.MIN_VALUE;
        int tempSum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (tempSum < 0) {
                tempSum = nums[i];
            } else {
                tempSum += nums[i];
            }
            max = Math.max(tempSum, max);
        }
        return max;

    }

    private int findMaxSubArray(int[] nums, int low, int high) {
        if (low == high) {
            return nums[low];
        } else {
            int mid = (low + high) / 2;
            int leftMax = findMaxSubArray(nums, low, mid);
            int rightMax = findMaxSubArray(nums, mid + 1, high);
            int crossMax = findMaxCrossSubArray(nums, low, mid, high);
            return leftMax > rightMax ?
                    leftMax > crossMax ? leftMax : crossMax :
                    rightMax > crossMax ? rightMax : crossMax;

        }
    }

    private int findMaxCrossSubArray(int[] nums, int low, int mid, int high) {
        int leftMax = Integer.MIN_VALUE;
        int leftSum = 0;
        if (low == mid) {
            leftMax = nums[low];
        }
        for (int i = mid; i >= 0; i--) {
            leftSum += nums[i];
            if (leftSum > leftMax) {
                leftMax = leftSum;
            }
        }
        int rightMax = Integer.MIN_VALUE;
        int rightSum = 0;
        if (mid + 1 == high) {
            rightMax = nums[high];
        }
        for (int i = mid + 1; i <= high; i++) {
            rightSum += nums[i];
            if (rightSum > rightMax) {
                rightMax = rightSum;
            }
        }
        return leftMax + rightMax;
    }

    /**
     * 从开始计算，只要sum为正值，则会一直递增，与max比较设置最大值
     * 当sum为负值时，后面的数加上前面的和就一定比自身小，所以sum置0，重新计算后面数的和，然后再与max比较
     *
     * @param nums
     * @return
     */
    public static int maxSubArrayDynamicGreedy(int[] nums) {
        int max = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
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