package com.kimtaehoonki.task;

/**
 * 프로젝트 상태 코드.
 * ACTIVATION: 활성, DORMANT: 휴면, EXIT: 종료
 */
public enum ProjectStatus {
    ACTIVATION,
    DORMANT,
    EXIT;

    public boolean isExit() {
        return this == EXIT;
    }
}
