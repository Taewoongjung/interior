package com.interior.application.unittest.readmodel.business.handlers;

import com.interior.adapter.inbound.business.enumtypes.ProgressQueryType;
import com.interior.application.readmodel.business.handlers.GetBusinessAlimtalkHistoryQueryHandler;
import com.interior.application.readmodel.business.queries.GetBusinessAlimtalkHistoryQuery;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import com.interior.helper.spy.KakaoMsgResultRepositorySpy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("GetBusinessAlimtalkHistoryQueryHandler 는 ")
class GetBusinessAlimtalkHistoryQueryHandlerTest {

    private final KakaoMsgResultRepository kakaoMsgResultRepository = new KakaoMsgResultRepositorySpy();

    private final GetBusinessAlimtalkHistoryQueryHandler sut = new GetBusinessAlimtalkHistoryQueryHandler(
            kakaoMsgResultRepository);


    @Test
    @DisplayName("알림톡 히스토리를 조회할 수 있다.")
    void test1() {

        // given
        Long businessId = 1L;
        ProgressQueryType progressType = ProgressQueryType.ALL;

        GetBusinessAlimtalkHistoryQuery event = new GetBusinessAlimtalkHistoryQuery(businessId,
                progressType);

        // when
        List<KakaoMsgResult> actual = sut.handle(event);

        // then
        assertThat(actual.size()).isEqualTo(3);

        assertThat(actual.get(0).getId()).isEqualTo(1L);
        assertThat(actual.get(0).getMsgId()).isEqualTo("sadaw214");

        assertThat(actual.get(1).getId()).isEqualTo(2L);
        assertThat(actual.get(1).getMsgId()).isEqualTo("sadaw223");

        assertThat(actual.get(2).getId()).isEqualTo(4L);
        assertThat(actual.get(2).getMsgId()).isEqualTo("sadaw323");
    }

    @Test
    @DisplayName("특정 사업 진행 타입에 속한 알림톡 히스토리를 조회할 수 있다.")
    void test2() {

        // given
        Long businessId = 1L;
        ProgressQueryType progressType = ProgressQueryType.QUOTATION_REQUESTED;

        GetBusinessAlimtalkHistoryQuery event = new GetBusinessAlimtalkHistoryQuery(businessId,
                progressType);

        // when
        List<KakaoMsgResult> actual = sut.handle(event);

        // then
        assertThat(actual.size()).isEqualTo(2);

        assertThat(actual.get(0).getId()).isEqualTo(1L);
        assertThat(actual.get(0).getMsgId()).isEqualTo("sadaw214");

        assertThat(actual.get(1).getId()).isEqualTo(2L);
        assertThat(actual.get(1).getMsgId()).isEqualTo("sadaw223");
    }

    @Test
    @DisplayName("사업에 매핑 된 정보가 없으면 빈 값을 return 한다.")
    void test3() {

        // given
        Long businessId = 4L;
        ProgressQueryType progressType = ProgressQueryType.QUOTATION_REQUESTED;

        GetBusinessAlimtalkHistoryQuery event = new GetBusinessAlimtalkHistoryQuery(businessId,
                progressType);

        // when
        List<KakaoMsgResult> actual = sut.handle(event);

        // then
        assertThat(actual.size()).isEqualTo(0);
    }
}