package list;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author xuhongyu
 * @create 2022-06-16 9:56 上午
 */
public class FindPairs {

    /**
     * 给定一个整数数组和一个整数 k，你需要在数组里找到 不同的 k-diff 数对，并返回不同的 k-diff 数对 的数目。
     * <p>
     * 这里将 k-diff 数对定义为一个整数对 (nums[i], nums[j])，并满足下述全部条件：
     * <p>
     * 0 <= i < j < nums.length
     * |nums[i] - nums[j]| == k
     * 注意，|val| 表示 val 的绝对值。
     */

    public static void main(String[] args) {
        FindPairs findPairs = new FindPairs();
        int[] nums = {3, 1, 4, 1, 5};
        int pairs = findPairs.findPairs1(nums, 2);
        System.out.println(pairs);

    }

    public int findPairs(int[] nums, int k) {
        Set<Integer> visited = new HashSet<Integer>();
        Set<Integer> res = new HashSet<Integer>();
        for (int num : nums) {
            if (visited.contains(num - k)) {
                res.add(num - k);
            }
            if (visited.contains(num + k)) {
                res.add(num);
            }
            visited.add(num);
        }
        return res.size();
    }

    public int findPairs1(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 0;
        // 滑动窗口 [i, j]
        for (int i = 0, j = 1; j < n; j++) {
            while (nums[j] - nums[i] > k) {
                i++;
            }
            if (i == j) {
                continue;
            }
            if (nums[j] - nums[i] == k) {
                ans++;
                // 跳过相同元素
                while (j + 1 < n && nums[j] == nums[j + 1]) {
                    j++;
                }
            }
        }
        return ans;
    }


}
