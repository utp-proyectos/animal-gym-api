package pe.edu.utp.animal_gym_api.domain.partner.service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.mapper.PartnerMapper;
import pe.edu.utp.animal_gym_api.domain.partner.service.dto.PersonProfileRequest;
import pe.edu.utp.animal_gym_api.domain.person.PersonValidator;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineMapper;
import pe.edu.utp.animal_gym_api.domain.person.PersonValidator;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PersonValidator personValidator;

	@Autowired
	private PartnerMapper mapper;

	@Autowired
	private RoutineMapper routineMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StorageService storageService;

	PartnerServiceImpl(PersonValidator personValidator) {
		this.personValidator = personValidator;
	}

	@Override
	public List<PartnerResponseDTO> findAll() {
		return partnerRepository.findAll().stream()
				.map(mapper::toResponseDTO)
				.toList();
	}

	@Override
	public PartnerResponseDTO findById(Long id) {
		Partner partner = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));
		return mapper.toResponseDTO(partner);
	}

	@Override
	@Transactional(readOnly = true)
	public PartnerDetailDTO findDetailById(Long id) {
		Partner partner = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));
		return mapper.toDetailDTO(partner);
	}

	@Override
	@Transactional
	public PartnerResponseDTO create(PartnerRequestDTO requestDTO) {
		personValidator.validateUniqueForCreate(
				requestDTO.getDni(), requestDTO.getEmail(), requestDTO.getPhoneNumber());

		Partner partner = mapper.toEntity(requestDTO);

		partner.setMembership(null);
		partner.setExpirationDate(null);
		partner.setStatus(true);
		partner.setPoints(0);
		partner.setRole(Role.SOCIO);

		if (partner.getAvatar() == null || partner.getAvatar().isBlank()) {
			partner.setAvatar("");
		}

		Partner saved = partnerRepository.save(partner);

		User user = new User();
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		user.setPerson(saved);
		userRepository.save(user);

		return mapper.toResponseDTO(saved);
	}

	@Override
	@Transactional
	public PartnerResponseDTO update(Long id, PartnerRequestDTO requestDTO) {
		Partner existing = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));
		personValidator.validateUniqueForUpdate(
				id, requestDTO.getDni(), requestDTO.getEmail(), requestDTO.getPhoneNumber());

		mapper.updateEntityFromDTO(requestDTO, existing);

		if (existing.getAvatar() == null) {
			existing.setAvatar("");
		}

		Partner saved = partnerRepository.save(existing);

		userRepository.findByPersonId(id).ifPresent(user -> {
			if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank()) {
				user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
				userRepository.save(user);
			}
		});

		return mapper.toResponseDTO(saved);
	}

	@Override
	@Transactional
	public PartnerResponseDTO updateProfile(
			Long id,
			PersonProfileRequest dto) {

		Partner partner = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found"));

		personValidator.validateUniqueForUpdate(
				id,
				partner.getDni(),
				dto.getEmail(),
				dto.getPhoneNumber());

		partner.setFirstName(dto.getFirstName());
		partner.setLastName(dto.getLastName());
		partner.setEmail(dto.getEmail());
		partner.setPhoneNumber(dto.getPhoneNumber());
		partner.setGender(dto.getGender());
		partner.setBirthDate(dto.getBirthDate());

		partnerRepository.save(partner);

		return mapper.toResponseDTO(partner);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!partnerRepository.existsById(id)) {
			throw new EntityNotFoundException("Partner not found with ID: " + id);
		}
		partnerRepository.deleteById(id);
	}

	@Override
	public List<PartnerResponseDTO> findByStatus(Boolean status) {
		return partnerRepository.findByStatus(status).stream()
				.map(mapper::toResponseDTO)
				.toList();
	}

	@Override
	public List<PartnerResponseDTO> findByExpirationDateBetween(LocalDate start, LocalDate end) {
		return partnerRepository.findByExpirationDateBetween(start, end).stream()
				.map(mapper::toResponseDTO)
				.toList();
	}

	@Override
	public List<PartnerResponseDTO> findByMembershipId(Long membershipId) {
		return partnerRepository.findByMembershipId(membershipId).stream()
				.map(mapper::toResponseDTO)
				.toList();
	}

	@Override
	public List<PartnerResponseDTO> findByName(String name) {
		return partnerRepository.findByNameContainingIgnoreCase(name).stream()
				.map(mapper::toResponseDTO)
				.toList();
	}

	@Override
	@Transactional(readOnly = true)
	public PartnerRoutinesResponseDTO findRoutinesByPartnerId(Long id) {
		Partner partner = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));

		return routineMapper.toRoutinesResponseDTO(partner);
	}

	@Override
	@Transactional(readOnly = true)
	public List<PartnerRoutinesResponseDTO> findAllWithRoutines() {
		return partnerRepository.findAll().stream()
				.map(routineMapper::toRoutinesResponseDTO)
				.toList();
	}

	@Override
	@Transactional
	public String updateAvatar(Long id, MultipartFile file) throws IOException {
		Partner partner = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));
		String url = storageService.upload(file, "partners");
		partner.setAvatar(url);
		partnerRepository.save(partner);
		return url;
	}
}
