package com.owl.studypack.leetcode;


import java.util.HashSet;
import java.util.Set;

/**
 * Description: LeetCode算法题1-10题<br/>
 * date: 2020/7/5 19:46<br/>
 *
 * @author MuyerJ<br
    */
public class Q1toQ10 {
    /** Q3:给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     示例 1:
     输入: "abcabcbb"
     输出: 3
     解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     示例 2:
     输入: "bbbbb"
     输出: 1
     解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     示例 3:
     输入: "pwwkew"
     输出: 3
     解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3，请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     **/

    /**
     * ====解法======
     * 暴力求解：
     * 逐个生成子字符串；看它是否不包含重复字符串
     * ====收获======
     * 1.求子串
     * 2.利用Set/HashMap去重
     *
     * @param s
     * @return
     */
    public static int Q3_method1(String s) {
        //判断是否是空字符串
        if (s == null || "".equals(s)) {
            return 0;
        }
        //最大长度
        int maxLength = 1;
        //生成各个子串
        for (int i = 0; i < s.length(); i++) {
            //子串容器
            Set<Character> set = new HashSet<>();
            set.add(s.charAt(i));
            for (int j = i + 1; j < s.length(); j++) {
                set.add(s.charAt(j));
                if (j - i + 1 > set.size()) {
                    break;
                }
                maxLength = maxLength > set.size() ? maxLength : set.size();
            }
        }
        return maxLength;
    }


    public static void main(String[] args) {
        System.out.println(Q3_method1(""));
    }
}
