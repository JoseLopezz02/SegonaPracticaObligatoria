package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(User user) {
        jdbcTemplate.update("insert into User (username,name,password) values (?,?,?)"
                , user.getUsername(), user.getName(), user.getPassword());
    }

    @Override
    public boolean comprobaSiUserExisteix(String username) {
        Integer aparicionesUser = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM User WHERE username = ?", Integer.class, username);

        return aparicionesUser != null && aparicionesUser > 0;
    }

    @Override
    public User checkUserInDbUsingPasswAndUserName(String username, String password) {
        try {
            String sql = "SELECT * FROM User WHERE username = ? AND password = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username,
                    password
            );
        } catch (Exception e) {
            return null;
        }
    }

}
