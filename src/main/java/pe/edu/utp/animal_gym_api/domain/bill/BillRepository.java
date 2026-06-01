package pe.edu.utp.animal_gym_api.domain.bill;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
	List<Bill> findByPartnerId(Long partnerId);

	@Query("SELECT new pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO(" +
			"b.id, b.issueDate, b.time, b.subTotal, b.totalPrice, b.igv, b.status, " +
			"e.firstName, e.lastName, " +
			"p.firstName, p.lastName, " +
			"m.name) " +
			"FROM Bill b " +
			"JOIN b.employee e " +
			"JOIN b.partner p " +
			"JOIN p.membership m")
	List<BillResponseDTO> findAllBills();

	@Query("SELECT new pe.edu.utp.animal_gym_api.domain.bill.dto.BillResponseDTO(" +
			"b.id, b.issueDate, b.time, b.subTotal, b.totalPrice, b.igv, b.status, " +
			"e.firstName, e.lastName, " +
			"p.firstName, p.lastName, " +
			"m.name) " +
			"FROM Bill b " +
			"JOIN b.employee e " +
			"JOIN b.partner p " +
			"JOIN p.membership m " +
			"WHERE b.id = :id")
	Optional<BillResponseDTO> findDetailById(@Param("id") Long id);
}
