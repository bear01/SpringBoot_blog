package com.lrm.service;

import com.lrm.po.User;

/**
 * Created by bear on 2020/3/15.
 */
public interface UserService {

    User checkUser(String username, String password);
}
