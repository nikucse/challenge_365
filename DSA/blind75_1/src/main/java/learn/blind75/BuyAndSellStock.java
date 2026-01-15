// https://leetcode.com/problems/longest-consecutive-sequence/description/?envType=problem-list-v2&envId=oizxjoit


/*
You are given daily stock prices.
You must buy once and sell once to maximize profit.

Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.

*/

public class BuyAndSellStock{
    public static void main(String... args){
        int[] prices = {7,1,5,3,6,4};
        maxProfit(prices);
    }

    public static void maxProfit(int[] prices) {
        int maxProfit = 0;
        int buyPrice = Integer.MAX_VALUE;

        for (int i = 0; i < prices.length; i++){

            // Buy at minimum Price
            buyPrice = Math.min(buyPrice,prices[i]);

            // Selling at maximum profit
            int sellPrice = prices[i] - buyPrice;

            maxProfit = Math.max(maxProfit, sellPrice);

        }
        System.out.println(maxProfit);
    }
}

/*

2️⃣ Pattern / Algorithm Used
    🔹 Greedy Algorithm
Why Greedy?
    We always want the lowest buy price And the highest profit afterward
🧠 Pattern Name:
    Min-so-far Greedy Pattern

🔹 What Is a Greedy Algorithm?
A greedy algorithm:
    Makes the best local decision at each step
    Never reconsiders past choices
    Still leads to a global optimal solution

🔹 Why This Problem Is Greedy
    Always remember the lowest price so far (best buy)
    At each day, try selling today and maximize profit

This is a greedy problem.
I traverse once, track the minimum price so far, and compute the profit if I sell on each day.
I keep updating the maximum profit.
This gives O(n) time and O(1) space.”

Dry Run

| Day | Price | Min Price So Far | Profit If Sell Today | Max Profit |
| --- | ----- | ---------------- | -------------------- | ---------- |
| 0   | 7     | 7                | 0                    | 0          |
| 1   | 1     | 1✅              | 0                    | 0          |
| 2   | 5     | 1                | 4                    | 4          |
| 3   | 3     | 1                | 2                    | 4          |
| 4   | 6     | 1                | 5                    | 5 ✅        |
| 5   | 4     | 1                | 3                    | 5          |

Buy at 1, Sell at 6

*/