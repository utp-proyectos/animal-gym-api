package pe.edu.utp.animal_gym_api.domain.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByPerson_Dni(String dni);

	Optional<User> findByPersonId(Long id);

	default boolean isEmpty() {
		return count() == 0;
	}
}
