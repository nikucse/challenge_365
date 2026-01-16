package learn.blind75;
//https://leetcode.com/problems/rotate-image/description/?envType=problem-list-v2&envId=oizxjoit
/*
You are given an n x n 2D matrix representing an image, rotate the image by 90 degrees (clockwise).
You have to rotate the image in-place, which means you have to modify the input 2D matrix directly.
DO NOT allocate another 2D matrix and do the rotation.

Input: matrix = [[1,2,3],
                 [4,5,6],
                 [7,8,9]]
Output: [[7,4,1],
         [8,5,2],
         [9,6,3]]

 */

import java.util.Arrays;

public class RotateImage {

    static void main() {
        int matrix[][] = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}};

        rotate(matrix);

        for (int i = 0; i< matrix.length; i++){
            for (int j =0 ; j< matrix[i].length; j++){
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

    }
    public static void rotate(int[][] matrix) {
        for (int i = 0 ; i < matrix.length; i++){
            for (int j = i+1; j < matrix.length; j++){
                // Transpose
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        reverse(matrix);
    }

    public static void reverse (int[][] matrix){

        for(int i = 0; i < matrix.length; i++){
            int left = 0;
            int right = matrix[i].length-1;
            while(left < right){
                int temp = matrix[i][left];
                matrix[i][left] = matrix[i][right];
                matrix[i][right] = temp;
                left++; right--;

            }
        }
    }
}

/*
steps:
    Transpose matrix
    Reverse each row
 */
