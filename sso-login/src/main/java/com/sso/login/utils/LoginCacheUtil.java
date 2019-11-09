package com.sso.login.utils;

import com.sso.login.pojo.User;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户登录缓存工具类
 * @Date 2019年11月8日
 */
public class LoginCacheUtil {
    public static Map<String, User> loginUser = new HashMap<>();
}
