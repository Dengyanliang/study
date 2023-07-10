package com.deng.study.datastru_algorithm.sort;

import com.deng.study.domain.Student;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Desc: 成绩排序
 * @Auther: dengyanliang
 * @Date: 2023/7/6 13:05
 */
public class ScoreSort {
    public static void main(String[] args) {
        ScoreSort scoreSort = new ScoreSort();
        scoreSort.test2();
    }

    private void test2(){
        Scanner in = new Scanner(System.in);

        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNext()) {
            int n = Integer.parseInt(in.next());
            int flag = Integer.parseInt(in.next());

            Student[] students = new Student[n];
            for (int i = 0; i < n; i++) {
                Student student = new Student(in.next(),Integer.parseInt(in.next()));
                students[i] = student;
            }
            sortAndPrint(students,flag);

            break;
        }
    }

    private void test(){
        Scanner in = new Scanner(System.in);

        // 注意 hasNext 和 hasNextLine 的区别
        while (in.hasNextLine()) { // 注意 while 处理多个 case
            int n = Integer.parseInt(in.nextLine());
            int flag = Integer.parseInt(in.nextLine());

            Student[] students = new Student[n];

            for (int i = 0; i < n; i++) {
                // 按空格进行分割
                String[] s = in.nextLine().split("\\s+");
                String name = s[0];
                String score = s[1];

                Student student = new Student(name,Integer.parseInt(score));
                students[i] = student;
            }
            sortAndPrint(students,flag);
        }
    }

    private void sortAndPrint(Student[] students,int flag){
        Arrays.sort(students,((o1, o2) -> {
            if(flag == 1){ // 升序
                return o1.getScore() - o2.getScore();
            }else{ // 降序
                return o2.getScore() - o1.getScore();
            }
        }));

        for (Student student : students) {
            System.out.println(student);
        }
    }
}



