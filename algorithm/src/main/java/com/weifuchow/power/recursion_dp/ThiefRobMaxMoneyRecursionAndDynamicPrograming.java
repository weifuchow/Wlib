package com.weifuchow.power.recursion_dp;

import java.util.HashMap;

//你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
// 如果两间相邻的房屋在同一晚上
//被小偷闯入，系统会自动报警。
//
// 给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
public class ThiefRobMaxMoneyRecursionAndDynamicPrograming {

    public int rob(int[] nums) {
//        return robRecursion(nums,0,-2);
//        return robRecursionByMemoryHash(nums,0,-2,new HashMap<>());
//        return robRecursion1(nums,0);
        return robDp(nums);
    }

    //输入：[1,2,3,1]
    //输出：4
    //解释：偷窃 1 号房屋 (金额 = 1) ，然后偷窃 3 号房屋 (金额 = 3)。
    //   偷窃到的最高金额 = 1 + 3 = 4 。
    // 示例 2：

    //
    //输入：[2,7,9,3,1]
    //输出：12
    //解释：偷窃 1 号房屋 (金额 = 2), 偷窃 3 号房屋 (金额 = 9)，接着偷窃 5 号房屋 (金额 = 1)。
    //偷窃到的最高金额 = 2 + 9 + 1 = 12 。
    // 从index 开始，偷取最大的金额。last表示上次选择的哪一家
    //  last初始值为 0,
    public int robRecursion(int[] nums,int index,int last) {
        // 表示无路可走
        if(nums.length == index ){
            return 0;
        }else {
            // last 表示上次选择了哪一家，一开始初始化为0，会有歧义，
            if(index - 1 != last){
                int selectNext = robRecursion(nums,index+1,last);
                int selectCurrent = nums[index] + robRecursion(nums,index+1,index);
                return Math.max(selectNext,selectCurrent);
            }
            return robRecursion(nums,index+1,last);
        }
    }

    // 表示可以选择这一家进行打劫。或者不选这一家
    public int robRecursion1(int[] nums,int index) {
        // 表示无路可走
        if(nums.length <= index ){
            return 0;
        }else {
            // last
            int selectNext = robRecursion1(nums,index+1);
            int selectCurrent = nums[index] + robRecursion1(nums,index+2);
            return Math.max(selectNext,selectCurrent);
        }
    }

    public int robDp(int[] nums) {
        // 表示无路可走
        // avoid 数据越界
        // 0 length length+1
        int[] dp = new int[nums.length+2];
        for (int index = nums.length -1; index >= 0 ; index--) {
            int selectNext = dp[index+1];
            int selectCurrent = nums[index] + dp[index+2];
            dp[index] =  Math.max(selectNext,selectCurrent);
        }
        return dp[0];
    }

    public int robRecursionByMemoryHash(int[] nums, int index, int last, HashMap<String,Integer> map) {
        // 表示无路可走
        if(map.get(index+","+last) != null){
            return map.get(index+","+last);
        }

        if(nums.length == index ){
            map.put(index+","+last , 0);
            return map.get(index+","+last);
        }else {
            // 选择当前这一家，与不选择当前这一家
            if(index - 1 != last){
                int selectNext = robRecursionByMemoryHash(nums,index+1,last,map);
                int selectCurrent = nums[index] + robRecursionByMemoryHash(nums,index+1,index,map);
                int result =  Math.max(selectNext,selectCurrent);
                map.put(index+","+last , result);
                return map.get(index+","+last);
            }
            int result =  robRecursionByMemoryHash(nums,index+1,last,map);
            map.put(index+","+last , result);
            return map.get(index+","+last);
        }
    }






    public static void main(String[] args) {
        ThiefRobMaxMoneyRecursionAndDynamicPrograming solution = new ThiefRobMaxMoneyRecursionAndDynamicPrograming();

        Long now = System.currentTimeMillis();
//        int[] arrays = new int[]{114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205};
        int[] arrays = new int[]{114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205,169,241,202,144,240};
        System.out.println(solution.rob(arrays));
        System.out.println("recursion use time = > " + (System.currentTimeMillis() -  now) );

        now = System.currentTimeMillis();
//        int[] arrays = new int[]{114,117,207,117,235,82,90,67,143,146,53,108,200,91,80,223,58,170,110,236,81,90,222,160,165,195,187,199,114,235,197,187,69,129,64,214,228,78,188,67,205,94,205};
        System.out.println(solution.robDp(arrays));
        System.out.println("dp use time = > " + (System.currentTimeMillis() -  now) );


    }
}
