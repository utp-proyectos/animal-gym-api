package pe.edu.utp.animal_gym_api.domain.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageServiceImpl implements StorageService {
	@Override
	public String upload(MultipartFile file, String folder) throws IOException {
		String destiny = "storage/" + folder + "/";
		String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		filename = filename.replace(" ", "_");

		Path path = Paths.get(destiny + filename);

		Files.createDirectories(path.getParent());
		Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

		String baseUrl = "http://localhost:8080/";

		return baseUrl + destiny + filename;
	}

}
