package com.kimtaehoonki.task.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    PROJECT_NAME_DUPLICATE("프로젝트의 이름이 중복되었습니다"),
    PROJECT_NOT_FOUND("존재하지 않는 프로젝트입니다"),
    AUTHORIZED("해당 자원에 접근권한이 없습니다"),
    AUTHENTICATED("인증되지 않은 사용자입니다"),
    PROJECT_EXIT("이미 종료된 프로젝트입니다"),
    ALREADY_PROJECT_MEMBER("이미 해당 프로젝트에 소속된 멤버입니다"),
    MEMBER_NOT_FOUND("해당되는 멤버를 찾을 수 없습니다"),
    TASK_NOT_FOUND("존재하지 않는 태스크입니다"),
    TAG_NOT_FOUND("존재하지 않는 태그입니다"),
    MILESTONE_NOT_FOUND("존재하지 않는 마일스톤입니다"),
    COMMENT_NOT_FOUND("존재하지_않는_댓글입니다"),
    START_DATE_LATER_THAN_END_DATE("시작일은 종료일보다 빨라야 합니다"),
    PAGE_PARAM_INVALID("0보다 작은 숫자의 페이지는 입력될 수 없습니다"),
    TAG_NAME_DUPLICATED("해당 태그의 이름은 사용중입니다");

    private String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
