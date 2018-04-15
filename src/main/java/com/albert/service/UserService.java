package com.albert.service;

import com.albert.dao.LoginLogDao;
import com.albert.dao.UserDao;
import com.albert.domain.LoginLog;
import com.albert.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserDao userDao;
    private LoginLogDao loginLogDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setLoginLogDao(LoginLogDao loginLogDao) {
        this.loginLogDao = loginLogDao;
    }

    public boolean hasMatchUser(String userName,String password){
        int t=userDao.getMatchCount(userName,password);
        return t>0;
    }

    public user findUserByName(String userName){
        return userDao.findUserByName(userName);
    }

    @Transactional
    public void loginSuccess(user user){
        user.setCredits(5+user.getCredits());
        LoginLog loginLog=new LoginLog();
        loginLog.setUserId(user.getUserId());
        loginLog.setIp(user.getLastIp());
        loginLog.setLoginDate(user.getLastVisit());
        userDao.updateLoginInfo(user);
        loginLogDao.insertLoginLog(loginLog);
    }
}
