package com.weifuchow.b;

import com.weifuchow.commons.Module;
import com.weifuchow.commons.SourceType;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:51
 */
public class BModuleInfo implements Module {
    @Override
    public void print() {
        System.out.println("fuck " + " ==> " + SourceType.B);
    }
}
