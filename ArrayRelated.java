package algorithm;

import java.util.HashMap;
import java.util.Map;

class ArrayRelated {

    /**
     * #1: 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数（默认只存在唯一解，且数组中的元素不能重复使用）
     * 输入: nums = [2, 7, 11, 15], target = 9
     * 输出: [0, 1]
     * 时间复杂度: O(n)，空间复杂度: O(n)
     * https://leetcode.com/problems/two-sum
     */
    public static int[] twoSum(int[] nums, int target) {
        // key--数组元素值，value--数组下标
        Map<Integer, Integer> map = new HashMap<>();
        int length = nums.length;
        for (int i = 0; i < length; i++) {
            int complement = target - nums[i];
            // 判断map中是否包含与nums[i]和为target的元素
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            // 将nums[i]放入map用于后续判断
            map.put(nums[i], i);
        }
        throw new IllegalArgumentException("The sum of two elements does not exist as the target value.");
    }

    /**
     * #41: 给定一个未排序的整数数组，找出缺失的最小正整数
     * 输入: nums = [3, -1, 4, 1]
     * 输出: 2
     * 时间复杂度: O(n)
     * https://leetcode.com/problems/first-missing-positive
     */
    public static int firstMissingPositive(int[] nums) {
        int length = nums.length;
        /*
         * 若整数数组的长度为length，则最小正整数一定在[1,length]区间内
         * 如:[3, -1, 4, 1]中缺失的最小正整数一定在[1,4]区间内
         *    基于数组下标法进行查找---将元素3交换至下标2处；元素-1不在上述区间内，不做处理...
         *    最终数组中第一个元素值与(下标+1)不符的即为缺失的最小正整数
         *    [3, -1, 4, 2]-->[4, -1, 3, 2]-->[2, -1, 3, 4]-->[-1, 2, 3, 4]
         */
        for (int i = 0; i < length; i++) {
            int n = nums[i];
            while (n >= 1 && n <= length && n != i + 1) {
                // 交换
                nums[i] = nums[n - 1];
                nums[n - 1] = n;
                n = nums[i];
            }
        }
        for (int i = 0; i < length; i++) {
            if (nums[i] != i + 1) {
                return i + 1;
            }
        }
        return -1;
    }

    /**
     * #643: 给定一个数组，找出连续k个元素子数组中的最大平均值
     * 输入: nums = [1, 12, -5, -6, 50, 3], k = 4
     * 输出: sub = [12, -5, -6, 50], ave = 12.75
     * https://leetcode.com/problems/maximum-average-subarray-i
     */
    public static double maximumAverageSubarray(int[] nums, int k) {
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        double max = sum;
        int length = nums.length;
        for (int i = k; i < length; i++) {
            sum += (nums[i] - nums[i - k]);
            max = Math.max(max, sum);
        }
        return max / k;
    }

}
