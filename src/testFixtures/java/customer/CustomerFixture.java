package customer;

import com.interior.domain.customer.Customer;
import java.time.LocalDateTime;

public class CustomerFixture {
	
	public static Customer CUSTOMER = Customer.of(1L, "홍길동", "abc@gmail.com", "111", "01012345678",
		LocalDateTime.of(2024, 3, 22, 12, 10),
		LocalDateTime.of(2024, 3, 22, 15, 10));
	
}
