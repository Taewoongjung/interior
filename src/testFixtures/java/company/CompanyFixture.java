package company;

import com.interior.domain.company.Company;
import com.interior.domain.util.BoolType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompanyFixture {

    public static Company COMPANY_1 = Company.of(
            1L,
            "TW",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_2 = Company.of(
            2L,
            "TW_second",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_3 = Company.of(
            3L,
            "TW_second",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_4 = Company.of(
            4L,
            "TW_second",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_5 = Company.of(
            5L,
            "TW_second",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_6 = Company.of(
            6L,
            "TW_second",
            "01000",
            10L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_24 = Company.of(
            24L,
            "TW_second",
            "01000",
            12L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_43 = Company.of(
            43L,
            "TW_second",
            "01000",
            32L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static Company COMPANY_71 = Company.of(
            71L,
            "TW_second",
            "01000",
            12L,
            "군자로 43",
            "809호",
            "0132104912908451209",
            "01012345678",
            BoolType.F,
            LocalDateTime.of(2024, 5, 19, 23, 30),
            LocalDateTime.of(2024, 5, 19, 23, 30)
    );

    public static List<Company> COMPANY_LIST() {

        List<Company> list = new ArrayList<>();
        list.add(COMPANY_1);
        list.add(COMPANY_2);

        return list;
    }

    public static List<Company> COMPANY_LIST_OVER_5() {

        List<Company> list = new ArrayList<>();
        list.add(COMPANY_1);
        list.add(COMPANY_2);
        list.add(COMPANY_3);
        list.add(COMPANY_4);
        list.add(COMPANY_5);
        list.add(COMPANY_6);
        list.add(COMPANY_24);
        list.add(COMPANY_43);
        list.add(COMPANY_71);

        return list;
    }
}
