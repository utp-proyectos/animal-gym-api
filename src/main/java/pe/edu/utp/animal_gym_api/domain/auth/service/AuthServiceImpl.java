package pe.edu.utp.animal_gym_api.domain.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;
import pe.edu.utp.animal_gym_api.security.jwt.JwtService;

@Service
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
				.orElseThrow(() -> new EntityNotFoundException("Credenciales invalidas"));

		System.out.println("------------------------------ ");

		System.out.println("Usuario autenticado: " + auth.getName());
		System.out.println("Usuario autenticado: " + user.getPerson().getFirstName());
		System.out.println("------------------------------ ");

		String token = jwtService.generateToken(auth);
		System.out.println("Token generado: " + token);
		System.out.println("------------------------------ ");

		AuthResponse r = new AuthResponse(
				token,
				user.getId(),
				user.getPerson().getDni(),
				user.getPerson().getFirstName(),
				user.getPerson().getLastName(),
				user.getPerson().getEmail(),
				"",
				user.getRole());
		System.out.println("------------------------------ ");
		System.out.println("respuesta: " + r);
		System.out.println("respuesta: " + r.getDni());
		System.out.println("------------------------------ ");

		return r;
	}

	@Override
	public User getCurrentUser(String dni) {
		return userRepository.findByPerson_Dni(dni)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
	}

}
