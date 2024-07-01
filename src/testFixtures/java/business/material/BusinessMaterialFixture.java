package business.material;

import com.interior.domain.business.expense.BusinessMaterialExpense;
import com.interior.domain.business.material.BusinessMaterial;
import com.interior.domain.util.BoolType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusinessMaterialFixture {

    public static List<BusinessMaterial> getBusinessMaterial() {
        List<BusinessMaterial> list = new ArrayList<>();

        list.add(BM_1);
        list.add(BM_2);

        return list;
    }

    public static BusinessMaterial BM_1 = BusinessMaterial.of(
            32L, 1L, "타일", "화장실 공사",
            "OSB/PB", BigDecimal.valueOf(10), "ea", "메모메모~", BoolType.T,
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            BusinessMaterialExpense.of(
                    10L,
                    1L,
                    "1000",
                    "2000",
                    LocalDateTime.of(2024, 7, 1, 2, 3),
                    LocalDateTime.of(2024, 7, 1, 2, 3)
            )
    );

    public static BusinessMaterial BM_2 = BusinessMaterial.of(
            36L, 1L, "벽돌", "외벽 공사",
            "세라믹 벽돌", BigDecimal.valueOf(21), "ea", "메모해요", BoolType.F,
            LocalDateTime.of(2024, 7, 1, 2, 3),
            LocalDateTime.of(2024, 7, 1, 2, 3),
            BusinessMaterialExpense.of(
                    12L,
                    1L,
                    "1200",
                    "5000",
                    LocalDateTime.of(2024, 7, 1, 2, 3),
                    LocalDateTime.of(2024, 7, 1, 2, 3)
            )
    );
}
