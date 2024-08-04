package idat.proyecto.veterinaria.cloud;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GoogleStorage {
	
	private String nameKeyJson = "proyecto-veterinario-firebase.json";
	private String projectId = "proyecto-veterinario";
	private String bucketName = "proyecto-veterinario.appspot.com";
	private String pathName;
	private String prefix;
	
	public GoogleStorage(String pathName, String prefix) {
		this.pathName = pathName;
		this.prefix = prefix;
	}
	
	public String uploadImage(String fileName, MultipartFile file) {
		
	    String originalFilename = file.getOriginalFilename();
	    
	    String extension = "";
	    if (originalFilename != null && originalFilename.contains(".")) {
	        extension = originalFilename.substring(originalFilename.lastIndexOf("."));
	    }

	    fileName = prefix + fileName + extension;

	    GoogleCredentials credentials;
	    try {
	        Resource resource = new ClassPathResource(nameKeyJson);
	        InputStream inputStream = resource.getInputStream();
	        credentials = GoogleCredentials.fromStream(inputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }

	    Storage storage = StorageOptions.newBuilder()
	        .setProjectId(projectId)
	        .setCredentials(credentials)
	        .build()
	        .getService();
	    
	    String contentType = "image/jpeg";
	    if (extension.equals(".png")) {
	        contentType = "image/png";
	    }
	    
	    BlobInfo blobInfo = BlobInfo.newBuilder(BlobId.of(bucketName, pathName + "/" + fileName))
	        .setContentType(contentType)
	        .setCacheControl("no-cache")
	        .build();

	    try {
	        byte[] content = file.getBytes();
	        storage.create(blobInfo, content);
	        System.out.println("Imagen subida a Cloud Storage con éxito");
	    } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	    }
	    
	    String publicUrl = "https://storage.googleapis.com/" + bucketName + "/" + pathName + "/" + fileName;
	    return publicUrl;
	}
	
}
	

