package com.interior.application.readmodel.business.handlers;

import com.interior.abstraction.domain.IQueryHandler;
import com.interior.adapter.inbound.business.enumtypes.ProgressQueryType;
import com.interior.application.readmodel.business.queries.GetBusinessAlimtalkHistoryQuery;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.alimtalk.kakaomsgresult.repository.KakaoMsgResultRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetBusinessAlimtalkHistoryQueryHandler implements
        IQueryHandler<GetBusinessAlimtalkHistoryQuery, List<KakaoMsgResult>> {

    private final KakaoMsgResultRepository kakaoMsgResultRepository;

    @Override
    public boolean isQueryHandler() {
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KakaoMsgResult> handle(final GetBusinessAlimtalkHistoryQuery query) {
        log.info("process GetBusinessAlimtalkHistoryQuery {}", query);

        List<KakaoMsgResult> historyResult = kakaoMsgResultRepository.getAlimtalkHistory(
                query.businessId());

        if (query.progressType() != null) {
            if (query.progressType().equals(ProgressQueryType.QUOTATION_REQUESTED)) {
                return historyResult.stream()
                        .filter(f -> ProgressQueryType.QUOTATION_REQUESTED.getTemplateName()
                                .equals(f.getTemplateName()))
                        .collect(Collectors.toList());
            }
        }

        return historyResult;
    }
}
