package com.sid.mobile.helper;

import java.util.HashMap;

public class TestData {
    public static final HashMap<String, Object> PLAN = new HashMap<String, Object>() {
        {
            put("name", "88元套餐");
            put("price", 88);
            put("data", 500);
            put("calls", 100);
        }
    };

    public static final HashMap<String, Object> PRODUCT = new HashMap<String, Object>() {
        {
            put("type", "data");
            put("amount", 500);
            put("price", 30);
        }
    };
}
