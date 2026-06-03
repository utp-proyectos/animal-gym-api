package pe.edu.utp.animal_gym_api.domain.membership;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

	List<Membership> findByStatus(Boolean status);

	List<Membership> findByPriceBetween(Double minPrice, Double maxPrice);

	@Query("SELECT m FROM Membership m WHERE m.capacityLimit > " +
			"(SELECT COUNT(p) FROM Partner p WHERE p.membership.id = m.id AND p.status = true)")
	List<Membership> findWithAvailableCapacity();

	@Query("SELECT m.id, COUNT(p) FROM Partner p JOIN p.membership m WHERE p.status = true GROUP BY m.id")
	List<Object[]> countEnrolledPartnersByMembership();

	@Query("SELECT COUNT(p) FROM Partner p WHERE p.membership.id = :id AND p.status = true")
	Long countActiveByMembershipId(@Param("id") Long id);
}
