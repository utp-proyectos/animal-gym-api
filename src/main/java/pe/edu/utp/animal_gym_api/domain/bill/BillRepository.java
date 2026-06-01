package pe.edu.utp.animal_gym_api.domain.bill;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BillRepository extends JpaRepository<Bill, Long> {
	List<Bill> findByPartnerId(Long partnerId);
}
