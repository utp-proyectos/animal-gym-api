package pe.edu.utp.animal_gym_api.domain.bill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pe.edu.utp.animal_gym_api.domain.bill.Bill;
import pe.edu.utp.animal_gym_api.domain.bill.BillRepository;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;
import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.EmployeeRepository;
import pe.edu.utp.animal_gym_api.domain.partner.Partner;
import pe.edu.utp.animal_gym_api.domain.partner.PartnerRepository;

@Service
@RequiredArgsConstructor

public class BillServiceImpl implements BillService {
	private final BillRepository billRepository;
	private final EmployeeRepository employeeRepository;
	private final PartnerRepository partnerRepository;

	@Override
	public List<BillResponseDTO> findAll() {
		return billRepository.findAllBills();
	}

	@Override
	public BillResponseDTO findById(Long id) {
		return billRepository.findDetailById(id)
				.orElseThrow(() -> new RuntimeException("Boleta no encontrada"));
	}

	@Override
	public BillResponseDTO save(BillRequestDTO dto) {
		Employee employee = employeeRepository.findById(dto.getEmployeeId())
				.orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

		Partner partner = partnerRepository.findById(dto.getPartnerId())
				.orElseThrow(() -> new RuntimeException("Socio no encontrado"));

		Bill bill = new Bill();
		bill.setIssueDate(dto.getIssueDate());
		bill.setTime(dto.getTime());
		bill.setSubTotal(dto.getSubTotal());
		bill.setTotalPrice(dto.getTotalPrice());
		bill.setIgv(dto.getIgv());
		bill.setStatus(dto.isStatus());
		bill.setEmployee(employee);
		bill.setPartner(partner);
		billRepository.save(bill);

		return billRepository.findDetailById(bill.getId())
				.orElseThrow(() -> new RuntimeException("Error al guardar boleta"));
	}

}
