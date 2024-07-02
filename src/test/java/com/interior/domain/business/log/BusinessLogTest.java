package com.interior.domain.business.log;

import static com.interior.adapter.common.exception.ErrorType.EMPTY_BUSINESS_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_CHANGE_FIELD;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_ID;
import static com.interior.adapter.common.exception.ErrorType.EMPTY_UPDATER_NAME;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.interior.adapter.common.exception.InvalidInputException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("BusinessLog 는 ")
class BusinessLogTest {

    @Test
    @DisplayName("생성된다.")
    void test1() {
        assertDoesNotThrow(() -> BusinessLog.of(
                10L,
                BusinessChangeFieldType.CREATE_NEW_BUSINESS,
                "변경 전 데이터",
                "변경 후 데이터",
                519L,
                "홍길동",
                LocalDateTime.now()
        ));
    }

    @Test
    @DisplayName("연관 된 사업 id 는 필수값입니다.")
    void test2() {
        assertThatThrownBy(() -> {
            BusinessLog.of(
                    null,
                    BusinessChangeFieldType.CREATE_NEW_BUSINESS,
                    "변경 전 데이터",
                    "변경 후 데이터",
                    519L,
                    "홍길동",
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_BUSINESS_ID.getMessage());
    }

    @Test
    @DisplayName("변경 된 필드는 필수값입니다.")
    void test3() {
        assertThatThrownBy(() -> {
            BusinessLog.of(
                    10L,
                    null,
                    "변경 전 데이터",
                    "변경 후 데이터",
                    519L,
                    "홍길동",
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_CHANGE_FIELD.getMessage());
    }

    @Test
    @DisplayName("업데이트 한 사람의 id는 필수값입니다.")
    void test4() {
        assertThatThrownBy(() -> {
            BusinessLog.of(
                    10L,
                    BusinessChangeFieldType.CREATE_NEW_BUSINESS,
                    "변경 전 데이터",
                    "변경 후 데이터",
                    null,
                    "홍길동",
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_UPDATER_ID.getMessage());
    }

    @Test
    @DisplayName("업데이트 한 사람의 이름은 필수값입니다.")
    void test5() {
        assertThatThrownBy(() -> {
            BusinessLog.of(
                    10L,
                    BusinessChangeFieldType.CREATE_NEW_BUSINESS,
                    "변경 전 데이터",
                    "변경 후 데이터",
                    519L,
                    null,
                    LocalDateTime.now()
            );
        })
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(EMPTY_UPDATER_NAME.getMessage());
    }
}