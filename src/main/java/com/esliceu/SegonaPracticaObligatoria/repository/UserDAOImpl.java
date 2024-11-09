package com.esliceu.SegonaPracticaObligatoria.repository;

import com.esliceu.SegonaPracticaObligatoria.model.USER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImpl implements UserDAO{
    @Autowired
  JdbcTemplate jdbcTemplate;
    @Override
    public void save(USER user) {
        jdbcTemplate.update("insert into USER (username,name,password) values (?,?,?)"
                , user.getUsername(), user.getName(), user.getPassword());
    }

    @Override
    public boolean comprobaSiUserExisteix(String username) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM USER WHERE username = ?", Integer.class, username);

        return count != null && count > 0;
    }

}
