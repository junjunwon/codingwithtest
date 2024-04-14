package com.codingwithtest.codewithtest;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepeatedTest {

    /**
     * BeforeAll, AfterAll은 반드시 static함수로 만들어져야한다.
     * why? Junit은 각각의 메서드마다 instance를 만들기 때문
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
        System.out.println(this); //to check whether class instance is change (yes)
        System.out.println(repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("반복 테스트 - 파라미터")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(strings = {"날씨가", "많이", "따뜻해", "지고 있네요."})
    @EmptySource // empty Test
    @NullSource // null test
    @NullAndEmptySource // empty and null test
    void parameterizedTest(String message) {
        System.out.println(this); //to check whether class instance is change (yes)
        System.out.println(message);
    }

    @DisplayName("반복 테스트 - 파라미터 converting")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @ValueSource(ints = {10,20,30})
    void parameterizedTest2(@ConvertWith(StudyConverter.class) Study study) {
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {

        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class, targetType, "Can only convert to a study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("반복 테스트 - 파라미터 converting")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '테스트코드 스터디'", "20, 'can we?'"})
    void parameterizedTest3(ArgumentsAccessor argumentsAccessor) {
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(0));
        System.out.println(study);
    }

    @DisplayName("반복 테스트 - 파라미터 converting")
    @ParameterizedTest(name = "{index} {displayName} message = {0}")
    @CsvSource({"10, '테스트코드 스터디'", "20, 'can we?'"})
    void parameterizedTest4(@AggregateWith(StudyAggregator.class) Study study) {
        System.out.println(study);
    }

    //static inner class or public class여야만 사용 가능하다.
    static class StudyAggregator implements ArgumentsAggregator {

        @Override
        public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws ArgumentsAggregationException {
            return new Study(accessor.getInteger(0), accessor.getString(0));
        }
    }
}
