package pe.edu.utp.animal_gym_api.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		User usuario = userRepository.findByPerson_Dni(dni)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return org.springframework.security.core.userdetails.User.builder()
				.username(dni)
				.password(usuario.getPassword())
				.roles(usuario.getPerson().getRole().toString())
				.build();
	}
}
