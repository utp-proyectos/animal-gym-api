package pe.edu.utp.animal_gym_api.domain.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
	boolean existsByDni(String dni);

	boolean existsByEmail(String email);

	boolean existsByPhoneNumber(String phoneNumber);

	boolean existsByDniAndIdNot(String dni, Long id);

	boolean existsByEmailAndIdNot(String email, Long id);

	boolean existsByPhoneNumberAndIdNot(String phoneNumber, Long id);
}
