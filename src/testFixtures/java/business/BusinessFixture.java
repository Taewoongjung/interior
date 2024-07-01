package business;

import com.interior.domain.business.Business;
import com.interior.domain.util.BoolType;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusinessFixture {

    public static List<Business> getBusinessList() {

        List<Business> list = new ArrayList<>();

        list.add(B_1);
        list.add(B_2);
        list.add(B_3);

        return list;
    }

    public static Business B_1 = Business.of(
            1L, "사업 현장 1", 17L, 519L, BoolType.F,
            "01000", "서울 강서구 강서로 375", "101동 202호", "1150010400114530001010977",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), new ArrayList<>());

    public static Business B_2 = Business.of(
            2L, "사업 현장 2", 23L, 519L, BoolType.F,
            "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), new ArrayList<>());

    public static Business B_3 = Business.of(
            3L, "사업 현장 2", 56L, 519L, BoolType.T,
            "01000", "경기 성남시 분당구 판교대장로 7", "101동 1403호", "1171010900106580001000001",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), new ArrayList<>());
}
