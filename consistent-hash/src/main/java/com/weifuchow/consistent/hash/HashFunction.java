package com.weifuchow.consistent.hash;

public interface HashFunction {
    long hash(String key);
}