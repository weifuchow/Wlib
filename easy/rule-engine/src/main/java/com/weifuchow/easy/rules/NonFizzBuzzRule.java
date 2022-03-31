package com.weifuchow.easy.rules;

import org.jeasy.rules.annotation.*;

@Rule
public class NonFizzBuzzRule {

    @Condition
    public boolean isNotFizzNorBuzz(@Fact("number") Integer number) {
        // can return true, because this is the latest rule to trigger according to assigned priorities
        // and in which case, the number is not fizz nor buzz
        return number % 5 != 0 || number % 7 != 0;
    }

    @Action
    public void printInput(@Fact("number") Integer number) {
        System.out.print(number);
    }

    @Priority
    public int getPriority() {
        return 3;
    }
}