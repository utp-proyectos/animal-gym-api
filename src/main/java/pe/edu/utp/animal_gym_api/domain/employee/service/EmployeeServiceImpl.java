package pe.edu.utp.animal_gym_api.domain.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeMapper;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;
import pe.edu.utp.animal_gym_api.domain.person.PersonValidator;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
@RequiredArgsConstructor

public class EmployeeServiceImpl implements EmployeeService {

	private final PasswordEncoder passwordEncoder;

	private final EmployeeRepository employeeRepository;

	private final UserRepository userRepository;

	private final EmployeeMapper employeeMapper;

	private final StorageService storageService;

	private final PersonValidator personValidator;

	@Override
	public List<EmployeeResponseDetailDTO> findAll() {
		return employeeRepository.findAll().stream().map(
				employeeMapper::toDetailDto).collect(Collectors.toList());
	}

	@Override
	public EmployeeResponseDetailDTO findById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

		return employeeMapper.toDetailDto(employee);
	}

	@Override
	@Transactional
	public EmployeeResponseDetailDTO save(EmployeeUser dto) throws IOException {

		personValidator.validateUniqueForCreate(dto.getDni(), dto.getEmail(), dto.getPhoneNumber());
		String avatar = "";

		if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
			avatar = storageService.upload(dto.getAvatar(), "employees");
		} else {
			avatar = "../../resource/img/default.png";
		}

		Employee employee = employeeMapper.toEntity(dto);
		employee.setAvatar(avatar);
		employeeRepository.save(employee);

		User user = new User();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setPerson(employee);
		userRepository.save(user);

		return employeeMapper.toDetailDto(employee);
	}

	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	@Transactional
	public EmployeeResponseDetailDTO update(Long id, EmployeeUser dto) throws IOException {
		personValidator.validateUniqueForUpdate(id, dto.getDni(), dto.getEmail(), dto.getPhoneNumber());

		Employee existing = employeeRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

		Employee employee = employeeMapper.toEntity(dto);
		employee.setId(id);

		if (dto.getAvatar() != null && !dto.getAvatar().isEmpty()) {
			String avatar = storageService.upload(dto.getAvatar(), "employees");
			employee.setAvatar(avatar);
		} else {
			employee.setAvatar(existing.getAvatar());
		}

		employeeRepository.save(employee);
		return employeeMapper.toDetailDto(employee);
	}

}
