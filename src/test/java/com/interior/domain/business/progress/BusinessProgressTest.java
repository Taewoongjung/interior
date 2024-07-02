package com.interior.domain.business.progress;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_IS_DELETED;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_PROGRESS_TYPE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessProgress 는 ")
class BusinessProgressTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessProgress.of(
                1L,
                10L,
                ProgressType.CONSTRUCTION_CONTINUE,
                BoolType.F,
                LocalDateTime.now()
        ));
    }

    @Test
    @DisplayName("연관 된 사업 id가 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessProgress.of(
                    1L,
                    null,
                    ProgressType.CONSTRUCTION_CONTINUE,
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_ID.getMessage());
    }

    @Test
    @DisplayName("진행 상황 객체가 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessProgress.of(
                    1L,
                    10L,
                    null,
                    BoolType.F,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_PROGRESS_TYPE.getMessage());
    }

    @Test
    @DisplayName("삭제 유무 객체가 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessProgress.of(
                    1L,
                    10L,
                    ProgressType.CONSTRUCTION_CONTINUE,
                    null,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_IS_DELETED.getMessage());
    }
}