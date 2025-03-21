package com.example.bank.util;

import java.util.HashMap;
import java.util.Map;

import com.example.bank.common.Constants;

public class ReturnEntity extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public ReturnEntity() {
        put(Constants.CODE, Constants.SUCCESS);
        put(Constants.MSG, Constants.SUCCESS_MSG);
    }

    public static ReturnEntity error(String msg) {
        return error(Constants.FAILED, msg);
    }

    public static ReturnEntity error(int code, String msg) {
        ReturnEntity rE = new ReturnEntity();
        rE.put(Constants.CODE, code);
        rE.put(Constants.MSG, msg);
        return rE;
    }

    public static ReturnEntity ok(String msg) {
        ReturnEntity rE = new ReturnEntity();
        rE.put(Constants.MSG, msg);
        return rE;
    }

    public static ReturnEntity ok(Map<String, Object> map) {
        ReturnEntity rE = new ReturnEntity();
        rE.putAll(map);
        return rE;
    }

    public static ReturnEntity ok() {
        return new ReturnEntity();
    }

    public ReturnEntity put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
