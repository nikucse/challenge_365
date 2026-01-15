import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

// https://leetcode.com/problems/two-sum/description/?envType=problem-list-v2&envId=oizxjoit

/*
Input: nums = [2,7,11,15], target = 9
Output: [0,1]
Explanation: Because nums[0] + nums[1] == 9, we return [0, 1].
You are given an array of integers and a target number.
You must return indices of two numbers such that they add up to the target.
*/
public class TwoSum {

    public static void main(String[] args) {
        int[] nums = {2,7,11,15};
        int target = 9;

        int[] result = twoSum(nums, target);
        System.out.println(Arrays.toString(result));
    }

    public static int[] twoSum(int[] nums, int target) {

        // Map to store number -> index
        Map<Integer,Integer> result = new HashMap<>();

        for(int idx = 0; idx < nums.length; idx++){
            int diff = target - nums[idx];

            // Check if needed number exists
            if(result.containsKey(diff)){
                return new int[] {result.get(diff),idx};
            }
            // Store current number with index
            result.put(nums[idx], idx);
        }
        return null;
    }
}

/*
Pattern / Algorithm Used
    Array + Hashing (Lookup Pattern)
Why this pattern?
    We need fast lookup
    HashMap gives O(1) average search
    We trade space for time
    
Complexity
    Time: O(n)
    Space: O(n)
*/
