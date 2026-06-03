package pe.edu.utp.animal_gym_api.domain.user.service;

import pe.edu.utp.animal_gym_api.domain.user.UserPasswordDTO;

public interface UserService {
	void changePassword(Long id, UserPasswordDTO dto);

}
