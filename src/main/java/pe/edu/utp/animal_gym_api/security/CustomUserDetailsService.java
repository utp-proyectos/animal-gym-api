package pe.edu.utp.animal_gym_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		User usuario = userRepository.findByPerson_Dni(dni)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return org.springframework.security.core.userdetails.User.builder()
				.username(dni)
				.password(usuario.getPassword())
				.roles(usuario.getRole().getName())
				.build();
	}
}
