# 📚 STACK — Complete Deep-Dive (From Zero → Expert)

---

## 1️⃣ High-Level Overview

### 🔹 Simple Explanation (Plain English)

A **Stack** is a data structure where the **last element added is the first one removed**.

**Real-life analogy:**  
A stack of plates — you add plates on top and remove plates from the top.

> **LIFO → Last In, First Out**

---

### 🔹 Formal Definition

A **Stack** is a **linear data structure** that follows the **LIFO (Last In First Out)** principle, where **insertion and deletion are allowed only at one end**, called the **top**.

---

### 🔹 Why Stack Exists

**Real-Life Analogy:** Think of a **stack of plates in a kitchen**

```
Top of Stack
    ↓
[ Plate 4 ]  ← You pick this one first (Last one added)
[ Plate 3 ]
[ Plate 2 ]
[ Plate 1 ]  ← This one was added first
Bottom of Stack
```

**You add plates on top and remove from top = LIFO (Last In, First Out)**

Stacks were invented because many real problems work this way:

1. **Reverse Order** - When you need things in opposite order
2. **Nested Structures** - When things go "inside" then "outside"
3. **Function Execution** - When functions call other functions
4. **Temporary Storage** - When you need to store but in reverse
5. **Undo/Redo** - When you need to go back to previous states

---

### 🔹 Problems It SOLVES (With Examples)

#### ✅ **1. Function Calls (Call Stack)**

```
Imagine your program runs:
  main() calls function A
    A calls function B
      B calls function C
        C finishes → Go back to B
      B finishes → Go back to A
    A finishes → Go back to main

Stack looks like:
main
  ↓ A calls
main → A
  ↓ B calls
main → A → B
  ↓ C calls
main → A → B → C
  ↓ C returns
main → A → B
  ↓ B returns
main → A
  ↓ A returns
main
```

**Your computer uses a STACK to track this!**

#### ✅ **2. Expression Evaluation (Math)**

```
Problem: Evaluate "3 + 4 * 2"
Need to respect: Multiplication before Addition

Stack helps track:
- Numbers to add
- Operations to apply
- Order of operations

Stack: [3, 4, 2] with + and *
Result: 3 + (4*2) = 11
```

#### ✅ **3. Undo / Redo Operations**

```
Real example: Google Docs or Word

User types: "Hello"
Stack: [H] → [He] → [Hel] → [Hell] → [Hello]

User presses Ctrl+Z (Undo):
Pop from stack → Remove "o" → "Hell"
Pop again → Remove "l" → "Hel"

User presses Ctrl+Y (Redo):
Push back → "Hell"
Push again → "Hello"
```

**Each keystroke is pushed on the stack. Undo pops it.**

#### ✅ **4. Valid Parenthesis Checking**

```
Problem: Is this correct? "({[]})"

Idea: Stack it!
- See opening ( → PUSH it
- See closing ) → POP and check if they match

Stack journey:
(    → Push (        [stack: (]
{    → Push {        [stack: (, {]
[    → Push [        [stack: (, {, []
]    → Pop [ ✓match  [stack: (, {]
}    → Pop } ✓match  [stack: (]
)    → Pop ) ✓match  [stack: empty]

Result: YES, valid! Stack is empty at end
```

#### ✅ **5. Backtracking (Finding Way Out of Maze)**

```
You're in a maze. You try a path:
  - Step forward (PUSH each step)
  - Hit dead end
  - Go back (POP each step) until you find another way
  - Try new path

Stack remembers: Where did I come from? Go back there!
```

#### ✅ **6. Browser Back Button**

```
You browse:
1. Google.com (PUSH to stack)
2. Click link → Facebook.com (PUSH to stack)
3. Click link → YouTube.com (PUSH to stack)

Stack: [Google] → [Facebook] → [YouTube]

You click BACK:
POP YouTube → Show Facebook
POP Facebook → Show Google
```

---

### 🔹 Problems It DOES NOT SOLVE (With Examples)

#### ❌ **1. Random Access**

```
Problem: "Give me the 3rd element quickly"

Stack can only access TOP!

Stack: [1, 2, 3, 4, 5]  ← Top
To get 3rd element, you must:
POP → 5
POP → 4
POP → 3 ← Finally!

But 1, 2 are buried! You'd need to pop through them.

Solution: Use ARRAY - Access any element instantly!
```

#### ❌ **2. Fast Searching**

```
Problem: "Does 42 exist in this data?"

Stack: [1, 2, 3, 42, 5, 6]
You must check one by one from TOP:
Check 6? No
Check 5? No
Check 42? Yes! ← Found after 3 checks

Array/HashMap: Can find it in O(1) or O(log n)
Stack: Takes O(n) time

Solution: Use HashMap or HashSet for fast search
```

#### ❌ **3. FIFO Workflows (First In, First Out)**

```
Problem: "Process jobs in ORDER they arrived"

Jobs arrive:
1. Job A arrives first
2. Job B arrives second
3. Job C arrives third

You want to process: A → B → C (In order received)

But STACK is LIFO!
Stack gives you: C → B → A (Opposite order!)

Solution: Use QUEUE instead!
```

#### ❌ **4. Priority-Based Retrieval**

```
Problem: "Handle IMPORTANT tasks first, even if they arrived last"

Tasks:
1. Urgent email (arrived last)
2. Regular task (arrived first)
3. Low priority task

You want: Urgent email FIRST (regardless of order)

Stack can't do this - It's LIFO, doesn't understand "importance"

Solution: Use PRIORITY QUEUE (special data structure)
```

---

### 📊 Quick Comparison Table

| Need               | Use                | Why                       |
| ------------------ | ------------------ | ------------------------- |
| Reverse order      | **Stack**          | LIFO matches reversal     |
| Process in order   | **Queue**          | FIFO maintains order      |
| Quick lookup       | **HashMap**        | O(1) search               |
| Any element access | **Array**          | Direct index access       |
| Important first    | **Priority Queue** | Custom priority           |
| Nested tracking    | **Stack**          | Handles nesting naturally |

---

## 2️⃣ Core Principles & Theory

### 🔹 Fundamental Rules

1. **Push** → insert element only at top
2. **Pop** → remove element only from top
3. Only the **top element is accessible**

---

### 🔹 Underlying Logic

Stack enforces **strict order control**.  
This perfectly matches:

- Nested operations
- Reversal logic
- Recursive execution

---

### 🔹 Invariants (Always True)

- Stack grows/shrinks from the top
- Order is always LIFO
- No element below top is directly accessible

---

### 🔹 Why These Principles Work

Most computational problems **unwind in reverse order**:

- Recursive returns
- Expression evaluation
- DFS traversal

---

## 3️⃣ Terminology & Concepts

| Term       | Meaning              |
| ---------- | -------------------- |
| Push       | Insert element       |
| Pop        | Remove top element   |
| Peek / Top | View top element     |
| Overflow   | Push on full stack   |
| Underflow  | Pop from empty stack |
| LIFO       | Last In First Out    |

---

### 🔹 Common Confusions

- Stack ≠ Array
- Stack ≠ Heap (memory concept)
- `java.util.Stack` ≠ recommended

---

## 4️⃣ Operations / Components (WITH PSEUDOCODE)

---

### 🔹 PUSH Operation

**Purpose:** Add element to the stack

**Algorithm (Pseudocode):**

PUSH(stack, value):
if top == capacity - 1:
print "Stack Overflow"
return

**Time Complexity:** O(1)  
**Space Complexity:** O(1)

**Edge Cases:**

- Stack full
- Invalid capacity

---

### 🔹 POP Operation

**Purpose:** Remove top element

**Algorithm (Pseudocode):**

```
POP(stack):
  if top == -1:
    print "Stack Underflow"
    return NULL

  element = stack[top]
  top = top - 1
  return element
```

**Time Complexity:** O(1)  
**Space Complexity:** O(1)

**Edge Cases:**

- Stack empty
- Invalid state

---

### 🔹 PEEK Operation

**Purpose:** View top element without removing it

**Algorithm (Pseudocode):**

```
PEEK(stack):
  if top == -1:
    print "Stack Empty"
    return NULL

  return stack[top]
```

**Time Complexity:** O(1)  
**Space Complexity:** O(1)

**Edge Cases:**

- Stack empty

---

### 🔹 ISFULL Operation

**Purpose:** Check if stack capacity reached

**Algorithm (Pseudocode):**

```
ISFULL(stack):
  return top == capacity - 1
```

**Time Complexity:** O(1)

---

### 🔹 ISEMPTY Operation

**Purpose:** Check if stack has no elements

**Algorithm (Pseudocode):**

```
ISEMPTY(stack):
  return top == -1
```

**Time Complexity:** O(1)

---

## 5️⃣ Implementation

### 🔹 Array-Based Stack (Fixed Size)

```java
public class StackArray {
    private int[] elements;
    private int top;
    private int capacity;

    public StackArray(int size) {
        this.capacity = size;
        this.elements = new int[size];
        this.top = -1;
    }

    public void push(int value) {
        if (top == capacity - 1) {
            System.out.println("Stack Overflow");
            return;
        }
        elements[++top] = value;
    }

    public int pop() {
        if (top == -1) {
            System.out.println("Stack Underflow");
            return -1;
        }
        return elements[top--];
    }

    public int peek() {
        if (top == -1) {
            System.out.println("Stack Empty");
            return -1;
        }
        return elements[top];
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public boolean isFull() {
        return top == capacity - 1;
    }

    public int size() {
        return top + 1;
    }
}
```

---

### 🔹 LinkedList-Based Stack (Dynamic Size)

```java
public class StackLinkedList<T> {
    private Node<T> top;
    private int size;

    private class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    public StackLinkedList() {
        this.top = null;
        this.size = 0;
    }

    public void push(T value) {
        Node<T> newNode = new Node<>(value);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (top == null) {
            System.out.println("Stack Underflow");
            return null;
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (top == null) {
            System.out.println("Stack Empty");
            return null;
        }
        return top.data;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public int size() {
        return size;
    }
}
```

---

## 6️⃣ Time & Space Complexity

| Operation | Time | Space |
| --------- | ---- | ----- |
| Push      | O(1) | O(1)  |
| Pop       | O(1) | O(1)  |
| Peek      | O(1) | O(1)  |
| Search    | O(n) | O(1)  |
| Traverse  | O(n) | O(1)  |

**Overall Space Complexity:**

- Array-based: O(n) - fixed at creation
- LinkedList-based: O(n) - grows dynamically

---

## 7️⃣ Classic Problems

### 🔹 Valid Parentheses

**Problem:** Check if brackets are balanced

**Approach:** Push opening brackets, pop on closing brackets

```java
public boolean isValidParentheses(String s) {
    Stack<Character> stack = new Stack<>();

    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (!isMatching(top, c)) return false;
        }
    }

    return stack.isEmpty();
}

private boolean isMatching(char open, char close) {
    return (open == '(' && close == ')') ||
           (open == '{' && close == '}') ||
           (open == '[' && close == ']');
}
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n)

---

### 🔹 Next Greater Element

**Problem:** For each element, find next greater element to the right

**Approach:** Use stack to maintain decreasing order

```java
public int[] nextGreaterElement(int[] nums) {
    int[] result = new int[nums.length];
    Stack<Integer> stack = new Stack<>();

    for (int i = nums.length - 1; i >= 0; i--) {
        while (!stack.isEmpty() && stack.peek() <= nums[i]) {
            stack.pop();
        }
        result[i] = stack.isEmpty() ? -1 : stack.peek();
        stack.push(nums[i]);
    }

    return result;
}
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n)

---

### 🔹 Duplicate Removal

**Problem:** Remove consecutive duplicates from string

**Approach:** Stack to maintain characters

```java
public String removeDuplicates(String s) {
    Stack<Character> stack = new Stack<>();

    for (char c : s.toCharArray()) {
        if (!stack.isEmpty() && stack.peek() == c) {
            stack.pop();
        } else {
            stack.push(c);
        }
    }

    StringBuilder result = new StringBuilder();
    for (char c : stack) {
        result.append(c);
    }
    return result.toString();
}
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n)

---

### 🔹 Evaluate Reverse Polish Notation

**Problem:** Evaluate expression in postfix notation

**Approach:** Process operators when encountered

```java
public int evalRPN(String[] tokens) {
    Stack<Integer> stack = new Stack<>();

    for (String token : tokens) {
        if (isOperator(token)) {
            int b = stack.pop();
            int a = stack.pop();
            int result = applyOperator(a, b, token);
            stack.push(result);
        } else {
            stack.push(Integer.parseInt(token));
        }
    }

    return stack.pop();
}

private boolean isOperator(String s) {
    return s.equals("+") || s.equals("-") ||
           s.equals("*") || s.equals("/");
}

private int applyOperator(int a, int b, String op) {
    return switch(op) {
        case "+" -> a + b;
        case "-" -> a - b;
        case "*" -> a * b;
        case "/" -> a / b;
        default -> 0;
    };
}
```

**Time Complexity:** O(n)  
**Space Complexity:** O(n)

---

## 8️⃣ Advantages & Disadvantages

### ✅ Advantages

- **Simple implementation** - Easy to understand & code
- **Fast operations** - O(1) for push/pop
- **Memory efficient** - No extra overhead
- **Natural fit** - Perfect for nested problems
- **Undo/Redo** - Easily track history

### ❌ Disadvantages

- **Limited access** - Only top element visible
- **Fixed capacity** (array-based) - Need to resize
- **No search** - Must pop to find element
- **Memory wastage** (array-based) - Pre-allocated size
- **Not suitable for FIFO** - Use Queue instead

---

## 9️⃣ When to Use Stack

✅ **Use Stack When:**

- Need LIFO behavior
- Parenthesis matching
- Expression evaluation
- DFS traversal
- Undo/Redo functionality
- Function call management
- Backtracking

❌ **Don't Use Stack When:**

- Need random access
- Need FIFO behavior (use Queue)
- Need fast search (use HashSet/HashMap)
- No specific ordering required (use Array/List)

---

## 🔟 Comparison with Other Structures

| Feature | Stack | Queue | Array  | LinkedList |
| ------- | ----- | ----- | ------ | ---------- |
| Access  | LIFO  | FIFO  | Random | Sequential |
| Push    | O(1)  | O(1)  | O(1)   | O(1)       |
| Pop     | O(1)  | O(1)  | O(n)   | O(1)       |
| Search  | O(n)  | O(n)  | O(n)   | O(n)       |
| Space   | O(n)  | O(n)  | O(n)   | O(n)       |

---

## 1️⃣1️⃣ Real-World Applications

1. **Browser History** - Back button (stack of URLs)
2. **Undo/Redo** - Text editors store operations
3. **Function Calls** - Call stack manages recursion
4. **Expression Parsing** - Compilers evaluate syntax
5. **Memory Management** - Stack memory in CPU
6. **Backtracking** - Maze solving, N-Queens
7. **Graph DFS** - Depth-first search uses stack
8. **Balanced Parentheses** - Compiler syntax checking
9. **Stock Span** - Finding nearest greater element
10. **Decode String** - Nested brackets evaluation

---

## 1️⃣2️⃣ Interview Tips

📌 **Key Points to Remember:**

- Always check for empty/full conditions
- Understand difference from Queue (FIFO vs LIFO)
- Know both array & linked list implementations
- Practice classic problems repeatedly
- Explain your approach before coding
- Handle edge cases explicitly

📌 **Common Mistakes:**

- Forgetting to check empty/full
- Confusing with Queue
- Off-by-one errors with array index
- Not handling overflow/underflow
- Using wrong data structure

---

## 1️⃣3️⃣ Summary

**Stack = LIFO Data Structure**

| Aspect       | Details                              |
| ------------ | ------------------------------------ |
| **Access**   | Only top element                     |
| **Main Ops** | Push, Pop, Peek                      |
| **Time**     | O(1) for all operations              |
| **Space**    | O(n) overall                         |
| **Best For** | Nested problems, Reversal, Recursion |
| **Avoid**    | Random access, FIFO needs            |

**Master the fundamentals, practice problems, and you'll ace any Stack interview question! 🚀**
