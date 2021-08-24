package com.weifuchow.c;

import com.weifuchow.commons.Module;
import com.weifuchow.commons.SourceType;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/5/25 15:51
 */
public class CModuleInfo implements Module {
    @Override
    public void print() {
        System.out.println("1.5 version fuck " + " ==> " + SourceType.C);
    }
}
