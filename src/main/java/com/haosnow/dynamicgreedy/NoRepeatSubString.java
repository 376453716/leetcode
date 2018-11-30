package com.haosnow.dynamicgreedy;

import java.util.*;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 * <p>
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 * 请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 * <p>
 * 1 2 3 4 5 6 7 8
 *
 * @description: 无重复字符的最长子串
 * @author: xionghao_cd@keruyun.com
 * @create: 2018-11-29 17:45
 **/
public class NoRepeatSubString {

    public static void main(String[] args) {
        System.out.println(lengthOfLongestSubstring("abcda"));
        System.out.println(lengthOfLongestSubstring2("abcd"));
    }

    /**
     * 从第二个字符开始判断，如果在前面的字符中已经出现，则记录前面的字符长度，清除字符串数组，从重复字符之后再重新判断，直到最后一个字符
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
        List<Character> subCharacter = new ArrayList<Character>();
        char[] array = s.toCharArray();
        int length = 0;
        int maxLength = 0;
        int startIndex = 0;
        for (int i = startIndex; i < array.length; i++) {
            if (!subCharacter.contains(array[i])) {
                length++;
                if (length > maxLength) {
                    maxLength = length;
                }
                subCharacter.add(array[i]);
            } else {
                startIndex++;
                i = startIndex - 1;
                length = 0;
                subCharacter.clear();
            }
        }
        return maxLength;
    }

    /**
     * a 1
     * a 2
     * b 3
     * 计算从开始字符当前字符的最长无重复字符的长度，比较所有字符的最长无重复字符长度，得到结果
     * <p>
     * 用map记录遍历过程中字符出现的最新位置，用pre记录最近重复字符的位置
     * 当前字符的最大长度为
     * 从最近重复的index到当前位置到长度
     * <p>
     * pwwkew
     * 012345
     *
     * @param s
     * @return
     */
    public static int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0) {
            return 0;
        }
        int max = 1;
        //记录遍历的节点的最新位置
        Map<Character, Integer> map = new HashMap<Character, Integer>();
        char[] array = s.toCharArray();
        map.put(array[0], 0);
        int preIndex = -1;
        for (int i = 1; i < array.length; i++) {
            //从遍历节点中检查是否是重复元素
            Integer index = map.get(array[i]);
            //发现重复元素，记录前一个重复元素的位置，从重复元素开始重新计算字符长度
            if (index != null) {
                //有重复值时，需从最新的重复元素开始计算，避免充map中取出的旧的重复index覆盖最新的index
                preIndex = Math.max(index, preIndex);
            }
            max = Math.max(i - preIndex, max);
            map.put(array[i], i);
        }
        return max;
    }

}