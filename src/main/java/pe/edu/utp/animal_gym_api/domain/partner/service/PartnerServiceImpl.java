package pe.edu.utp.animal_gym_api.domain.partner.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.bill.Bill;
import pe.edu.utp.animal_gym_api.domain.bill.BillRepository;
import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.MembershipRepository;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerDetailDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRequestDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.dto.PartnerRoutinesResponseDTO;
import pe.edu.utp.animal_gym_api.domain.partner.mapper.PartnerMapper;
import pe.edu.utp.animal_gym_api.domain.routine.RoutineMapper;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;
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
	private BillRepository billRepository;

	@Autowired
	private PartnerMapper mapper;

	@Autowired
	private RoutineMapper routineMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private StorageService storageService;

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
		Membership membership = membershipRepository.findById(requestDTO.getMembershipId())
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + requestDTO.getMembershipId()));

		Long enrolled = membershipRepository.countActiveByMembershipId(membership.getId());
		if (enrolled >= membership.getCapacityLimit()) {
			throw new IllegalStateException(
					"La membresía '" + membership.getName() + "' ha alcanzado su límite de cupos ("
							+ membership.getCapacityLimit() + ")");
		}

		Partner partner = mapper.toEntity(requestDTO);

		partner.setMembership(membership);
		partner.setExpirationDate(LocalDate.now().plusDays(membership.getDuration()));
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
		Double igv = membership.getPrice() * 0.18;
		Double subTotal = membership.getPrice() - igv;

		Bill bill = new Bill();
		bill.setIssueDate(LocalDate.now());
		bill.setTime(LocalTime.now());
		bill.setSubTotal(subTotal);
		bill.setIgv(igv);
		bill.setTotalPrice(membership.getPrice());
		bill.setStatus(true);
		bill.setPartner(saved);
		bill.setEmployee(null);

		// Snapshot del socio
		bill.setPartnerDni(saved.getDni());
		bill.setPartnerFirstName(saved.getFirstName());
		bill.setPartnerLastName(saved.getLastName());

		bill.setEmployeeDni(null);
		bill.setEmployeeFirstName("Sistema");
		bill.setEmployeeLastName("(auto-registro)");

		// Snapshot de la membresía
		bill.setMembershipName(membership.getName());

		billRepository.save(bill);

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

			boolean membershipChanged = existing.getMembership() == null
					|| !existing.getMembership().getId().equals(membership.getId());

			if (membershipChanged) {
				Long enrolled = membershipRepository.countActiveByMembershipId(membership.getId());
				if (enrolled >= membership.getCapacityLimit()) {
					throw new IllegalStateException(
							"La membresía '" + membership.getName() + "' ha alcanzado su límite de cupos");
				}
				existing.setExpirationDate(LocalDate.now().plusDays(membership.getDuration()));
			}

			existing.setMembership(membership);
		}

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
