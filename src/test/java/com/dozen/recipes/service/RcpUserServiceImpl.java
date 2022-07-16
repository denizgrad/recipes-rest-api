package com.dozen.recipes.service;

import com.dozen.recipes.dao.UserDao;
import com.dozen.recipes.model.entity.RcpUser;
import com.dozen.recipes.service.impl.RcpUserServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.stereotype.Service;

@RunWith(MockitoJUnitRunner.class)
class RcpUserServiceImplTest{

    @Mock
    UserDao userDao;

    @InjectMocks
    RcpUserServiceImpl service;

    @Test
    public void findByUserNameTest()
    {
//        findByUserName
    }
}
