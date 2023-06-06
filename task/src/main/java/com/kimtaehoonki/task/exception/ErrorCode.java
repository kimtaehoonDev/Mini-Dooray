package com.kimtaehoonki.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PROJECT_NAME_DUPLICATE("프로젝트의 이름이 중복되었습니다"),
    PROJECT_NOT_FOUND("존재하지 않는 프로젝트입니다"),
    AUTHORIZED("해당 자원에 접근권한이 없습니다"),
    AUTHENTICATED("인증되지 않은 사용자입니다"),
    PROJECT_EXIT("이미 종료된 프로젝트입니다");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
