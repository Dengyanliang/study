package com.deng.study.datastru_algorithm.algorithm.dynamicprogramming;


/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/5/3 21:21
 */
public class LCS {

    public static void main(String[] args) {
        System.out.println(longestCommonSubsequence("abcde","ace"));
        System.out.println(longestCommonSubsequence("abc","abc"));
        System.out.println(longestCommonSubsequence("abc","def"));
    }

    /**
     * 时间复杂度：O(N1*N2)
     * 空间复杂度：O(N1*N2)
     * @param s1
     * @param s2
     * @return
     */
    private static int longestCommonSubsequence(String s1,String s2){
        int n1 = s1.length();
        int n2 = s2.length();

        // 定义一个状态矩阵
        int[][] dp = new int[n1+1][n2+1];

        for (int i = 1; i <= n1 ; i++) {
            for (int j = 1; j <= n2; j++) {
                if(s1.charAt(i-1) == s2.charAt(j-1)){
                    dp[i][j] = dp[i-1][j-1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i-1][j],dp[i][j-1]);
                }
            }
        }

//        ArrayUtil.printArray(dp);

        return dp[n1][n2];
    }
}
