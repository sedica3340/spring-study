package com.study.springstudy.database.chap01;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SpringJdbc {

    private final JdbcTemplate template;

    // INSERT
    public int save(Person person) {

        String sql = "INSERT INTO tbl_person VALUES (?, ?, ?)";
        return template.update(sql, person.getId(), person.getPersonName(), person.getPersonAge());

    }

    public boolean delete(long id) {
        String sql = "DELETE FROM tbl_person WHERE id = ?";
        int result = template.update(sql, id);
        return result == 1;
    }

    // UPDATE
    public boolean update(Person newPerson) {
        // 이름, 나이 수정
        String sql = "UPDATE tbl_person " +
                "SET person_name = ?, person_age = ? " +
                "WHERE id = ?";
        int flag = template.update(sql, newPerson.getPersonName()
                , newPerson.getPersonAge(), newPerson.getId());
        return flag == 1;
    }

    public List<Person> findAll() {
        String sql = "SELECT * FROM tbl_person";
        return template.query(sql, (rs, rowNum) -> new Person(rs));
    }

    public Person findOne(long id) {
        String sql = "SELECT * FROM tbl_person WHERE id = ?";
        return template.queryForObject(sql, (rs, n) -> new Person(rs), id);
    }
}
