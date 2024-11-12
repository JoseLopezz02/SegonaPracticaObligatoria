package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void save(USER user) {
        jdbcTemplate.update("insert into USER (username,name,password) values (?,?,?)"
                , user.getUsername(), user.getName(), user.getPassword());
    }

    @Override
    public boolean comprobaSiUserExisteix(String username) {
        Integer aparicionesUser = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM USER WHERE username = ?", Integer.class, username);

        return aparicionesUser != null && aparicionesUser > 0;
    }

    @Override
    public USER checkUserInDbUsingPasswAndUserName(String username, String password) {
        try {
            String sql = "SELECT * FROM USER WHERE username = ? AND password = ?";
            return jdbcTemplate.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(USER.class),
                    username,
                    password
            );
        } catch (Exception e) {
            return null;
        }
    }

}
