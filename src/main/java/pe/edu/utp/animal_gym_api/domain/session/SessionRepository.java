package pe.edu.utp.animal_gym_api.domain.session;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {
	List<Session> findByEmployee_Id(Long id);
}
