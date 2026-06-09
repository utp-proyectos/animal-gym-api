package pe.edu.utp.animal_gym_api.domain.employee.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeMapper;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDetailDTO;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeUser;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmployeeMapper employeeMapper;

	@Autowired
	private StorageService storageService;

	@Override
	public List<EmployeeResponseDTO> findAll() {
		return employeeRepository.findAll().stream().map(
				employeeMapper::toResponseDto).collect(Collectors.toList());
	}

	@Override
	public EmployeeResponseDetailDTO findById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

		return employeeMapper.toDetailDto(employee);
	}

	@Override
	@Transactional
	public EmployeeResponseDTO save(EmployeeUser dto) throws IOException {
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

		return employeeMapper.toResponseDto(employee);
	}

	@Override
	public void delete(Long id) {
		employeeRepository.deleteById(id);
	}

	@Override
	@Transactional

	public EmployeeResponseDTO update(Long id, EmployeeUser dto) {
		Employee employee = employeeMapper.toEntity(dto);
		employee.setId(id);
		employeeRepository.save(employee);
		return employeeMapper.toResponseDto(employee);

	}

}
