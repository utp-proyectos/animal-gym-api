package pe.edu.utp.animal_gym_api.domain.auth;

import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;

public interface AuthService {
	AuthResponse login(LoginRequest request);
}
