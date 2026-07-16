package pe.edu.utp.animal_gym_api.domain.membership.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.bill.Bill;
import pe.edu.utp.animal_gym_api.domain.bill.BillRepository;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.MembershipRepository;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipAssignmentResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchasePreviewDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipPurchaseResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipSelfResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.exception.MembershipAssignmentException;
import pe.edu.utp.animal_gym_api.domain.membership.mapper.MembershipMapper;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;
import pe.edu.utp.animal_gym_api.domain.storage.StorageService;

@Service
public class MembershipServiceImpl implements MembershipService {
	@Autowired
	private MembershipRepository membershipRepository;
	@Autowired
	private MembershipMapper membershipMapper;
	@Autowired
	private StorageService storageService;
	@Autowired
	private PartnerRepository partnerRepository;
	@Autowired
	private BillRepository billRepository;
	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public List<MembershipResponseDTO> findAll() {
		Map<Long, Long> enrolledMap = buildEnrolledMap();
		return membershipRepository.findAll().stream()
				.map(m -> enrich(membershipMapper.toResponseDTO(m), m,
						enrolledMap.getOrDefault(m.getId(), 0L)))
				.toList();
	}

	@Override
	public MembershipResponseDTO findById(Long id) {
		Membership membership = membershipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Membership not found with ID: " + id));
		Long enrolled = membershipRepository.countActiveByMembershipId(id);
		return enrich(membershipMapper.toResponseDTO(membership), membership, enrolled);
	}

	@Override
	@Transactional
	public MembershipResponseDTO create(MembershipRequestDTO requestDTO) throws IOException {
		Membership membership = membershipMapper.toEntity(requestDTO);

		MultipartFile imageFile = requestDTO.getImage();

		if (imageFile != null && !imageFile.isEmpty()) {
			String imageUrl = storageService.upload(imageFile, "memberships");
			membership.setImage(imageUrl);
		}

		Membership saved = membershipRepository.save(membership);
		return enrich(membershipMapper.toResponseDTO(saved), saved, 0L);
	}

	@Override
	@Transactional
	public MembershipResponseDTO update(Long id, MembershipRequestDTO requestDTO) throws IOException {
		Membership existing = membershipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Membership not found with ID: " + id));

		membershipMapper.updateEntityFromDTO(requestDTO, existing);

		MultipartFile imageFile = requestDTO.getImage();
		if (imageFile != null && !imageFile.isEmpty()) {
			String imageUrl = storageService.upload(imageFile, "memberships");
			existing.setImage(imageUrl);
		}

		Membership saved = membershipRepository.save(existing);
		Long enrolled = membershipRepository.countActiveByMembershipId(id);
		return enrich(membershipMapper.toResponseDTO(saved), saved, enrolled);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if (!membershipRepository.existsById(id)) {
			throw new EntityNotFoundException("Membership not found with ID: " + id);
		}
		membershipRepository.deleteById(id);
	}

	@Override
	public List<MembershipResponseDTO> findByStatus(Boolean status) {
		Map<Long, Long> enrolledMap = buildEnrolledMap();
		return membershipRepository.findByStatus(status).stream()
				.map(m -> enrich(membershipMapper.toResponseDTO(m), m,
						enrolledMap.getOrDefault(m.getId(), 0L)))
				.toList();
	}

	@Override
	public List<MembershipResponseDTO> findByPriceRange(Double minPrice, Double maxPrice) {
		Map<Long, Long> enrolledMap = buildEnrolledMap();
		return membershipRepository.findByPriceBetween(minPrice, maxPrice).stream()
				.map(m -> enrich(membershipMapper.toResponseDTO(m), m,
						enrolledMap.getOrDefault(m.getId(), 0L)))
				.toList();
	}

	@Override
	public List<MembershipResponseDTO> findWithAvailableCapacity() {
		Map<Long, Long> enrolledMap = buildEnrolledMap();
		return membershipRepository.findWithAvailableCapacity().stream()
				.map(m -> enrich(membershipMapper.toResponseDTO(m), m,
						enrolledMap.getOrDefault(m.getId(), 0L)))
				.toList();
	}

	@Override
	@Transactional
	public MembershipAssignmentResponseDTO assignToPartner(
			Long membershipId, MembershipAssignmentRequestDTO requestDTO, String operatorDni) {
		Membership membership = membershipRepository.findByIdForUpdate(membershipId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + membershipId));

		Partner partner = partnerRepository.findByDniForUpdate(requestDTO.getPartnerDni())
				.orElseThrow(() -> new EntityNotFoundException(
						"Socio no encontrado con DNI: " + requestDTO.getPartnerDni()));
		MembershipPurchasePreviewDTO preview = calculatePurchase(partner, membership);

		if (!Boolean.TRUE.equals(preview.getAllowed())) {
			throw new MembershipAssignmentException(preview.getMessage());
		}

		String paymentMethod = requestDTO.getPaymentMethod();
		if (preview.getAmountToPay() > 0
				&& (paymentMethod == null || paymentMethod.isBlank())) {
			throw new MembershipAssignmentException("Selecciona un método de pago");
		}

		LocalDate previousExpirationDate = partner.getExpirationDate();
		double totalPrice = preview.getAmountToPay();
		double subTotal = totalPrice > 0 ? roundMoney(totalPrice / 1.18) : 0;
		double igv = totalPrice > 0 ? roundMoney(totalPrice - subTotal) : 0;

		partner.setMembership(membership);
		partner.setExpirationDate(preview.getNewExpirationDate());
		partner.setStatus(true);
		partnerRepository.save(partner);

		Employee employee = employeeRepository.findByDni(operatorDni).orElse(null);
		Bill savedBill = null;

		if (totalPrice > 0) {
			Bill bill = buildBill(
					partner, membership, employee, operatorDni, subTotal, igv, totalPrice, false);
			savedBill = billRepository.save(bill);
		}

		return new MembershipAssignmentResponseDTO(
				savedBill != null ? savedBill.getId() : null,
				preview.getOperationType(),
				partner.getId(),
				partner.getDni(),
				partner.getFirstName() + " " + partner.getLastName(),
				membership.getId(),
				membership.getName(),
				previousExpirationDate,
				preview.getNewExpirationDate(),
				roundMoney(membership.getPrice()),
				preview.getSelectedPrice(),
				preview.getRemainingDays(),
				preview.getRemainingCredit(),
				totalPrice,
				preview.getRefundAmount(),
				subTotal,
				igv,
				preview.getDiscountApplied(),
				totalPrice > 0 ? paymentMethod : "SALDO",
				totalPrice > 0 ? "APROBADO" : "SIN_COBRO");
	}

	@Override
	@Transactional(readOnly = true)
	public MembershipPurchasePreviewDTO previewAssignment(Long membershipId, String partnerDni) {
		Membership membership = membershipRepository.findById(membershipId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + membershipId));
		Partner partner = partnerRepository.findByDni(partnerDni)
				.orElseThrow(() -> new EntityNotFoundException(
						"Socio no encontrado con DNI: " + partnerDni));
		return calculatePurchase(partner, membership);
	}

	@Override
	@Transactional(readOnly = true)
	public MembershipSelfResponseDTO findCurrentForPartner(String partnerDni) {
		Partner partner = findPartnerByDni(partnerDni);
		Membership membership = partner.getMembership();
		LocalDate expirationDate = partner.getExpirationDate();
		LocalDate today = LocalDate.now();
		boolean active = membership != null
				&& partner.isStatus()
				&& expirationDate != null
				&& !expirationDate.isBefore(today);
		long daysRemaining = active
				? Math.max(0, ChronoUnit.DAYS.between(today, expirationDate))
				: 0;

		return new MembershipSelfResponseDTO(
				partner.getId(),
				membership != null ? membership.getId() : null,
				membership != null ? membership.getName() : null,
				expirationDate,
				active,
				daysRemaining);
	}

	@Override
	@Transactional(readOnly = true)
	public MembershipPurchasePreviewDTO previewPurchase(Long membershipId, String partnerDni) {
		Membership membership = membershipRepository.findById(membershipId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + membershipId));
		return calculatePurchase(findPartnerByDni(partnerDni), membership);
	}

	@Override
	@Transactional
	public MembershipPurchaseResponseDTO purchaseForCurrentPartner(
			Long membershipId, MembershipPurchaseRequestDTO requestDTO, String partnerDni) {
		Membership membership = membershipRepository.findByIdForUpdate(membershipId)
				.orElseThrow(() -> new EntityNotFoundException(
						"Membership not found with ID: " + membershipId));
		Partner partner = partnerRepository.findByDniForUpdate(partnerDni)
				.orElseThrow(() -> new EntityNotFoundException(
						"No se encontró un socio asociado al usuario autenticado"));
		MembershipPurchasePreviewDTO preview = calculatePurchase(partner, membership);

		if (!Boolean.TRUE.equals(preview.getAllowed())) {
			throw new MembershipAssignmentException(preview.getMessage());
		}

		String paymentMethod = requestDTO.getPaymentMethod();
		if (preview.getAmountToPay() > 0
				&& (paymentMethod == null || paymentMethod.isBlank())) {
			throw new MembershipAssignmentException("Selecciona un método de pago");
		}

		LocalDate previousExpirationDate = partner.getExpirationDate();
		partner.setMembership(membership);
		partner.setExpirationDate(preview.getNewExpirationDate());
		partner.setStatus(true);
		partnerRepository.save(partner);

		double totalPrice = preview.getAmountToPay();
		double subTotal = totalPrice > 0 ? roundMoney(totalPrice / 1.18) : 0;
		double igv = totalPrice > 0 ? roundMoney(totalPrice - subTotal) : 0;
		Bill savedBill = null;

		if (totalPrice > 0) {
			Bill bill = buildBill(
					partner,
					membership,
					null,
					partnerDni,
					subTotal,
					igv,
					totalPrice,
					true);
			savedBill = billRepository.save(bill);
		}

		return new MembershipPurchaseResponseDTO(
				savedBill != null ? savedBill.getId() : null,
				preview.getOperationType(),
				partner.getId(),
				partner.getFirstName() + " " + partner.getLastName(),
				membership.getId(),
				membership.getName(),
				previousExpirationDate,
				preview.getNewExpirationDate(),
				preview.getSelectedPrice(),
				preview.getRemainingCredit(),
				totalPrice,
				preview.getRefundAmount(),
				subTotal,
				igv,
				preview.getDiscountApplied(),
				totalPrice > 0 ? paymentMethod : "SALDO",
				totalPrice > 0 ? "APROBADO" : "SIN_COBRO");
	}

	private MembershipPurchasePreviewDTO calculatePurchase(Partner partner, Membership selectedMembership) {
		LocalDate today = LocalDate.now();
		Membership currentMembership = partner.getMembership();
		LocalDate currentExpirationDate = partner.getExpirationDate();
		boolean currentActive = currentMembership != null
				&& partner.isStatus()
				&& currentExpirationDate != null
				&& !currentExpirationDate.isBefore(today);
		boolean sameMembership = currentMembership != null
				&& currentMembership.getId().equals(selectedMembership.getId());
		long remainingDays = currentActive
				? Math.max(0, ChronoUnit.DAYS.between(today, currentExpirationDate))
				: 0;
		PriceDetails selectedPrice = resolvePrice(selectedMembership, today);

		MembershipPurchasePreviewDTO preview = new MembershipPurchasePreviewDTO();
		preview.setCurrentMembershipId(currentMembership != null ? currentMembership.getId() : null);
		preview.setCurrentMembershipName(currentMembership != null ? currentMembership.getName() : null);
		preview.setCurrentExpirationDate(currentExpirationDate);
		preview.setRemainingDays(remainingDays);
		preview.setRemainingCredit(0.0);
		preview.setSelectedMembershipId(selectedMembership.getId());
		preview.setSelectedMembershipName(selectedMembership.getName());
		preview.setSelectedDuration(selectedMembership.getDuration());
		preview.setOriginalPrice(roundMoney(selectedMembership.getPrice()));
		preview.setSelectedPrice(selectedPrice.price());
		preview.setDiscountApplied(selectedPrice.discountApplied());
		preview.setAmountToPay(selectedPrice.price());
		preview.setRefundAmount(0.0);
		preview.setNewExpirationDate(today.plusDays(selectedMembership.getDuration()));

		if (!selectedMembership.isStatus()) {
			return blockPurchase(preview, "INACTIVE", "La membresía seleccionada está inactiva");
		}

		boolean occupiesSelectedSlot = sameMembership && currentActive;
		Long enrolled = membershipRepository.countActiveByMembershipId(selectedMembership.getId());
		if (!occupiesSelectedSlot && enrolled >= selectedMembership.getCapacityLimit()) {
			return blockPurchase(
					preview,
					"FULL",
					"La membresía seleccionada no tiene cupos disponibles");
		}

		String operationType;
		if (sameMembership && currentActive) {
			operationType = "RENEWAL";
			preview.setNewExpirationDate(currentExpirationDate.plusDays(selectedMembership.getDuration()));
			preview.setMessage("La nueva vigencia se sumará al vencimiento actual");
		} else if (currentActive) {
			operationType = "CHANGE";
			PriceDetails currentPrice = resolvePrice(currentMembership, today);
			double remainingCredit = currentMembership.getDuration() > 0
					? roundMoney(currentPrice.price() / currentMembership.getDuration() * remainingDays)
					: 0;
			double difference = roundMoney(selectedPrice.price() - remainingCredit);

			preview.setRemainingCredit(remainingCredit);
			preview.setAmountToPay(Math.max(0, difference));
			preview.setRefundAmount(Math.max(0, roundMoney(-difference)));
			preview.setMessage("El saldo restante del plan actual se descontará del nuevo plan");
		} else if (sameMembership) {
			operationType = "RENEWAL";
			preview.setMessage("La nueva vigencia comenzará hoy");
		} else {
			operationType = "PURCHASE";
			preview.setMessage("La vigencia de la membresía comenzará hoy");
		}

		preview.setOperationType(operationType);
		preview.setAllowed(true);
		return preview;
	}

	private MembershipPurchasePreviewDTO blockPurchase(
			MembershipPurchasePreviewDTO preview, String operationType, String message) {
		preview.setOperationType(operationType);
		preview.setAllowed(false);
		preview.setMessage(message);
		return preview;
	}

	private Partner findPartnerByDni(String partnerDni) {
		return partnerRepository.findByDni(partnerDni)
				.orElseThrow(() -> new EntityNotFoundException(
						"No se encontró un socio asociado al usuario autenticado"));
	}

	private PriceDetails resolvePrice(Membership membership, LocalDate date) {
		boolean discountApplied = membership.getDiscountPrice() != null
				&& membership.getOfferStartDate() != null
				&& membership.getOfferEndDate() != null
				&& !date.isBefore(membership.getOfferStartDate())
				&& !date.isAfter(membership.getOfferEndDate());
		double price = discountApplied ? membership.getDiscountPrice() : membership.getPrice();
		return new PriceDetails(roundMoney(price), discountApplied);
	}

	private record PriceDetails(double price, boolean discountApplied) {
	}

	private Bill buildBill(
			Partner partner,
			Membership membership,
			Employee employee,
			String operatorDni,
			double subTotal,
			double igv,
			double totalPrice,
			boolean selfService) {
		Bill bill = new Bill();
		bill.setIssueDate(LocalDate.now());
		bill.setTime(java.time.LocalTime.now());
		bill.setSubTotal(subTotal);
		bill.setIgv(igv);
		bill.setTotalPrice(totalPrice);
		bill.setStatus(true);
		bill.setPartner(partner);
		bill.setEmployee(employee);
		bill.setPartnerDni(partner.getDni());
		bill.setPartnerFirstName(partner.getFirstName());
		bill.setPartnerLastName(partner.getLastName());
		bill.setMembershipName(membership.getName());

		if (employee != null) {
			bill.setEmployeeDni(employee.getDni());
			bill.setEmployeeFirstName(employee.getFirstName());
			bill.setEmployeeLastName(employee.getLastName());
		} else if (selfService) {
			bill.setEmployeeDni(null);
			bill.setEmployeeFirstName("Autoservicio");
			bill.setEmployeeLastName("");
		} else {
			bill.setEmployeeDni(operatorDni);
			bill.setEmployeeFirstName("Sistema");
			bill.setEmployeeLastName("");
		}

		return bill;
	}

	private double roundMoney(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	private Map<Long, Long> buildEnrolledMap() {
		Map<Long, Long> map = new HashMap<>();
		membershipRepository.countEnrolledPartnersByMembership()
				.forEach(row -> map.put((Long) row[0], (Long) row[1]));
		return map;
	}

	private MembershipResponseDTO enrich(MembershipResponseDTO dto, Membership m, Long enrolled) {
		LocalDate today = LocalDate.now();
		boolean hasOffer = m.getOfferStartDate() != null && m.getOfferEndDate() != null;

		dto.setActive(hasOffer
				&& !today.isBefore(m.getOfferStartDate())
				&& !today.isAfter(m.getOfferEndDate()));

		dto.setExpired(m.getOfferEndDate() != null && today.isAfter(m.getOfferEndDate()));

		dto.setRemainingDays(hasOffer && !today.isAfter(m.getOfferEndDate())
				? ChronoUnit.DAYS.between(today, m.getOfferEndDate())
				: null);

		dto.setEnrolledMembers(enrolled.intValue());
		return dto;
	}
}
