package learn.blind75;

//https://leetcode.com/problems/longest-consecutive-sequence/description/?envType=problem-list-v2&envId=oizxjoit
/*
You are given an unsorted array of integers.
You need to find the length of the longest sequence of consecutive numbers.

Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4], Therefore its length is 4.

*/

import java.util.HashSet;
import java.util.Set;

public class LCS {

    public static void main(String... args){

        int[] nums = {100,4,200,1,3,2};
        System.out.println("Longest Consecutive Sequence: " + longestConsecutive(nums));
    }

    public static int longestConsecutive(int[] nums) {
        Set<Integer> storage = new HashSet<>();
        for(int num : nums){
            storage.add(num);
        }
        int longest = 0;

         // IMPORTANT: iterate over set, not array
        for(int num : storage){

            // start only if no previous number exist
            if(!storage.contains(num-1)){
                int currentNum = num;
                int length = 1;

                // Step 4: Expand sequence
                while (storage.contains(currentNum + 1)) {
                    currentNum++;
                    length++;
                }
                // Debug visualization
               // System.out.println("Start: " + currentNum + " Length: " + length);
                longest = Math.max(longest, length);
            }
        }
        return longest;
    }
}



/*
CONSECUTIVE
    Consecutive numbers are numbers that come one after another with NO gap.
    Consecutive = numbers with no gaps, each number is +1 from the previous number.
    Think of stairs 🪜
    Step 1 → Step 2 → Step 3 → Step 4
    e.g., 1,2,3,4 or 10,11,12,13

* What is the longest group of numbers that can be arranged in consecutive order?

1 → 2 → 3 → 4   ✅ consecutive (length 4)
100             (length 1)
200             (length 1)

Pattern / Algorithm Used
    HashSet + Sequence Start Detection (Greedy + Hashing)
Why this pattern?
    We need fast lookup
    HashSet gives O(1) average search
    We trade space for time



Step-by-step strategy:
    Put all numbers into a HashSet (fast lookup)
For each number:
    If (num - 1) exists → ❌ skip (not a start)
    Else → ✅ start counting sequence
        Count how many numbers exist in the sequence (num, num+1, num+2, ...)
        Update max length if current sequence is longer

| Number | num-1 exists? | Start? |
| ------ | ------------- | ------ |
| 100    | 99 ❌          | ✅      |
| 4      | 3 ✅           | ❌      |
| 200    | 199 ❌         | ✅      |
| 1      | 0 ❌           | ✅      |
| 3      | 2 ✅           | ❌      |
| 2      | 1 ✅           | ❌      |

Complexity
    Time: O(n)
    Space: O(n)

*/