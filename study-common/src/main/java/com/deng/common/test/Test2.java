package com.deng.common.test;

/**
 * @Desc:
 * @Date: 2024/1/11 14:58
 * @Auther: dengyanliang
 */
public class Test2 {

    public static void main(String[] args) {
        int[] numbers = {1,8,6,2,5,4,8,3,7};
        int result = maxArea(numbers);
        System.out.println(result);
    }

    private static int maxArea(int[] numbers){
        int left = 0;
        int right = numbers.length - 1;
        int result = 0;
        while(left < right){
            int area = Math.min(numbers[left],numbers[right]) * (right-left);
            if(numbers[left] < numbers[right]){
                left++;
            }else{
                right--;
            }
            result = Math.max(area,result);
        }
        return result;
    }

}
