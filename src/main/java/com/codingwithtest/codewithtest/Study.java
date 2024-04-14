package com.codingwithtest.codewithtest;

import lombok.ToString;

@ToString
public class Study {

    private StudyStatus studyStatus = StudyStatus.DRAFT;
    private int limit;
    private String msg;

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 작을 수 없습니다.");
        }
        this.limit = limit;
    }

    public Study(int limit, String msg) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit은 0보다 작을 수 없습니다.");
        }
        this.limit = limit;
        this.msg = msg;
    }

    public Study() {
    }

    public StudyStatus getStudyStatus() {
        return studyStatus;
    }

    public int getLimit() {
        return limit;
    }
}
