package com.weifuchow.jdk.learn.finallyy;

/**
 * Copyright © weifuchow., Ltd. . .
 *
 * @author: weifuchow
 * @date: 2021/4/19 17:33
 */
public class FuckResource implements AutoCloseable{

    private boolean isClose;

    public boolean isClose() {
        return isClose;
    }

    public void setClose(boolean close) {
        isClose = close;
    }

    @Override
    public void close() throws Exception {
        System.out.println("current close :" + isClose);
        if(isClose){
            throw new RuntimeException("is already closing ");
        }
        setClose(true);
        System.out.println("i am fucking  atuo close " + isClose());
    }
}
