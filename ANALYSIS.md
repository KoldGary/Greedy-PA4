# CS366 - PA4: Theoretical Analysis

---

## Problem 1: Greedy Choice Property

**Prove that the greedy choice (always combining the two smallest sticks) leads to an optimal solution.**

### Greedy Choice Property Explanation

The greedy choice property for the stick-combining problem states that in any optimal solution, we can always choose to combine the two smallest sticks first without losing optimality.
### Informal Proof

[Provide an informal proof using exchange argument or similar technique. Consider: 

Let the two smallest sticks be aaa and bbb. The greedy algorithm merges these two first at a cost a+ba + ba+b. 

Suppose there exists an optimal solution that does not merge aaa and bbb first. Instead, it merges some other pair of sticks, xxx and yyy. Since aaa and bbb are the two smallest sticks, we must have: X≥a and y≥b, x≥a and y≥b. After this exchange, what happens to the remaining sticks? We exchange the merges and swape the depth or frequency with the values changes except that aaa and bbb are already merged. All other sticks and all subsequent merges remain unchanged. This means that in the exchanged solution, the later costs add up the same way as before, except now the small sticks aaa and bbb don’t keep appearing in future merges. Since we reduced the first merge cost and none of the later merges get more expensive, the total cost cannot be worse.. This means that the cost of the first merge in this “optimal” solution is:x+y≥a+b.x + y \ge a + b.x+y≥a+b. Since the greedy first merge costs a+ba + ba+b, and the other solution’s first merge costs at least x+yx + yx+y, the exchanged solution never increases the total cost.

 Therefore, from any solution that does not merge the two smallest sticks first, we can transform it into a solution of equal or lower cost that does merge them first. Therefore, an optimal solution involves merging the two smallest sticks in the first step. This means the greedy choice is always safe, and repeating this choice at every step yields an optimal overall solution.


- What happens if we choose sticks other than the two smallest?
If you merge a larger stick instead of a smallest stick at some step, you pay a larger immediate cost.
- How does the greedy choice affect the total cost?
Merging the two smallest minimizes the immediate cost paid at that step, and because that smaller merged result participates in future merges (with costs that are the sums of things already paid plus new sums), choosing smaller sums early prevents large sums from accumulating later.
- Why can't any other choice lead to a better solution?]
Any other choice can be transformed (by local swaps/exchanges) into one that merges the two smallest first without increasing total cost.

---

## Problem 2: Time Complexity Analysis - Greedy Naive Approach

**Analyze the time complexity of the greedy naive approach using an UNSORTED list.**

**Important Note:** This approach uses the same **greedy algorithm** as the optimized version (always combine the two smallest sticks). The difference is in the **implementation efficiency** - this version uses an **unsorted ArrayList** requiring O(n) linear scans to find minimums, rather than O(log n) heap operations.

### Algorithm Description

[Briefly describe the steps of the greedy naive algorithm - emphasize that it follows the greedy strategy but uses an **unsorted ArrayList** requiring linear scans for each minimum extraction. The list remains unsorted throughout - no sorting is performed.]
The naive greedy algorithm repeatedly merges the two smallest sticks, but keeps all sticks in an unsorted ArrayList, never sorting it at any point. In each iteration, it follows the greedy rule by performing a linear scan over the entire list to find the smallest and second-smallest sticks, removing them, merging them, and inserting the new stick back into the still unsorted list.
### Iteration Analysis

[Explain why each iteration takes O(n) time. Consider:
Each iteration is O(n) because the list is unsorted, so finding the smallest stick requires a full O(n) scan, and finding the second-smallest requires another O(n) scan (or a single O(n) pass tracking both). 
- Finding the first minimum in the **unsorted list**: O(n)- must scan all elements
- Finding the second minimum in the **unsorted list**: O(n)- must scan all elements again
- Removing elements: O(?)
- Adding element back to the **unsorted list**: O(n)]

### Total Complexity Calculation

[Show the calculation:

- Number of iterations: n-1
- Cost per iteration: O(n)
- Total: (n-1) * O(n)=O(N^2)

### Recurrence Relation

[Write and solve the recurrence relation for the greedy naive approach]
T(n) = T(n−1) +O (n),T(1) = 0. 
**Conclusion:** The greedy naive approach has time complexity of O(N^2)

---

## Problem 3: Time Complexity Analysis - Greedy Optimized Approach

**Analyze the time complexity of the greedy optimized approach using a priority queue (heap).**

**Important Note:** This approach uses the **exact same greedy algorithm** as the naive version - it always combines the two smallest sticks. The only difference is using a heap data structure to efficiently find minimums, rather than linear scans.

### Heap Operations

[Explain the time complexity of each heap operation used:

- Building initial heap: O(n)
- Extract minimum (poll): O(log n)
- Insert (offer): O(log n)

### Build-Heap Analysis

[Explain why building a heap from n elements is O(n), not O(n log n)]

Building a heap from nnn elements is O(n) because of how the heapify process works from the bottom up, not because each insertion takes O(log⁡n)
### Iteration Analysis

[Analyze one iteration:

- Extract first minimum: O(logN)
- Extract second minimum: O(logN)
- Insert sum back: O(logN)
- Total per iteration: O(logN) + O(logN + O(logN) = O(logN) x 3 = O(logN)

### Total Complexity Calculation

[Show the calculation:

- Build heap: O(n)
- Number of iterations: n-1
- Cost per iteration: O(log n) + O(log n) + O(log n) = O(log n)
- Total: O(n) + (n-1)O(log n) = O(n log n)

**Conclusion:** The greedy optimized (heap) approach has time complexity of O(n log n)

---

## Problem 4: Performance Prediction

**Predict the speedup factor for different input sizes when comparing Greedy Naive vs Greedy Optimized implementations.**

**Important Note:** Both implementations use the same greedy strategy. This analysis demonstrates how data structure choice affects performance while keeping the algorithm constant.

### Theoretical Speedup Calculations

For input size n, compare O(n²) vs O(n log n):

**n = 100:**

- Naive: O(100²) = [10,000]
- Heap: O(100 × log₂ 100) ≈ [6.64]
- Speedup factor: [15.1×]

**n = 1,000:**

- Naive: O(1000²) = [1,000,000]
- Heap: O(1000 × log₂ 1000) ≈ [9.97]
- Speedup factor: [100.3×]

**n = 10,000:**

- Naive: O(10000²) = [100,000,000]
- Heap: O(10000 × log₂ 10000) ≈ [13.29]
- Speedup factor: [752.3]

### Empirical Results

[After implementing your solution, run compareApproaches() with different input sizes and record actual timing results]

| Input Size (n) | Naive Time (ms) | Heap Time (ms) | Actual Speedup |
| -------------- | --------------- | -------------- | -------------- |
| 100            |         1.4     |      0.3       |       4.7×     |
| 500            |         10.8 |         1.6    |         6.7×   |
| 1,000          |         41.2    |        4.2     |       9.8×     |
| 5,000          |         812.5   |       19.3     |       42.1×    |

### Analysis of Results

[Compare theoretical predictions with empirical results. Discuss:

- Do the empirical results match your theoretical predictions?
Yes, the empirical results generally match the theoretical predictions.
- At what input size does the heap approach become significantly faster
In my experiments, the heap approach becomes significantly faster somewhere between N=500 and N=1000
- What factors might cause differences between theoretical and empirical speedup?]
Constant factors and implementation details: Big-O hides constants. ArrayList and PriorityQueue have different constant overheads in Java.
---

_Course content developed by Declan Gray-Mullen for WNEU with Claude_
