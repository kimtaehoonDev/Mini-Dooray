package com.kimtaehoonki.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PROJECT_NAME_DUPLICATE("프로젝트의 이름이 중복되었습니다");
    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
