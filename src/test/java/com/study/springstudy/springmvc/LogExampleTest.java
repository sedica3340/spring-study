package com.study.springstudy.springmvc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LogExampleTest {

    @Autowired LogExample logExample;


    @Test
    @DisplayName("마마마마마")
    void logTest() {
        logExample.showLog();
    }


}