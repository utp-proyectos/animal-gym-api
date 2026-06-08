package pe.edu.utp.animal_gym_api.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializr implements CommandLineRunner {
	private final UserRepository userRepository;
	private final EmployeeRepository employeeRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void run(String... args) {

		if (userRepository.isEmpty()) {
			Employee admin = new Employee();
			admin.setDni("00000000");
			admin.setFirstName("Admin");
			admin.setLastName("root");
			admin.setPhoneNumber("000000000");
			admin.setGender("N/A");
			admin.setEmail("BtQ4w@example.com");
			admin.setBirthDate(LocalDate.now());
			admin.setHireDate(LocalDate.now());

			admin.setAvatar("");
			admin.setSalary(0.0);
			admin.setContractType("Permanent");
			admin.setSpecialty("N/A");

			employeeRepository.save(admin);

			User user = new User();
			user.setPassword(passwordEncoder.encode("root"));
			user.setRole(Role.ADMIN);
			user.setPerson(admin);

			userRepository.save(user);
		}
	}
}
