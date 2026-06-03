package pe.edu.utp.animal_gym_api.domain.membership.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.membership.Membership;
import pe.edu.utp.animal_gym_api.domain.membership.MembershipRepository;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipRequestDTO;
import pe.edu.utp.animal_gym_api.domain.membership.dto.MembershipResponseDTO;
import pe.edu.utp.animal_gym_api.domain.membership.mapper.MembershipMapper;

@Service
public class MembershipServiceImpl implements MembershipService {
	@Autowired
	private MembershipRepository membershipRepository;
	@Autowired
	private MembershipMapper membershipMapper;

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
	public MembershipResponseDTO create(MembershipRequestDTO requestDTO) {
		Membership saved = membershipRepository.save(membershipMapper.toEntity(requestDTO));
		return enrich(membershipMapper.toResponseDTO(saved), saved, 0L);
	}

	@Override
	@Transactional
	public MembershipResponseDTO update(Long id, MembershipRequestDTO requestDTO) {
		Membership existing = membershipRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Membership not found with ID: " + id));
		membershipMapper.updateEntityFromDTO(requestDTO, existing);
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
