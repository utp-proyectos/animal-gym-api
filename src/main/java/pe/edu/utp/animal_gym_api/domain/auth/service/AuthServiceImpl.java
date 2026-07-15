package pe.edu.utp.animal_gym_api.domain.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;
import pe.edu.utp.animal_gym_api.security.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UserRepository userRepository;

	@Override
	public AuthResponse login(LoginRequest request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getDni(), request.getPassword()));

		User user = userRepository.findByPerson_Dni(auth.getName())
				.orElseThrow(() -> new EntityNotFoundException("Credenciales invalidas"));

		String token = jwtService.generateToken(auth);

		AuthResponse response = new AuthResponse(
				token,
				user.getId(),
				user.getPerson().getId(),
				user.getPerson().getDni(),
				user.getPerson().getFirstName(),
				user.getPerson().getLastName(),
				user.getPerson().getEmail(),
				user.getPerson().getAvatar(),
				user.getPerson().getRole());

		return response;
	}

	@Override
	public User getCurrentUser(String dni) {
		return userRepository.findByPerson_Dni(dni)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
	}

}
