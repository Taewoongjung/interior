package business;

import com.interior.domain.business.Business;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.business.progress.BusinessProgress;
import com.interior.domain.business.progress.ProgressType;
import com.interior.domain.util.BoolType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusinessFixture {

    public static List<Business> getBusinessList() {

        List<Business> list = new ArrayList<>();

        list.add(B_1);
        list.add(B_2);
        list.add(B_2_1);
        list.add(B_2_2);
        list.add(B_3);
        list.add(B_4);

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
            new ArrayList<>(), getBusinessProgress());

    public static Business B_2_1 = Business.of(
            21L, "사업 현장 2", 23L, 519L, BoolType.F,
            "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), getBusinessProgress());

    public static Business B_2_2 = Business.of(
            22L, "사업 현장 2", 43L, 519L, BoolType.F,
            "01000", "부산 해운대구 APEC로 21", "401동 1202호", "2635010500115130000000002",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), getBusinessProgress());

    public static Business B_3 = Business.of(
            3L, "사업 현장 2", 56L, 519L, BoolType.T,
            "01000", "경기 성남시 분당구 판교대장로 7", "101동 1403호", "1171010900106580001000001",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            new ArrayList<>(), new ArrayList<>());

    public static Business B_4 = Business.of(
            4L, "사업 현장 4", 71L, 519L, BoolType.F,
            "01000", "경기 성남시 분당구 판교대장로 7", "101동 1403호", "1171010900106580001000001",
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            getBusinessMaterial(), getBusinessProgress());

    private static List<BusinessProgress> getBusinessProgress() {

        List<BusinessProgress> list = new ArrayList<>();

        list.add(BusinessProgress.of(3L, ProgressType.IN_PROGRESS));
        list.add(BusinessProgress.of(3L, ProgressType.QUOTATION_REQUESTED));

        return list;
    }

    private static List<BusinessMaterial> getBusinessMaterial() {

        List<BusinessMaterial> list = new ArrayList<>();

        list.add(BusinessMaterial.of(1L, 4L, "벽 타일",
                "외벽 공사", "타일", BigDecimal.valueOf(1), "ea",
                "memo", BoolType.F,
                LocalDateTime.of(2024, 7, 1, 6, 3),
                LocalDateTime.of(2024, 7, 1, 6, 3),
                null
        ));

        list.add(BusinessMaterial.of(2L, 4L, "벽돌",
                "외벽 공사", "돌", BigDecimal.valueOf(1),
                "ea", "memo", BoolType.F,
                LocalDateTime.of(2024, 7, 1, 6, 3),
                LocalDateTime.of(2024, 7, 1, 6, 3),
                null
        ));

        return list;
    }
}
