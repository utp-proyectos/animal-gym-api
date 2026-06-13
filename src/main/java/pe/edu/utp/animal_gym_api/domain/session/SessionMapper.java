package pe.edu.utp.animal_gym_api.domain.session;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.employee.Employee;
import pe.edu.utp.animal_gym_api.domain.employee.dto.EmployeeResponseDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionCardDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionDetailDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionParticipantDTO;
import pe.edu.utp.animal_gym_api.domain.session.dto.SessionRequestDTO;
import pe.edu.utp.animal_gym_api.domain.sessionBooking.SessionBooking;

@Mapper(componentModel = "spring")
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
	@Mapping(target = "employeeId", source = "employee.id")
	@Mapping(target = "date", source = "date")
	@Mapping(target = "startTime", source = "startTime")
	@Mapping(target = "endTime", source = "endTime")
	SessionCardDTO toCardDTO(Session session);

	@Mapping(target = "currentBookings", expression = "java(session.getBookings() != null ? session.getBookings().size() : 0)")
	@Mapping(target = "enrolled", ignore = true)
	@Mapping(target = "participants", source = "bookings")
	@Mapping(target = "employee", source = "employee")
	SessionDetailDTO toDetailDTO(Session session);

	@Mapping(target = "role", ignore = true)
	EmployeeResponseDTO toResponseEmployeeDto(Employee employee);

	@Mapping(target = "id", source = "partner.id")
	@Mapping(target = "dni", source = "partner.dni")
	@Mapping(target = "firstName", source = "partner.firstName")
	@Mapping(target = "lastName", source = "partner.lastName")
	SessionParticipantDTO toParticipantDTO(SessionBooking booking);
}
