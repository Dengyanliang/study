package com.deng.study.datastru_algorithm.leetcode;

import com.deng.common.util.MyArrayUtil;
import org.junit.Test;

import java.util.*;

/**
 * @Desc:
 * @Auther: dengyanliang
 * @Date: 2023/7/29 19:41
 */
public class Hot100Test {

    @Test
    public void 盛最多水的容器(){

        int[] height = {1,8,6,2,5,4,8,3,7};
        int i = 0;
        int j = height.length-1;
        int area = 0;
        while(i < j){
            if(height[i] < height[j]){
                area = Math.max(area,  (j-i)*height[i]);
                i++;
            }else{
                area = Math.max(area, (j-i)*height[j]);
                j--;
            }
        }
        System.out.println(area);
    }

    @Test // TODO
    public void 三数之和(){
        int[] nums = {-1,0,1,2,-1,-4};

        // 第一种办法：暴力法
        int n = nums.length;
        ArrayList<List<Integer>> resultList = new ArrayList<>();
        for (int i = 0; i < n-2; i++) {
            for (int j = i+1; j < n - 1; j++) {
                for (int k = j+1; k < n; k++) {
                    if(nums[i]+nums[j]+nums[k] == 0)
                        resultList.add(Arrays.asList(nums[i],nums[j],nums[k]));
                }
            }
        }
        System.out.println(resultList);

    }

    @Test
    public void 找到字符串中所有字母异位词(){
        String s = "cbaebabacd", p = "abc";

        System.out.println(get字符串中所有字母异位词(s, p));
    }

    private List<Integer> get字符串中所有字母异位词(String s,String p){
        int sLen = s.length(),pLen = p.length();
        if(sLen < pLen){
            return new ArrayList<>();
        }

        List<Integer> resultList = new ArrayList<>();
        int[] sCount = new int[26];
        int[] pCount = new int[26];
        // 第一步，计算sCount，pCount的值
        for (int i = 0; i < pLen; i++) {
            sCount[s.charAt(i)-'a']++;
            pCount[p.charAt(i)-'a']++;
        }
        if(Arrays.equals(sCount,pCount)){
            resultList.add(0);
        }
        for (int i = 0; i < sLen-pLen; i++) {
            // 先对计算好的sCount数组的索引下标减1，再对sCount数组的索引下标加1，目的是消除第一步中已经判断好的值
            sCount[s.charAt(i)-'a']--;      //
            sCount[s.charAt(i+pLen)-'a']++; //

            if(Arrays.equals(sCount,pCount)){
                resultList.add(i+1);
            }
        }
        return resultList;
    }

    @Test
    public void 无重复字符的最长子串(){
        String s = "abcabcbb";

        int left = 0,right = 0,max= 0;
        Set<Character> set = new HashSet<>();
        while (right < s.length()) {
            if(!set.contains(s.charAt(right))){
                set.add(s.charAt(right));
                right++;
            }else{
                set.remove(s.charAt(left));
                left++;
            }
            max = Math.max(max,set.size());
        }
        System.out.println(max + "," + set);
    }


    @Test
    public void 最大子数组和(){
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        // 定义动态规划数组
        int[] dp = new int[nums.length];
        dp[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            if(dp[i-1] > 0){ // 如果dp[i-1]>0，那么可以把nums[i]直接接在dp[i-1]表示的那个数组的后面，得到和更大的连续子数组
                dp[i] = dp[i-1] + nums[i];
            }else{  // 如果dp[i-1]<=0，那么nums[i]加上前面的数dp[i-1]以后，值一定不会变大。于是dp[i]另起炉灶，此时单独的一个nums[i]的值，就是dp[i]
                dp[i] = nums[i];
            }
        }

        int result = dp[0];
        for (int i = 1; i < nums.length; i++) {
            result = Math.max(result,dp[i]);
        }
        System.out.println(result);
    }

    @Test
    public void  合并区间(){
        int[][] intervals = {{1,3},{2,6},{8,10},{15,18}};
        int[][] result = null;
        if(intervals == null){
            result =  new int[0][2];
        }
        // 对所有的左区间进行升序排序
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0]-o2[0];
            }
        });

        // 放置一维数组，每个数组中有两个值
        List<int[]> merged = new ArrayList<>();

        for (int i = 0; i < intervals.length; i++) {
            // 取出每一对的左右区间
            int left = intervals[i][0],right = intervals[i][1];
            // result.size() == 0 表示还没有放入值，此时把第一个放入进去
            // result.get(result.size()-1)[1] < left 集合中最后一个值的右区间小于当前的左区间，说明是按照顺序的，直接放入
            if(merged.size() == 0 || merged.get(merged.size()-1)[1] < left){
                merged.add(new int[]{left,right});
            }else{ // 否则，比较集合中最后一个数组的右区间和当前的右区间，取出最大值，作为集合中最后一个区间的右区间
                merged.get(merged.size()-1)[1] = Math.max(merged.get(merged.size()-1)[1],right);
            }
        }

        result = merged.toArray(new int[merged.size()][]);
        MyArrayUtil.printArray(result);
    }

    @Test
    public void 旋转数组(){
        int[] nums = {1,2,3,4,5,6,7};
        int k = 3;
        int n = nums.length;
        int[] newArr = new int[n];

        for (int i = 0; i < n; i++) {
            int index = (i + k) % n ;
            System.out.print(index + "\t");
            newArr[index] = nums[i];
        }
        System.out.println();
        System.out.println(Arrays.toString(newArr));
        System.arraycopy(newArr, 0, nums, 0, n);
    }

    @Test
    public void 除自身以外数组的乘积(){
        int[] nums = {1,2,3,4};
        int n = nums.length;
        // 定义左右数组
        int[] L = new int[n];
        int[] R = new int[n];


        int[] answer = new int[n];

        // L[i]为索引i左侧所有元素的乘积，对于索引为0的元素，因为左侧没有元素，所以L[0]=1
        L[0] = 1;
        for (int i = 1; i < n; i++) {
            L[i] = L[i-1] * nums[i-1];
        }

        // R[i]为索引i右侧所有元素的成绩，对于索引为[n-1]的元素，因为右侧没有元素，所以R[n-1]=1
        R[n-1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            R[i] = R[i+1] * nums[i+1];
        }

        for (int i = 0; i < n; i++) {
            answer[i] = L[i] * R[i];
        }
        System.out.println(Arrays.toString(answer));
    }

    @Test
    public void 和为K的子数组(){
        int[] nums = {1,2,3};
        int k = 3;

        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            int sum = 0;
            for (int j = i; j >= 0 ; j--) {
                sum += nums[j];
                if(sum == k)
                    count++;
            }
        }
        System.out.println(count);
    }


    @Test
    public void 矩阵置零(){
        int[][] matrix = {{1,1,1},{1,0,1},{1,1,1}};
        int n = matrix.length;
        int m = matrix[0].length;
        boolean[] row = new boolean[n];
        boolean[] col = new boolean[m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(matrix[i][j] == 0){
                    row[i] = col[j] = true;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(row[i] || col[j]){
                    matrix[i][j] = 0;
                }
            }
        }

        MyArrayUtil.printArray(matrix);
    }

    @Test
    public void 旋转图像(){
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int n = matrix.length;

        // 先水平翻转
        for (int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[n-i-1][j];
                matrix[n-i-1][j] = temp;
            }
        }

        MyArrayUtil.printArray(matrix);

        System.out.println("---------------");

        // 按照主对角线对折
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int temp = matrix[i][j];
                matrix[i][j]  = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        MyArrayUtil.printArray(matrix);
    }

    @Test
    public void 搜索二维矩阵II(){
        int[][] maxtrix = {{1,4,7,11,15},{2,5,8,12,19},{3,6,9,16,22},{10,13,14,17,24},{18,21,23,26,30}};
        int target = 20;

        boolean flag = false;
        int n = maxtrix.length, m = maxtrix[0].length;
        int x = 0, y = m - 1;

        while (x < n && y >= 0){
            if(target == maxtrix[x][y]){
                flag = true;
            }
            if(target > maxtrix[x][y]){
                x++;
            }else{
                y--;
            }
        }
        System.out.println(flag);
    }





}
