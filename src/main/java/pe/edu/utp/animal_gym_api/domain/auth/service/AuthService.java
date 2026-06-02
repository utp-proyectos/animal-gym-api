package pe.edu.utp.animal_gym_api.domain.auth.service;

import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.user.User;

public interface AuthService {
	AuthResponse login(LoginRequest request);
	User getCurrentUser(String dni);
}
