package config;


import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryConfig {
	private static final String CLOUD_NAME = "dgf2upkwf";
	private static final String API_KEY = "367517456323582";
	private static final String API_SECRET = "6teYf5h8IP906Jnt083eBOkKHy0";

	private CloudinaryConfig() {
		// private constructor to prevent instantiation
	}

	public static Cloudinary getCloudinaryInstance() {
		Cloudinary cloudinary = new Cloudinary(
				ObjectUtils.asMap("cloud_name", CLOUD_NAME, "api_key", API_KEY, "api_secret", API_SECRET));
		return cloudinary;
	}
}
