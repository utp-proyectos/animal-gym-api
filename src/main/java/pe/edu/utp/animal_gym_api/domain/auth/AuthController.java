package pe.edu.utp.animal_gym_api.domain.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pe.edu.utp.animal_gym_api.common.response.ApiResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.AuthResponse;
import pe.edu.utp.animal_gym_api.domain.auth.dto.LoginRequest;
import pe.edu.utp.animal_gym_api.domain.auth.service.AuthService;
import pe.edu.utp.animal_gym_api.domain.user.User;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
		return ResponseEntity.ok(ApiResponse.ok(authService.login(loginRequest)));
	}

	@GetMapping("/me")
	public ResponseEntity<ApiResponse<User>> me(@AuthenticationPrincipal UserDetails userDetails) {
		return ResponseEntity.ok(ApiResponse.ok(authService.getCurrentUser(userDetails.getUsername())));
	}
}
