package com.albert.service;

import com.albert.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.Date;

import static org.testng.Assert.*;

@ContextConfiguration("classpath*:/smart-context.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testHasMatchUser(){
        boolean b1=userService.hasMatchUser("admin","123456");
        boolean b2=userService.hasMatchUser("admin","111111");
        assertTrue(b1);
        assertTrue(!b2);
    }

    @Test
    public void testFindUserByName() throws Exception{
        for (int i=0;i<100;i++){
            user user = userService.findUserByName("admin");
            assertEquals(user.getUserName(),"admin");
        }
    }

    @Test
    public void testAddLoginLog() {
        user user = userService.findUserByName("admin");
        user.setUserId(1);
        user.setUserName("admin");
        user.setLastIp("192.168.12.7");
        user.setLastVisit(new Date());
        userService.loginSuccess(user);
    }
}
