package pe.edu.utp.animal_gym_api.domain.employee.service;

import java.util.List;

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

	@Override
	public List<EmployeeResponseDTO> findAll() {
		return employeeRepository.findAllCardEmployees();
	}

	@Override
	public EmployeeResponseDetailDTO findById(Long id) {
		Employee employee = employeeRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + id));

		User user = userRepository.findByPersonId(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

		return employeeMapper.toDetailDto(employee, user.getRole());
	}

	@Override
	@Transactional
	public EmployeeResponseDTO save(EmployeeUser dto) {
		Employee employee = employeeMapper.toEntity(dto);
		employeeRepository.save(employee);

		User user = new User();
		user.setPassword(passwordEncoder.encode(dto.getPassword()));
		user.setRole(dto.getRole());
		user.setPerson(employee);
		userRepository.save(user);

		return employeeMapper.toResponseDto(employee, user.getRole());
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

		User user = userRepository.findByPersonId(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
		user.setRole(dto.getRole());
		userRepository.save(user);

		return employeeMapper.toResponseDto(employee, user.getRole());

	}

}
