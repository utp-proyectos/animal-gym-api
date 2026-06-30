package pe.edu.utp.animal_gym_api.domain.storage;

import java.io.IOException;
import java.util.Map;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@Profile("prod")
@RequiredArgsConstructor
public class CloudinaryStorageService implements StorageService {
	private final Cloudinary cloudinary;

	@Override
	public String upload(MultipartFile file, String folder) throws IOException {
		Map<String, Object> options = Map.of(
				"folder", folder,
				"resource_type", "auto");

		return (String) cloudinary.uploader().upload(file.getBytes(), options).get("secure_url");

	}
}
