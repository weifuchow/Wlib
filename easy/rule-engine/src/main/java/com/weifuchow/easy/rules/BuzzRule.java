package com.weifuchow.easy.rules;

import org.jeasy.rules.annotation.*;

@Rule
public class BuzzRule {

    @Condition
    public boolean isBuzz(@Fact("number") Integer number) {
        return number % 7 == 0;
    }

    @Action
    public void printBuzz() {
        System.out.print("buzz");
    }

    @Priority
    public int getPriority() {
        return 2;
    }
}