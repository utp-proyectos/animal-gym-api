package pe.edu.utp.animal_gym_api.domain.partner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {

	List<Partner> findByStatus(Boolean status);

	List<Partner> findByExpirationDateBetween(LocalDate startDate, LocalDate endDate);

	List<Partner> findByMembershipId(Long membershipId);

	@Query("SELECT p FROM Partner p WHERE LOWER(CONCAT(p.name, ' ', p.lastName)) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Partner> findByNameContainingIgnoreCase(@Param("name") String name);

	Long countByStatusTrue();

	@Query("SELECT COUNT(p) FROM Partner p " +
			"WHERE YEAR(p.expirationDate) = YEAR(CURRENT_DATE) " +
			"AND MONTH(p.expirationDate) = MONTH(CURRENT_DATE) " +
			"AND p.status = false")
	Integer countExpiredAndInactiveThisMonth();

	Optional<Partner> findById(Long id);
}
