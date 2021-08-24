package com.weifuchow.leecode.offer.arrays;

//统计一个数字在排序数组中出现的次数。
//
//
//
// 示例 1:
//
//
//输入: nums = [5,7,7,8,8,10], target = 8
//输出: 2
//
public class SearchSortArrays {

    public int search(int[] nums, int target) {
        // 先二分再查
        int position = binarySearch(nums,0,nums.length-1,target);
        int counter = 0;
        if(position != -1){
            // 左边-
            counter = counter  + 1 ;
            int i = position - 1;
            while (i >= 0 && nums[i] == target){
                counter++;
                i = i -1;
            }
            int j = position + 1;
            while (j < nums.length && nums[j] == target){
                counter++;
                j = j + 1;
            }

            // 右边+
        }

        return counter;
    }


    // 5 8
    public int binarySearch(int[] nums,int begin,int end, int target){
        if(begin > end){
            return -1;
        }
        int midPosition = (begin + end)/2;
        int mid = nums[midPosition];
        if(mid == target) return midPosition;
        else if(mid > target){
            return binarySearch(nums,begin,midPosition - 1,target);
        }
        else {
            return binarySearch(nums,midPosition + 1,end,target);
        }
    }


    public static void main(String[] args) {
        SearchSortArrays solution = new SearchSortArrays();
        //
        int[] arrays = new int[]{1};
        int target = 1;
        System.out.println(solution.search(arrays,target));
    }

}
