package pe.edu.utp.animal_gym_api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.cloudinary.Cloudinary;

@Component
@Profile("prod")
public class CloudinaryConfig {
	@Value("${CLOUDINARY_URL}")
	private String cloudinaryUrl;

	@Bean
	Cloudinary cloudinary() {
		return new Cloudinary(cloudinaryUrl);
	}
}
