package pe.edu.utp.animal_gym_api.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.auth.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public String login(@Valid @RequestBody LoginRequest loginRequest) {
		authService.login(loginRequest);

		return "Login successful";
	}
}
