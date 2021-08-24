package com.weifuchow.leecode;


public class FirstVersionError {


    public static  int BAD_VERSION = 4;
    // 找到第一个出错的版本
    public int firstBadVersion(int n) {
        // 二分查找 (0 - 5)  n =5 n= n/2
        return findFirstBadVersion(1,n);
    }


    public int findFirstBadVersion(int left,int right){
        if(left == right && isBadVersion(left) == true && !isBadVersion(left - 1)){
            return left;
        }
        Long half =  (0L + left + right) /2 ;
        if(isBadVersion(half.intValue()) == true && !isBadVersion(half.intValue() - 1)){
            return half.intValue();
        }else if(isBadVersion(half.intValue()) == true){
            return findFirstBadVersion(left,half.intValue() -1);
        }else {
            return findFirstBadVersion(half.intValue()+1,right);
        }
    }

    public static void main(String[] args) {
        FirstVersionError solution = new FirstVersionError();
        solution.BAD_VERSION = 1702766719;
        System.out.println(solution.firstBadVersion(2126753390));
    }


    public boolean isBadVersion(int version){
        if(BAD_VERSION <= version){
            return true;
        }
        return  false;
    }
}
