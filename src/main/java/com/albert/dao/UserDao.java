package com.albert.dao;

import com.albert.domain.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private final static String MATCH_COUNT_SQL="select count(*) from users "+"where user_name=? and password = ?";
    private  final static String UPDATE_LOGIN_INFO_SQL = " UPDATE users SET " +
            " last_visit=?,last_ip=?,credits=?  WHERE user_id =?";
    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate=jdbcTemplate;
    }

    public int getMatchCount(String userName,String password){
        return jdbcTemplate.queryForObject(MATCH_COUNT_SQL,new Object[]{userName,password},Integer.class);
    }

    public user findUserByName(final String userName){
        String sqlStr = " SELECT user_id,user_name,credits "
                + " FROM users WHERE user_name =? ";
        final user user=new user();
        jdbcTemplate.query(sqlStr, new Object[]{userName}, new RowCallbackHandler() {
            public void processRow(ResultSet resultSet) throws SQLException {
                user.setCredits(resultSet.getInt("credits"));
                user.setUserId(resultSet.getInt("user_id"));
                user.setUserName(resultSet.getString("user_name"));
            }
        });
        return user;
    }

    public void updateLoginInfo(user user){
        jdbcTemplate.update(UPDATE_LOGIN_INFO_SQL,
                new Object[]{user.getLastVisit(),user.getLastIp(),user.getCredits(),user.getUserId()});
    }
}
