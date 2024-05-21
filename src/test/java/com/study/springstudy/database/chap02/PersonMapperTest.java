package com.study.springstudy.database.chap02;

import com.study.springstudy.database.chap01.Person;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonMapperTest {

    @Autowired
    PersonMapper mapper;


    @Test
    @DisplayName("마이바티스 매퍼로 사람정보를 등록한다")
    void saveTest() {
        //given
        Person p = new Person(9999, "마이바", 59);
        //when
        boolean flag = mapper.save(p);
        //then
        assertTrue(flag);
    }


    @Test
    @DisplayName("아이디로 사람 정보 삭제한다.")
    void delTest() {
        //given
        long id = 9999;
        //when
        boolean flag = mapper.delete(id);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("아이디가 33인 사람의 정보를 수정한다.")
    void modifyTest() {
        //given
        Person p = new Person(33, "사기꾼",21);
        //when
        boolean flag = mapper.update(p);
        //then
        assertTrue(flag);
    }

    @Test
    @DisplayName("모든 사람의 정보를 받아오고 사이즈는 4이다.")
    void findAllTest() {
        //given
        //when
        List<Person> personList = mapper.findAll();
        //then
        personList.forEach(System.out::println);
        assertEquals(4,personList.size());
    }


    @Test
    @DisplayName("아이디가 64인 사람의 이름은 감자이고 나이는 2이다.")
    void findTest() {
        //given
        long id = 64;
        //when
        Person p = mapper.findOne(id);
        //then
        System.out.println("p = " + p);
        assertEquals("감자", p.getPersonName());
        assertEquals(2, p.getPersonAge());
    }

    @Test
    @DisplayName("사람수와 이름리스트를 조회한다")
    void findNamesTest() {
        //given

        //when
        List<String> names = mapper.findNames();
        int count = mapper.count();
        //then
        names.forEach(System.out::println);
        System.out.println("count = " + count);
    }



}