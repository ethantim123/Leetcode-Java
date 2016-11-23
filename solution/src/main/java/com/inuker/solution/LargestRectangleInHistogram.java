package com.inuker.solution;

/**
 * Created by dingjikerbo on 16/11/23.
 */

public class LargestRectangleInHistogram {

    /**
     * 这题关键是对于每根柱子，往两边延伸到某根柱子比自己矮或到边界为止。
     * 暴力办法是依次循环，时间复杂度O(n^2)，空间复杂度O(n)
     * 采用动态规划延伸的时候可以根据之前的结果跳着走，最优时间复杂度O(n)，最差时间复杂度O(n^2)，空间复杂度O(n)
     * 采用压栈的方法最巧妙，时间复杂度O(n)，空间复杂度O(n)
     */

    // 这种超时了，复杂度O(n^2)
    public int largestRectangleArea(int[] heights) {
        int max = 0;
        for (int i = 0, j, k; i < heights.length; i++) {
            for (j = i - 1; j >= 0 && heights[j] >= heights[i]; j--);
            for (k = i + 1; k < heights.length && heights[k] >= heights[i]; k++);
            max = Math.max(max, (k - j - 1) * heights[i]);
        }
        return max;
    }

    // 耗时4ms
    public int largestRectangleArea2(int[] heights) {
        int len = heights.length, j;
        int[] left = new int[len], right = new int[len];

        for (int i = 0; i < len; i++) {
            for (j = i; j >= 1 && heights[j - 1] >= heights[i]; j = left[j - 1]);
            left[i] = j;
        }

        for (int i = len - 1; i >= 0; i--) {
            for (j = i; j < len - 1 && heights[j + 1] >= heights[i]; j = right[j + 1]);
            right[i] = j;
        }

        int max = 0;
        for (int i = 0; i < len; i++) {
            max = Math.max(max, (right[i] - left[i] + 1) * heights[i]);
        }
        return max;
    }


    public int largestRectangleArea3(int[] heights) {
        int len = heights.length, max = 0;

        int[] stack = new int[len];

        for (int i = 0, top = -1; i <= len; ) {
            int height = i == len ? 0 : heights[i];

            if (top < 0 || height > heights[stack[top]]) {
                stack[++top] = i++;
            } else {
                int k = stack[top--];
                max = Math.max(max, (top < 0 ? i : i - 1 - stack[top]) * heights[k]);
            }
        }

        return max;
    }
}
