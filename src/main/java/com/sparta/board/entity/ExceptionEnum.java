package com.sparta.board.entity;

import lombok.Getter;

@Getter
public enum ExceptionEnum {

    NOT_EXIST_BOARD(400,"글이 존재하지 않습니다"),
    NOT_MY_CONTENT_DELETE(400,"본인의 글만 삭제 가능합니다"),
    NOT_MY_CONTENT_MODIFY(400,"본인의 글만 수정 가능합니다"),
    NOT_VALID_TOKEN(400,"토큰이 유효하지 않습니다"),
    PASSWORD_WRONG(400,"잘못된 패스워드 입니다"),
    NOT_EXIST_USER(400,"사용자가 없습니다"),
    DUPLICATE_USER(400,"중복된 사용자 입니다");

    private final int code;
    private final String msg;

    ExceptionEnum(int code,String msg) {

        this.code = code;
        this.msg = msg;
    }
}
