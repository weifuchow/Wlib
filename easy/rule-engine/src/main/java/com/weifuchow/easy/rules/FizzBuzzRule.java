package com.weifuchow.easy.rules;

import org.jeasy.rules.support.composite.UnitRuleGroup;

public class FizzBuzzRule extends UnitRuleGroup {

    public FizzBuzzRule(Object... rules) {
        for (Object rule : rules) {
            addRule(rule);
        }
    }

    @Override
    public int getPriority() {
        return 0;
    }
}