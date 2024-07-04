package business.log;

import com.interior.domain.business.material.log.BusinessMaterialChangeFieldType;
import com.interior.domain.business.material.log.BusinessMaterialLog;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BusinessMaterialLogFixture {

    public static List<BusinessMaterialLog> getBusinessMaterialLogList() {
        List<BusinessMaterialLog> list = new ArrayList<>();

        list.add(BML_1);
        list.add(BML_2);

        return list;
    }

    public static BusinessMaterialLog BML_1 = BusinessMaterialLog.of(
            1L, 10L, 32L,
            BusinessMaterialChangeFieldType.CREATE_NEW_MATERIAL, "beforeData", "afterData",
            519L, "홍길동",
            LocalDateTime.of(2024, 7, 4, 23, 30)
    );

    public static BusinessMaterialLog BML_2 = BusinessMaterialLog.of(
            2L, 10L, 33L,
            BusinessMaterialChangeFieldType.DELETE_MATERIAL, "beforeData", "afterData",
            519L, "홍길동",
            LocalDateTime.of(2024, 7, 19, 12, 23)
    );
}
