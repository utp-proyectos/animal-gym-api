package pe.edu.utp.animal_gym_api.domain.partner.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.MembershipRepository;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.mapper.PartnerMapper;
import pe.edu.utp.animal_gym_api.domain.user.User;
import pe.edu.utp.animal_gym_api.domain.user.UserRepository;

@Service
public class PartnerServiceImpl implements PartnerService {

	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private MembershipRepository membershipRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PartnerMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

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
		Partner partner = mapper.toEntity(requestDTO);

		Membership membership = membershipRepository.findById(requestDTO.getMembershipId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + requestDTO.getMembershipId()));

		partner.setMembership(membership);
		partner.setExpirationDate(LocalDate.now().plusDays(membership.getDuration()));
		partner.setStatus(true);
		partner.setPoints(0);
		Partner saved = partnerRepository.save(partner);

		User user = new User();
		user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
		user.setPerson(saved); // ya no asigna role al user
		userRepository.save(user);

		return mapper.toResponseDTO(saved);
	}

	@Override
	@Transactional
	public PartnerResponseDTO update(Long id, PartnerRequestDTO requestDTO) {
		Partner existing = partnerRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + id));

		mapper.updateEntityFromDTO(requestDTO, existing);

		if (requestDTO.getMembershipId() != null) {
			Membership membership = membershipRepository.findById(requestDTO.getMembershipId())
					.orElseThrow(() -> new EntityNotFoundException(
							"Membership not found with ID: " + requestDTO.getMembershipId()));
			existing.setMembership(membership);
			existing.setExpirationDate(LocalDate.now().plusDays(membership.getDuration()));
		}

		Partner saved = partnerRepository.save(existing);

		userRepository.findByPersonId(id).ifPresent(user -> {
			if (requestDTO.getPassword() != null && !requestDTO.getPassword().isBlank())
				user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
			userRepository.save(user);
		});

		return mapper.toResponseDTO(saved);
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
}
