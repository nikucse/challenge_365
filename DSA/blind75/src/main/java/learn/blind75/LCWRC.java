package learn.blind75;

//https://leetcode.com/problems/longest-substring-without-repeating-characters/description/?envType=problem-list-v2&envId=oizxjoit

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
Given a string s, find the length of the longest substring without duplicate characters.

Input: s = "abcabcbb"
Output: 3
Explanation: The answer is "abc", with the length of 3.
Note that "bca" and "cab" are also correct answers.
 */
public class LCWRC {

    public static void main() {
        String str = "pwwkew";
        System.out.println(lengthOfLongestSubstring3(str));
    }

    public static int lengthOfLongestSubstring(String str) {

        int longest = 0;
        String newString = "";
        for (int i = 0; i < str.length(); i++) {
            // Start a new substring with the current character
            newString = String.valueOf(str.charAt(i));
            for (int j = i + 1; j < str.length(); j++) {

                // Check if current character already exists in newString
                if (newString.contains(String.valueOf(str.charAt(j)))) {
                    // If duplicate found: update length
                    longest = Math.max(longest, newString.length());

                    // Stop extending this substring
                    break;
                }

                // If no duplicate, add character to substring
                newString += str.charAt(j);
            }
        }
        // Return the maximum length found
        return longest;
    }

    public static int lengthOfLongestSubstring2(String str){

        int longest = 0;
        int start = 0;
        int end = 0;
        String newString = "";
        while(end < str.length()){

            // If character NOT in window → expand
            if(!newString.contains(String.valueOf(str.charAt(end)))){
                newString += str.charAt(end);
                end++;
                longest = Math.max(longest, newString.length());
            }else {
                // If duplicate → shrink from left
                newString = newString.substring(1);
                start++;
            }
        }
        return longest;
    }

    public static int lengthOfLongestSubstring3(String str){
        Set<Character> set = new HashSet<>();
        int longest = 0;
        int start = 0;

        for(int end = 0;  end < str.length(); end++) {
            Character tempChar = str.charAt(end);

            // keep shrinking until duplicate is gone
             while(set.contains(tempChar)) {
                set.remove(str.charAt(start));
                start++;
            }
             set.add(tempChar);
             longest = Math.max(longest, (end-start) +1);

        }
        return longest;
    }

    public static int lengthOfLongestSubstring4(String str){
        Map<Character, Integer> map= new HashMap<>();
        int longest = 0;
        int start = 0;
        for(int end = 0;  end < str.length(); end++) {
            Character tempChar = str.charAt(end);
            // if duplicate found inside current window  update start
            if(map.containsKey(tempChar) && map.get(tempChar) >= start ) {
                start = map.get(tempChar) + 1;
            }
            // Update last seen index
            map.put(tempChar,end);
            longest = Math.max(longest, (end-start) +1);
        }
        return longest;
    }
}
/*
Important questions: on solution lengthOfLongestSubstring
    Do we really need to restart checking from scratch every time?
    Can we reuse work from previous substring?

Pattern : Sliding Window
What is SLIDING WINDOW?
Imagine a glass window on a wall.
    You can:
        Move it right
        Move it left
        See only what is inside the window
👉 You cannot see everything, only the part inside the window.

s = "a b c d e f"
We place a window over the string:
[a b] c d e f  : The window is showing ab
Move window one step right:
a [b c] d e f : Now window shows: bc

Why Sliding Window?
    We want to look at continuous characters
    We don’t want to start from scratch again and again
    We want to save time

Sliding window works ONLY for:
    Substrings
    Subarrays

Use sliding window when you see:
    Substring
    Subarray
    Continuous
    Longest / shortest
    At most / at least

Keep a moving window, grow it when valid, shrink it when invalid, and never go back.
 */

// Note : Sliding Window is a SPECIAL TYPE of Two Pointer technique

/*
Substring = continuous characters
s = "abcdef" -> [a, ab,abc, abcd, abcde]
"ace"   (characters skipped ❌)

 */


/*
Similar Questions
Medium
    Longest Substring with At Most Two Distinct Characters
    Longest Substring with At Most K Distinct Characters
    Maximum Erasure Value
    Number of Equal Count Substrings
    Minimum Consecutive Cards to Pick Up
    Longest Nice Subarray
    Optimal Partition of String
    Count Complete Subarrays in an Array
    Find Longest Special Substring That Occurs Thrice II
    Find Longest Special Substring That Occurs Thrice I


Hard
    Subarrays with K Different Integers
*/