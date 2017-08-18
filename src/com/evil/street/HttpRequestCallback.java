package com.evil.street;

public interface HttpRequestCallback {
    void response(int responseCode,String respone);
    void response(int responseCode,String url,int num);
    boolean response(int responseCode);//返回false则不执行response(int responseCode,String respone)方法
    void failure(String error);
}
