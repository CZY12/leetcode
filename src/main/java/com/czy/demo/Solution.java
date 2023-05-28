package com.czy.demo;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
//        boolean palindrome = isPalindrome(123321);
//        System.out.println(palindrome);
//        boolean b = queryString("0101011001101101010001001111110111100110110001001111001111111011010010101001011111010010001011011011", 5);
//        System.out.println(b);
//        String[] event1 = new String[2];
//        event1[0] = "01:15";
//        event1[1] = "02:00";
//        String[] event2 = new String[2];
//        event2[0] = "03:15";
//        event2[1] = "02:00";
//
//        boolean b = haveConflict(event1, event2);
//        System.out.println(b);

//        int[] bucket = new int[]{9, 0, 1};
//        int[] vat = new int[]{0, 2, 2};
//        int res = storeWater(bucket, vat);

//        int n = 7; int[][] edges = {{1,2},{1,3},{1,7},{2,4},{2,6},{3,5}};
//        int t = 2;int target = 5;
//        System.out.println(frogPosition(n, edges, t, target));

        String[] s = new String[]{"mll","edd","jii","tss","fee","dcc","nmm","abb","utt","zyy","xww","tss","wvv","xww","utt"};
        System.out.println(oddString(s));

    }

    //回文字串
    public static boolean isPalindrome(int x) {

        String s = String.valueOf(x);
        char[] chars = String.valueOf(x).toCharArray();
        int length = s.length();
        for (int i = 0; i < length / 2; i++) {
            if (chars[i] != chars[length - 1 - i]) {
                return false;
            }
        }
        return true;
    }

    public static boolean queryString(String s, int n) {
        Set<Integer> seen = new HashSet<Integer>();
        char[] chars = s.toCharArray();

        int e = (2 << 1);
        int h = '1' - '0';
        int w = e | h;
        for (int i = 0, m = chars.length; i < m; ++i) {
            int x = chars[i] - '0';
            if (x == 0) continue; // 二进制数从 1 开始
            for (int j = i + 1; x <= n; j++) {
                seen.add(x);
                if (j == m) break;
                x = (x << 1) | (chars[j] - '0'); // 子串 [i,j] 的二进制数
            }
        }
        return seen.size() == n;
    }

    public static boolean haveConflict(String[] event1, String[] event2) {
        if (event1 == null || event2 == null || event1.length == 0 || event2.length == 0) {
            return false;
        }

        String startTime1 = event1[0];
        String endTime1 = event1[event1.length - 1];
        String startTime2 = event2[0];
        if (startTime2.compareTo(endTime1) <= 0 && startTime2.compareTo(startTime1) <= 0) {
            return true;
        }
        return false;
    }

    //    作者：力扣官方题解
    //    链接：https://leetcode.cn/problems/o8SXZn/solutions/2276388/xu-shui-by-leetcode-solution-g4lx/
    //    来源：力扣（LeetCode）
    //    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    public static int storeWater(int[] bucket, int[] vat) {
        int n = bucket.length;
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> b[0] - a[0]);
        int cnt = 0;
        for (int i = 0; i < n; ++i) {
            if (bucket[i] == 0 && vat[i] != 0) {
                ++cnt;
                ++bucket[i];
            }
            if (vat[i] > 0) {
                pq.offer(new int[]{(vat[i] + bucket[i] - 1) / bucket[i], i});
            }
        }
        if (pq.isEmpty()) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        while (cnt < res) {
            int[] arr = pq.poll();
            int v = arr[0], i = arr[1];
            res = Math.min(res, cnt + v);
            if (v == 1) {
                break;
            }
            int t = (vat[i] + v - 2) / (v - 1);
            cnt += t - bucket[i];
            bucket[i] = t;
            pq.offer(new int[]{(vat[i] + bucket[i] - 1) / bucket[i], i});
        }
        return res;
    }

    //我们有一个 n 项的集合。给出两个整数数组 values 和 labels ，第 i 个元素的值和标签分别是 values[i] 和 labels[i]。还会给出两个整数 numWanted 和 useLimit 。
    //
    //从 n 个元素中选择一个子集 s :
    //
    //子集 s 的大小 小于或等于 numWanted 。
    //s 中 最多 有相同标签的 useLimit 项。
    //一个子集的 分数 是该子集的值之和。
    //
    //返回子集 s 的最大 分数 。
    //https://leetcode.cn/problems/largest-values-from-labels/description/
    public int largestValsFromLabels(int[] values, int[] labels, int numWanted, int useLimit) {
        int n = values.length;
        Integer[] bucket = new Integer[n];
        for (int i = 0; i < n; ++i) {
            bucket[i] = i;
        }

        //    排序:从大到小
        Arrays.sort(bucket, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return values[o2] - values[o1];
            }
        });
        // 存储当前label总数
        Map<Integer, Integer> map = new HashMap<>();
        int res = 0;
        int count = 0;
        for (int i = 0; i < n; i++) {
            int v = values[bucket[i]];
            if (map.containsKey(labels[bucket[i]])) {
                if (map.get(labels[bucket[i]]) <= useLimit) {
                    map.put(labels[bucket[i]], map.get(labels[bucket[i]]) + 1);
                    count++;
                    res += v;
                }
            } else {
                map.put(labels[bucket[i]], 1);
                count++;
                res += v;
            }
            // 子集个数达标，退出
            if (count == numWanted) {
                break;
            }

        }
        return res;
    }

    //给你一棵由 n 个顶点组成的无向树，顶点编号从 1 到 n。青蛙从 顶点 1 开始起跳。规则如下：
    //
    //在一秒内，青蛙从它所在的当前顶点跳到另一个 未访问 过的顶点（如果它们直接相连）。
    //青蛙无法跳回已经访问过的顶点。
    //如果青蛙可以跳到多个不同顶点，那么它跳到其中任意一个顶点上的机率都相同。
    //如果青蛙不能跳到任何未访问过的顶点上，那么它每次跳跃都会停留在原地。
    //无向树的边用数组 edges 描述，其中 edges[i] = [ai, bi] 意味着存在一条直接连通 ai 和 bi 两个顶点的边。
    //
    //返回青蛙在 t 秒后位于目标顶点 target 上的概率。与实际答案相差不超过 10-5 的结果将被视为正确答案。
    //https://leetcode.cn/problems/frog-position-after-t-seconds/description/
    public static double frogPosition(int n, int[][] edges, int t, int target) {
        //从1开始使用
        List<Integer>[] graph = new List[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }
        // 建立树
        for (int[] edge : edges) {
            graph[edge[0]].add(edge[1]);
            graph[edge[1]].add(edge[0]);
        }

        boolean[] seen = new boolean[n+1];
        return dfs(n, graph, t, target, seen,1);
    }

    public static double dfs(int n, List<Integer>[] graph, int t, int target, boolean[] seen, int i) {
        int nxt = i == 1 ? graph[i].size() : graph[i].size() - 1;
        if (t== 0 || nxt == 0) {
            // 时间使用完毕 或者无法继续遍历 时，当前节点是否等于目标节点，相等则概率为1，否则为0
            return target == i ? 1.00 : 0.00;
        }
        // 标记已遍历节点
        seen[i] = true;
        double ans = 0.00;
        //遍历当前节点的子节点
        for (int j : graph[i]) {
            if (!seen[j]) {
                // 不满足条件时概率为0，所以可以直接累加
                ans += dfs(n, graph, t - 1, target, seen, j);
            }
        }
        //当前节点的子节点概率/当前节点兄弟节点个数，表示从父节点遍历到目标节点的概率
        return ans/nxt;
    }


    //给你一个字符串数组 words ，每一个字符串长度都相同，令所有字符串的长度都为 n 。
    //
    //每个字符串 words[i] 可以被转化为一个长度为 n - 1 的 差值整数数组 difference[i] ，其中对于 0 <= j <= n - 2 有 difference[i][j] = words[i][j+1] - words[i][j] 。注意两个字母的差值定义为它们在字母表中 位置 之差，也就是说 'a' 的位置是 0 ，'b' 的位置是 1 ，'z' 的位置是 25 。
    //
    //比方说，字符串 "acb" 的差值整数数组是 [2 - 0, 1 - 2] = [2, -1] 。
    //words 中所有字符串 除了一个字符串以外 ，其他字符串的差值整数数组都相同。你需要找到那个不同的字符串。
    //
    //请你返回 words中 差值整数数组 不同的字符串。
    //https://leetcode.cn/problems/odd-string-difference/description/
    public static String oddString(String[] words) {
        int word1[] = getDiff(words[0]);
        int word2[] = getDiff(words[1]);
        if (Arrays.equals(word2,word1)) {
            for (int i = 2; i <words.length ; i++) {
                if (Arrays.equals(getDiff(words[i]),word1)) {
                    return words[i];
                }
            }
        }
        return Arrays.equals(word1,getDiff(words[2])) ? words[1] : words[0];

    }
    public static int[] getDiff(String s1) {
        int diff[] = new int[s1.length()-1];
        for (int i = 0; i < s1.length()-1; i++) {
            diff[i] = s1.charAt(i+1) - s1.charAt(i);
        }
        return diff;
    }
}
