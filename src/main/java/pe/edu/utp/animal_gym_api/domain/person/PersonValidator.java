package pe.edu.utp.animal_gym_api.domain.person;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.common.exception.DuplicateResourceException;

@Component
@RequiredArgsConstructor
public class PersonValidator {

	private final PersonRepository personRepository;

	public void validateUniqueForCreate(String dni, String email, String phoneNumber) {
		if (personRepository.existsByDni(dni)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese DNI");
		}
		if (personRepository.existsByEmail(email)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese correo");
		}
		if (personRepository.existsByPhoneNumber(phoneNumber)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese teléfono");
		}
	}

	public void validateUniqueForUpdate(Long id, String dni, String email, String phoneNumber) {
		if (personRepository.existsByDniAndIdNot(dni, id)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese DNI");
		}
		if (personRepository.existsByEmailAndIdNot(email, id)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese correo");
		}
		if (personRepository.existsByPhoneNumberAndIdNot(phoneNumber, id)) {
			throw new DuplicateResourceException("Ya existe una persona registrada con ese teléfono");
		}
	}

}
