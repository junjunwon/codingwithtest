package com.codingwithtest.codewithtest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;


class StudyTest {

    static String testEnv = System.getenv("TEST_ENV");

    @Tag("fast")
    @EnabledOnOs({OS.MAC, OS.LINUX})
    @EnabledOnJre({JRE.JAVA_8, JRE.JAVA_9, JRE.JAVA_10, JRE.JAVA_17})
    @EnabledIfEnvironmentVariable(named = "TEST_ENV", matches = "local")
    @Test
    void test() {

        System.out.println(testEnv);
        assumeTrue("LOCAL".equalsIgnoreCase(testEnv)); //해당 조건을 만족할 때에만 다음 테스트로직을 수행한다. (현재 null)
        Study study = new Study();
        assertAll(
                () -> assertNotNull(study),
                () -> assertEquals(StudyStatus.DRAFT, study.getStudyStatus(), () -> "스터디는 멈출 수 없습니다.")
        );
    }

    @Tag("fast")
    @DisplayName("studying limit test")
        @Test
        void study_limit_test() {
        assumingThat("LOCAL".equalsIgnoreCase(testEnv), () -> {
            IllegalArgumentException exception =
                    assertThrows(IllegalArgumentException.class, () -> new Study(-10));
            assertEquals("limit은 0보다 작을 수 없습니다.", exception.getMessage(), ()-> "studying limit test");
        });

        assumingThat("null".equalsIgnoreCase(testEnv), () -> {
        });

    }

    @Tag("slow")
    @DisplayName("timeout test")
    @Test
    void study_timeout() {
        /**
         * 단점 : 이 테스트가 끝날때까지 기다린다.
         *
         */
        assertTimeout(Duration.ofMillis(100), () -> {
            new Study();
            Thread.sleep(300);
        });
    }

}