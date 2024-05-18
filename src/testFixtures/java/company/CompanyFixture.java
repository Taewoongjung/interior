package company;

import com.interior.domain.company.Company;
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
            LocalDateTime.of(2024,5,19, 23, 30),
            LocalDateTime.of(2024,5,19, 23, 30)
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
            LocalDateTime.of(2024,5,19, 23, 30),
            LocalDateTime.of(2024,5,19, 23, 30)
    );

    public static List<Company> COMPANY_LIST() {

        List<Company> list = new ArrayList<>();
        list.add(COMPANY_1);
        list.add(COMPANY_2);

        return list;
    }
}
