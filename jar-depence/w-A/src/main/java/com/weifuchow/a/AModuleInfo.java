package com.weifuchow.a;

import com.weifuchow.commons.Module;
import com.weifuchow.commons.SourceType;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:51
 */
public class AModuleInfo implements Module {
    @Override
    public void print() {
        System.out.println("fuck " + " ==> " + SourceType.A);
    }
}
