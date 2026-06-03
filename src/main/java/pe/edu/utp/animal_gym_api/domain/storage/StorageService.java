package pe.edu.utp.animal_gym_api.domain.storage;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
	public String upload(MultipartFile file, String folder) throws IOException;
}
