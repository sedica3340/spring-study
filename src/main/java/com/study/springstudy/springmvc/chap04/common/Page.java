package com.study.springstudy.springmvc.chap04.common;

import lombok.*;

@Getter @ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Page {

    private int pageNo; // 클라이언트가 요청한 페이지 번호
    private int amount; // 클라이언트가 요청한 한페이지당 게시물 목록

    public Page () {
        this.pageNo = 1;
        this.amount = 6;
    }

    public int getPageStart () {
        return this.amount * (this.pageNo - 1);
    }

    public void setPageNo(int pageNo) {
        if(pageNo < 1   ) {
            this.pageNo = 1;
            return;
        }
        this.pageNo = pageNo;
    }

    public void setAmount(int amount) {
        if (amount < 6 || amount > 60) {
            this.amount = 6;
            return;
        }
        this.amount = amount;
    }
}
