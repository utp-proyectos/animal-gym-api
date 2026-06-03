package pe.edu.utp.animal_gym_api.domain.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserPasswordDTO;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional
	public void changePassword(Long id, UserPasswordDTO dto) {

		User user = userRepository.findByPersonId(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));

		user.setPassword(dto.getPassword());
	}
}
