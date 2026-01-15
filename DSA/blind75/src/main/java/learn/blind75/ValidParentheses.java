package learn.blind75;

// https://leetcode.com/problems/valid-parentheses/description/?envType=problem-list-v2&envId=oizxjoit
/*
Topic:
    String, Stack

Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.

An input string is valid if:
    Open brackets must be closed by the same type of brackets.
    Open brackets must be closed in the correct order.
    Every close bracket has a corresponding open bracket of the same type.
 */
import java.util.Stack;

public class ValidParentheses {

    static void main(String[] args) {
        String str = "()[]{}";
        String str2 = "([)]";
        System.out.println(isValid2(str2));
    }

    public static boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        for(int i = 0 ; i < str.length(); i++){
            char bracket = str.charAt(i);
            if(bracket == '(' || bracket == '{' || bracket == '['){
                stack.push(bracket);
            }else{
                if(stack.isEmpty()){
                    return false;
                }
                char temp = stack.pop();
                if(!((bracket == ')' && temp == '(') ||(bracket == ']' && temp == '[') ||(bracket == '}' && temp == '{'))){
                    return false;
                }
            }
        }
        return stack.isEmpty() ? true : false;
    }

    public static boolean isValid2(String s) {
        Stack<Character> stack = new Stack<Character>();
        for (char c : s.toCharArray()) {
            if (c == '(')
                stack.push(')');
            else if (c == '{')
                stack.push('}');
            else if (c == '[')
                stack.push(']');
            else if (stack.isEmpty() || stack.pop() != c)
                return false;
        }
        return stack.isEmpty();
    }
}


/*

Intuition
    We need to check if every opening bracket has a corresponding closing bracket in the correct order.
    A stack is a natural choice because it follows Last-In-First-Out (LIFO), which matches the bracket pairing rule.

Approach
    Traverse the string character by character.
    If it is an opening bracket ((, [, {), push it onto the stack.
    If it is a closing bracket (), ], }):
        Check if the stack is empty → if yes, return False.
        Otherwise, pop from the stack and ensure the popped element matches the type of closing bracket.
            If not matching, return False.
    At the end, if the stack is empty, return True; otherwise, False.

Complexity
    Time complexity:
        O(n) — we traverse the string once.
    Space complexity:
        O(n) — in the worst case, all characters are opening brackets stored in the stack.

 */
