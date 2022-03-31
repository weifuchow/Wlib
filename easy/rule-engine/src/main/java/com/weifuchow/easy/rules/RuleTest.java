package com.weifuchow.easy.rules;

import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

/**
 * Copyright © 2016-2021 Stander robots RIOT platform
 *
 * @Desc RuleTest
 * @Author zhouweifu
 * @Date 2022/1/14
 */
public class RuleTest {

    public static void main(String[] args) {

        // create facts
        Facts facts = new Facts();

        // create rules
        Rules rules = new Rules();
        rules.register(new HelloWorldRule());

        // create a rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();
        rulesEngine.fire(rules, facts);

    }
}
