package pe.edu.utp.animal_gym_api.domain.exercises;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

	List<Exercise> findByNameContainingIgnoreCase(String name);

	List<Exercise> findByMuscleGroupContainingIgnoreCase(String muscleGroup);

	List<Exercise> findByEquipmentContainingIgnoreCase(String equipment);

	@Query("SELECT e FROM Exercise e WHERE " +
			"(:name IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
			"(:muscleGroup IS NULL OR LOWER(e.muscleGroup) LIKE LOWER(CONCAT('%', :muscleGroup, '%'))) AND " +
			"(:equipment IS NULL OR LOWER(e.equipment) LIKE LOWER(CONCAT('%', :equipment, '%')))")
	List<Exercise> search(@Param("name") String name,
			@Param("muscleGroup") String muscleGroup,
			@Param("equipment") String equipment);
}
