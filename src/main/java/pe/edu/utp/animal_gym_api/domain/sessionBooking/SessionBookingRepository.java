package pe.edu.utp.animal_gym_api.domain.sessionBooking;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionBookingRepository extends JpaRepository<SessionBooking, Long> {

	// Busca las reservas de un socio por su ID
	List<SessionBooking> findByPartner_Id(Long partnerId);

	// Verifica si ya existe una reserva para ese socio en esa sesión
	boolean existsByPartner_IdAndSession_Id(Long partnerId, Long sessionId);

	@Modifying
	@Query("DELETE FROM SessionBooking r WHERE r.partner.id = :partnerId AND r.session.id = :sessionId")
	void deleteByPartnerIdAndSessionId(@Param("partnerId") Long partnerId, @Param("sessionId") Long sessionId);
}
