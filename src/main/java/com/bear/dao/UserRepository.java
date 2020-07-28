package com.bear.dao;

import com.bear.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by bear on 2020/3/15.
 */
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsernameAndPassword(String username, String password);
}
