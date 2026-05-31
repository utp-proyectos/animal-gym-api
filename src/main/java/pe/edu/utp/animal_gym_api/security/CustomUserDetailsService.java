package pe.edu.utp.animal_gym_api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.edu.utp.animal_gym_api.domain.user.User;

@Service
public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	// private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
		User usuario = usuarioRepository.findByPersona_Dni(dni)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return org.springframework.security.core.userdetails.User.builder()
				.username(dni)
				.password(usuario.getPassword())
				.authorities(usuario.getRole().getName())
				.build();
	}
}
