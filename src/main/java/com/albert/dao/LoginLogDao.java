package com.albert.dao;

import com.albert.domain.LoginLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class LoginLogDao {
    private JdbcTemplate jdbcTemplate;
    private final static String INSERT_LOGIN_LOG_SQL= "INSERT INTO login_log(user_id,ip,login_datetime) VALUES(?,?,?)";

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public void insertLoginLog(LoginLog loginLog){
        jdbcTemplate.update(INSERT_LOGIN_LOG_SQL,new Object[]{
                loginLog.getUserId(),loginLog.getIp(),loginLog.getLoginDate()
        });
    }

}
