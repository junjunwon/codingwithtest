package com.codingwithtest.codewithtest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.provider.CsvSource;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SingleTestInstance {
    /**
     * Testinstsance가 1개이면 BeforeAll, AfterAll은 static함수일 필요가 없어진다.
     * why? Junit은 클래스당 하나의 instance를 만들기 때문
     */
    @BeforeAll
    static void beforeAll() {
        System.out.println("before All");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("after All");
    }

    @DisplayName("반복 테스트1")
    @org.junit.jupiter.api.RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeated(RepetitionInfo repetitionInfo) {
        System.out.println(this);
        System.out.println(repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("반복 테스트 - 파라미터 converting")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '테스트코드 스터디'", "20, 'can we?'"})
    void parameterizedTest3(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(0));
        System.out.println(this);
        System.out.println(study);
    }
}
