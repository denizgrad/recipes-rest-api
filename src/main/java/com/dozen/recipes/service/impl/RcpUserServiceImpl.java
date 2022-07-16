package com.dozen.recipes.service.impl;

import com.dozen.recipes.dao.UserDao;
import com.dozen.recipes.model.entity.RcpUser;
import com.dozen.recipes.service.RcpUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RcpUserServiceImpl implements RcpUserService {

    private final UserDao userDao;

    @Override
    public RcpUser findByUserName(String userName) {
        return userDao.findByUsername(userName);
    }
}
