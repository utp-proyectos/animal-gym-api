package pe.edu.utp.animal_gym_api.domain.exercises.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;
import pe.edu.utp.animal_gym_api.domain.exercises.ExerciseRepository;

@Service
public class ExerciseServiceImpl implements ExerciseService {

	@Autowired
	private ExerciseRepository exerciseRepository;

	@Override
	public List<Exercise> findAll() {
		return exerciseRepository.findAll();
	}

	@Override
	public Exercise findById(Long id) {
		return exerciseRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Exercise not found with ID: " + id));
	}

	@Override
	public Exercise create(Exercise exercise) {
		return exerciseRepository.save(exercise);
	}

	@Override
	public Exercise update(Long id, Exercise exercise) {
		if (!exerciseRepository.existsById(id)) {
			throw new EntityNotFoundException("Exercise not found with ID: " + id);
		}
		exercise.setId(id);
		return exerciseRepository.save(exercise);
	}

	@Override
	public void delete(Long id) {
		if (!exerciseRepository.existsById(id)) {
			throw new EntityNotFoundException("Exercise not found with ID: " + id);
		}
		exerciseRepository.deleteById(id);
	}

	@Override
	public List<Exercise> search(String name, String muscleGroup, String equipment) {
		return exerciseRepository.search(name, muscleGroup, equipment);
	}
}