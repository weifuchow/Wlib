package com.weifuchow.easy.rules;

import org.jeasy.rules.annotation.*;

@Rule
public class FizzRule {

    @Condition
    public boolean isFizz(@Fact("number") Integer number) {
        return number % 5 == 0;
    }

    @Action
    public void printFizz() {
        System.out.print("fizz");
    }

    @Priority
    public int getPriority() {
        return 1;
    }
}