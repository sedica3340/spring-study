package com.study.springstudy.springmvc.chap04.common;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
@EqualsAndHashCode
public class Search extends Page {

    private String keyword, type;

    public Search() {
        this.keyword = "";
    }
}
