package pe.edu.utp.animal_gym_api.domain.sessionBooking;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import pe.edu.utp.animal_gym_api.domain.sessionBooking.dto.PartnerEnrolledResponseDTO;

@Mapper(componentModel = "spring")
public interface SessionBookingMapper {

	@Mapping(source = "id", target = "bookingId")
	@Mapping(source = "partner.id", target = "partnerId")
	@Mapping(source = "partner.dni", target = "dni")
	@Mapping(source = "partner.firstName", target = "firstName")
	@Mapping(source = "partner.lastName", target = "lastName")
	@Mapping(source = "date", target = "enrollmentDate")
	PartnerEnrolledResponseDTO toEnrolledResponse(SessionBooking booking);

	List<PartnerEnrolledResponseDTO> toEnrolledResponseList(List<SessionBooking> bookings);
}
