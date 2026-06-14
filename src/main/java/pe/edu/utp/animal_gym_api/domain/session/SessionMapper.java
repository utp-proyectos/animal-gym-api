package pe.edu.utp.animal_gym_api.domain.session;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.employee.EmployeeMapper;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionParticipantDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

@Mapper(componentModel = "spring", uses = { EmployeeMapper.class })
public interface SessionMapper {

	@Mapping(target = "employee.id", source = "employeeId")
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "bookings", ignore = true)
	@Mapping(target = "image", ignore = true)
	@Mapping(target = "status", ignore = true)
	@Mapping(target = "duration", ignore = true)
	@Mapping(target = "enrolled", ignore = true)
	Session toEntity(SessionRequestDTO dto);

	@Mapping(target = "enrolled", ignore = true)
	@Mapping(target = "employee", source = "employee")
	@Mapping(target = "date", source = "date")
	@Mapping(target = "startTime", source = "startTime")
	@Mapping(target = "endTime", source = "endTime")
	SessionCardDTO toCardDTO(Session session);

	@Mapping(target = "id", source = "partner.id")
	@Mapping(target = "dni", source = "partner.dni")
	@Mapping(target = "firstName", source = "partner.firstName")
	@Mapping(target = "lastName", source = "partner.lastName")
	SessionParticipantDTO toParticipantDTO(SessionBooking booking);
}
