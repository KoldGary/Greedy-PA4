package edu.wne.cs366;

import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;

/**
 * CS366 - PA4: Greedy Algorithms - Minimum Cost to Connect Sticks
 *
 * This assignment explores the greedy algorithm paradigm through the "Minimum Cost to Connect Sticks" problem.
 * Students will implement the SAME greedy algorithm using two different data structures:
 *   1. Greedy Naive: O(n²) using unsorted ArrayList (linear scans for minimums)
 *   2. Greedy Optimized: O(n log n) using PriorityQueue/heap (efficient minimum extraction)
 *
 * This demonstrates how data structure selection dramatically affects performance even when
 * the underlying algorithm (greedy strategy) remains identical.
 *
 * Problem: You have n sticks of varying lengths. The cost to connect two sticks is the sum of their lengths.
 * Connect all sticks into one stick with minimum total cost.
 *
 * Greedy Strategy: Always connect the two smallest sticks first.
 *
 * Example: sticks = [2, 4, 3]
 * - Connect 2 and 3 → cost = 5, remaining = [5, 4]
 * - Connect 5 and 4 → cost = 9, remaining = [9]
 * - Total cost = 5 + 9 = 14
 *
 * @author [Adrian Gary]
 * @date Due: November 6, 2025
 */
public class GreedyAlgorithms {

    /**
     * Calculate minimum cost to connect all sticks using GREEDY NAIVE approach.
     *
     * Greedy Strategy: Always combine the two smallest sticks first (same as optimized version).
     *
     * Implementation Approach:
     * - Store sticks in an UNSORTED ArrayList (keep unsorted throughout!)
     * - Each iteration: find two minimum elements via LINEAR SCAN through unsorted list (O(n))
     * - Remove them, add their sum back to list (still unsorted)
     * - Repeat until one stick remains
     *
     * Time Complexity: O(n²)
     * - n-1 iterations (combining n sticks into 1)
     * - Each iteration: O(n) to find two minimums via linear scan + O(n) to remove elements
     * - Total: O(n) × O(n) = O(n²)
     *
     * Space Complexity: O(n) for the list
     *
     * IMPORTANT: Keep the list UNSORTED throughout. Do NOT sort the list - this is intentional
     *            to demonstrate the O(n²) cost of repeated linear scans for minimum extraction.
     *
     * NOTE: The O(n²) complexity comes from using an UNSORTED list requiring O(n) scans,
     *       NOT from the greedy algorithm itself! See connectSticksHeap() for O(n log n) version.
     *
     * @param sticks array of stick lengths
     * @return minimum total cost to connect all sticks
     * @throws IllegalArgumentException if sticks array is null or has less than 2 elements
     */
    public static int connectSticksNaive(int[] sticks) {
    if (sticks == null || sticks.length < 2) {
        throw new IllegalArgumentException("sticks array must contain at least two elements");
    }
    List<Integer> list = new ArrayList<>();
    for (int len : sticks) {
        list.add(len);
    }
    int totalCost = 0;
    while (list.size() > 1) {
        int min1Index = 0;
        int min1Value = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            int val = list.get(i);
            if (val < min1Value) {
                min1Value = val;
                min1Index = i;
            }
        }
        int min2Index = -1;
        int min2Value = Integer.MAX_VALUE;
        for (int i = 0; i < list.size(); i++) {
            if (i == min1Index) continue;
            int val = list.get(i);
            if (val < min2Value) {
                min2Value = val;
                min2Index = i;
            }
        }
        int combined = min1Value + min2Value;
        totalCost += combined;
        if (min1Index > min2Index) {
            list.remove(min1Index);
            list.remove(min2Index);
        } else {
            list.remove(min2Index);
            list.remove(min1Index);
        }
        list.add(combined);
    }

   
    return totalCost;
}
    /**
     * Calculate minimum cost to connect all sticks using GREEDY OPTIMIZED approach.
     *
     * Greedy Strategy: Always combine the two smallest sticks first (SAME as naive version).
     *
     * Implementation Approach:
     * - Use Java's PriorityQueue (min-heap) to efficiently maintain smallest elements
     * - Build heap from input: O(n)
     * - Each iteration: 2 extractions + 1 insertion = O(log n) via HEAP operations
     * - Repeat until one stick remains
     *
     * Time Complexity: O(n log n)
     * - Build heap: O(n)
     * - n-1 iterations (combining n sticks into 1)
     * - Each iteration: O(log n) for heap operations
     * - Total: O(n) + O(n log n) = O(n log n)
     *
     * Space Complexity: O(n) for the heap
     *
     * KEY INSIGHT: Same greedy algorithm as naive, but using heap instead of unsorted list
     *              reduces time complexity from O(n²) to O(n log n)!
     *
     * @param sticks array of stick lengths
     * @return minimum total cost to connect all sticks
     * @throws IllegalArgumentException if sticks array is null or has less than 2 elements
     */
    public static int connectSticksHeap(int[] sticks) {
    if (sticks == null || sticks.length < 2) {
        throw new IllegalArgumentException("sticks array must contain at least two elements");
    }
    PriorityQueue<Integer> heap = new PriorityQueue<>();
    for (int len : sticks) {
        heap.offer(len);
    }
    int totalCost = 0;
    while (heap.size() > 1) {
        int first = heap.poll();
        int second = heap.poll();
        int combined = first + second;
        totalCost += combined;
        heap.offer(combined);
    }

    return totalCost;
}
    /**
     * Compare the performance of Greedy Naive vs Greedy Optimized approaches empirically.
     *
     * This method runs both implementations on the same input and measures their execution times,
     * demonstrating the practical difference between O(n²) and O(n log n) complexity when
     * using different data structures for the SAME greedy algorithm.
     *
     * @param sticks array of stick lengths
     */
    public static void compareApproaches(int[] sticks) {
        System.out.println("Comparing Greedy Naive vs Greedy Optimized");
        System.out.println("===========================================");
        System.out.printf("Input size: %d sticks%n", sticks.length);
        System.out.println("(Both use the SAME greedy algorithm, different data structures)\n");

        // Test greedy naive approach
        long startTime = System.nanoTime();
        int naiveCost = connectSticksNaive(sticks);
        long naiveTime = System.nanoTime() - startTime;

        // Test greedy optimized approach
        startTime = System.nanoTime();
        int heapCost = connectSticksHeap(sticks);
        long heapTime = System.nanoTime() - startTime;

        // Display results
        System.out.printf("Greedy Naive (unsorted list, O(n^2)):%n");
        System.out.printf("  Cost: %d%n", naiveCost);
        System.out.printf("  Time: %d nanoseconds (%.3f ms)%n%n", naiveTime, naiveTime / 1_000_000.0);

        System.out.printf("Greedy Optimized (heap, O(n log n)):%n");
        System.out.printf("  Cost: %d%n", heapCost);
        System.out.printf("  Time: %d nanoseconds (%.3f ms)%n%n", heapTime, heapTime / 1_000_000.0);

        System.out.printf("Speedup: %.2fx%n", (double) naiveTime / heapTime);
        System.out.printf("Results match: %s (same greedy algorithm!)%n", naiveCost == heapCost);
    }

    /**
     */
    public static void main(String[] args) {
        System.out.println("CS366 - PA4: Greedy Algorithms - Minimum Cost to Connect Sticks");
        System.out.println("===============================================================\n");

        // Example test cases
        int[][] testCases = {
            {2, 4, 3},           // Small example from problem description
            {1, 8, 3, 5},        // Another small example
            {4, 3, 2, 6, 1},     // Medium example
        };

        System.out.println("Testing with small examples:");
        System.out.println("----------------------------\n");

        for (int i = 0; i < testCases.length; i++) {
            int[] sticks = testCases[i];
            System.out.printf("Test Case %d: ", i + 1);
            printArray(sticks);

            int naiveCost = connectSticksNaive(sticks);
            int heapCost = connectSticksHeap(sticks);

            System.out.printf("  Naive approach cost: %d%n", naiveCost);
            System.out.printf("  Heap approach cost:  %d%n", heapCost);
            System.out.printf("  Results match: %s%n%n", naiveCost == heapCost);
        }

        // Performance comparison with larger input
        System.out.println("\nPerformance Comparison with Larger Input:");
        System.out.println("==========================================\n");

        // Generate larger test case
        int[] largeTest = generateRandomSticks(1000, 1, 100);
        System.out.printf("Testing with %d random sticks...%n%n", largeTest.length);

        compareApproaches(largeTest);

        System.out.println("\n===============================================================");
        System.out.println("Complete the theoretical analysis in ANALYSIS.md");
        System.out.println("Run tests with: ./gradlew test");
    }

    /**
     * Helper method to generate random stick lengths for testing.
     *
     * @param count number of sticks to generate
     * @param minLength minimum stick length
     * @param maxLength maximum stick length
     * @return array of random stick lengths
     */
    private static int[] generateRandomSticks(int count, int minLength, int maxLength) {
        int[] sticks = new int[count];
        for (int i = 0; i < count; i++) {
            sticks[i] = minLength + (int)(Math.random() * (maxLength - minLength + 1));
        }
        return sticks;
    }

    /**
     * Helper method to print an array.
     *
     * @param arr array to print
     */
    private static void printArray(int[] arr) {
        System.out.print("[");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i]);
            if (i < arr.length - 1) System.out.print(", ");
        }
        System.out.println("]");
    }
}
