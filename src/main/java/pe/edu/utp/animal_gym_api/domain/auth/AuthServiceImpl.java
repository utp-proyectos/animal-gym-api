package pe.edu.utp.animal_gym_api.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import pe.edu.utp.animal_gym_api.common.exception.ResourceNotFoundException;
import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;
import pe.edu.utp.animal_gym_api.security.jwt.JwtService;

public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private UserRepository userRepository;

	@Override
	public AuthResponse login(LoginRequest request) {
		Authentication auth = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getDni(), request.getPassword()));

		User user = userRepository.findByPerson_Dni(auth.getName())
				.orElseThrow(() -> new ResourceNotFoundException("Credenciales invalidas"));

		String token = jwtService.generateToken(auth);

		return new AuthResponse(
				token,
				auth.getName(),
				user.getPerson().getDni(),
				user.getPerson().getFirstName(),
				user.getPerson().getLastName(),
				user.getPerson().getEmail(),
				"",
				user.getRole());
	}

}
