package pe.edu.utp.animal_gym_api.domain.exercises.service;

import java.util.List;

import pe.edu.utp.animal_gym_api.domain.exercises.Exercise;

public interface ExerciseService {
	List<Exercise> findAll();

	Exercise findById(Long id);

	Exercise create(Exercise exercise);

	Exercise update(Long id, Exercise exercise);

	void delete(Long id);

	List<Exercise> search(String name, String muscleGroup, String equipment);
}