package learn.blind75;
// https://leetcode.com/problems/container-with-most-water/description/?envType=problem-list-v2&envId=oizxjoit

/*
You are given an array of numbers.
Each number represents the height of a vertical line.

You must choose two lines such that:
    They form a container
    The container holds maximum water

Input: height = [1,8,6,2,5,4,8,3,7]
Output: 49
Explanation: The above vertical lines are represented by array [1,8,6,2,5,4,8,3,7]. In this case, the max area of water (blue section) the container can contain is 49.
 */
public class ContainerWithMostWater {

    public static void main(String... args){

        int[] height = {1,8,6,2,5,4,8,3,7};
        System.out.println(height);
    }
    public static int maxArea(int[] height) {

        int left = 0;
        int right = height.length-1;
        int maxOutput = 0;

        while(left < right){
            int width = right - left;
            int minHeight = Math.min(height[left], height[right]);
            int area = width * minHeight;

            // Move the pointer with smaller height
            if(height[left] > height[right]){
                left++;
            }else{
                right++;
            }
            maxOutput = Math.max(maxOutput, area);
        }
        return maxOutput;
    }
}


/*
Algorithm
    Two Pointer + Greedy Algorithm

Two Pointer Technique
    Why?
        We place one pointer at the left end
        One pointer at the right end
        We move pointers towards each other
    This problem must compare elements from both ends, so two pointers is the natural fit.

🔹 Greedy Strategy
    At each step we make a greedy choice:
    Move the pointer with the smaller height

Why greedy?
    The current water height is limited by the shorter line
    Moving the taller line cannot increase the area
    Moving the shorter line might increase the area
    This is a locally optimal decision that leads to the global maximum.

 */