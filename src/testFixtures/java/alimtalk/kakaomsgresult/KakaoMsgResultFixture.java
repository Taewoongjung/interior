package alimtalk.kakaomsgresult;

import com.interior.domain.alimtalk.AlimTalkMessageType;
import com.interior.domain.alimtalk.kakaomsgresult.KakaoMsgResult;
import com.interior.domain.business.thirdpartymessage.BusinessThirdPartyMessage;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class KakaoMsgResultFixture {

    public static List<BusinessThirdPartyMessage> getBusinessThirdPartyMessage() {
        List<BusinessThirdPartyMessage> list = new ArrayList<>();

        list.add(BTPM_1);
        list.add(BTPM_2);
        list.add(BTPM_3);

        return list;
    }

    private static BusinessThirdPartyMessage BTPM_1 = BusinessThirdPartyMessage.of(
            1L,
            519L,
            1L
    );

    private static BusinessThirdPartyMessage BTPM_2 = BusinessThirdPartyMessage.of(
            1L,
            520L,
            2L
    );

    private static BusinessThirdPartyMessage BTPM_3 = BusinessThirdPartyMessage.of(
            1L,
            521L,
            4L
    );

    public static List<KakaoMsgResult> getKakaoMsgResultList() {
        List<KakaoMsgResult> list = new ArrayList<>();

        list.add(KMR_1);
        list.add(KMR_2);
        list.add(KMR_3);

        return list;
    }

    private static KakaoMsgResult KMR_1 = KakaoMsgResult.of(
            1L, "견적서 초안 도착", "TT_6052",
            "msgSubject", "msg", AlimTalkMessageType.KKO,
            "01012345678", "sadaw214", BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    private static KakaoMsgResult KMR_2 = KakaoMsgResult.of(
            2L, "견적서 초안 도착", "TT_6052",
            "msgSubject", "msg", AlimTalkMessageType.KKO,
            "01087654321", "sadaw223", BoolType.F,
            LocalDateTime.of(2024, 5, 20, 21, 12)
    );

    private static KakaoMsgResult KMR_3 = KakaoMsgResult.of(
            4L, "회원가입 완료", "TT_6051",
            "msgSubject", "msg", AlimTalkMessageType.KKO,
            "01012582396", "sadaw323", BoolType.F,
            LocalDateTime.of(2024, 5, 30, 11, 42)
    );
}
