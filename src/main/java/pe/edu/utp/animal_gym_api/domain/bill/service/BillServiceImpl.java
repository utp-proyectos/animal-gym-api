package pe.edu.utp.animal_gym_api.domain.bill.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import pe.edu.utp.animal_gym_api.common.enums.Role;
import pe.edu.utp.animal_gym_api.domain.bill.Bill;
import pe.edu.utp.animal_gym_api.domain.bill.BillMapper;
import pe.edu.utp.animal_gym_api.domain.bill.BillRepository;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;

@Service

public class BillServiceImpl implements BillService {
	@Autowired
	private BillRepository billRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private PartnerRepository partnerRepository;

	@Autowired
	private BillMapper billMapper;

	@Override
	public List<BillResponseDTO> findAll(Authentication auth) {
		List<Bill> bills;

		if (canViewAllBills(auth)) {
			bills = billRepository.findAll();
		} else {
			String dni = auth.getName();
			bills = billRepository.findByPartner_Dni(dni);
		}

		return bills.stream()
				.map(billMapper::toResponseDto)
				.collect(Collectors.toList());
	}

	@Override
	public BillResponseDTO findById(Long id, Authentication auth) {
		Bill bill = billRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Bill not found"));

		boolean isOwner = bill.getPartner() != null
				&& bill.getPartner().getDni().equals(auth.getName());

		if (!canViewAllBills(auth) && !isOwner) {
			throw new AccessDeniedException("Not authorized to view this bill");
		}

		return billMapper.toResponseDto(bill);
	}

	private boolean canViewAllBills(Authentication auth) {
		return auth.getAuthorities().stream()
				.anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")
						|| a.getAuthority().equals("ROLE_RECEPCIONISTA"));
	}

	@Override
	@Transactional
	public BillResponseDTO save(BillRequestDTO dto) {
		Employee employee = employeeRepository.findById(dto.getEmployeeId())
				.orElseThrow(() -> new EntityNotFoundException("Employee not found with ID: " + dto.getEmployeeId()));

		Partner partner = partnerRepository.findById(dto.getPartnerId())
				.orElseThrow(() -> new EntityNotFoundException("Partner not found with ID: " + dto.getPartnerId()));

		Bill bill = billMapper.toEntity(dto);
		bill.setEmployee(employee);
		bill.setPartner(partner);

		// Snapshot: se congela aquí, al momento de emitir la boleta
		bill.setPartnerDni(partner.getDni());
		bill.setPartnerFirstName(partner.getFirstName());
		bill.setPartnerLastName(partner.getLastName());

		bill.setEmployeeDni(employee.getDni());
		bill.setEmployeeFirstName(employee.getFirstName());
		bill.setEmployeeLastName(employee.getLastName());

		if (partner.getMembership() != null) {
			bill.setMembershipName(partner.getMembership().getName());
		}

		billRepository.save(bill);

		return billMapper.toResponseDto(bill);
	}
}
