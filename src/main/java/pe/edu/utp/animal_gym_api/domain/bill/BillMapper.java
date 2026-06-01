package pe.edu.utp.animal_gym_api.domain.bill;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.bill.dto.BillRequestDTO;
import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;

@Mapper(componentModel = "spring")
public interface BillMapper {
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "partner", ignore = true)
	@Mapping(target = "employee", ignore = true)
	Bill toEntity(BillRequestDTO dto);

	@Mapping(target = "employeeFirstName", source = "bill.employee.firstName")
	@Mapping(target = "employeeLastName", source = "bill.employee.lastName")
	@Mapping(target = "partnerFirstName", source = "bill.partner.firstName")
	@Mapping(target = "partnerLastName", source = "bill.partner.lastName")
	@Mapping(target = "membershipName", source = "bill.partner.membership.name")
	BillResponseDTO toResponseDto(Bill bill);
}
