package pe.edu.utp.animal_gym_api.common.enums;

public enum Role {
	ADMIN("ROLE_ADMIN"),
	ENTRENADOR("ROLE_ENTRENADOR"),
	SOCIO("ROLE_SOCIO"),
	RECEPCIONISTA("ROLE_RECEPCIONISTA");

	private final String name;

	Role(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}
