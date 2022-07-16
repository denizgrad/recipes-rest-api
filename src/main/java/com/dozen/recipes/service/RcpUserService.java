package com.dozen.recipes.service;

import com.dozen.recipes.model.entity.RcpUser;

public interface RcpUserService {
   RcpUser findByUserName(String userName);
}
