package idat.proyecto.veterinaria.configuration;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
public class Infinito {

	@Scheduled(fixedRate = 300000)
    public void ejecutarAPI() {
		final String uri = "https://veterinaria-supabase.onrender.com/veterinaria/testing";
        
        RestTemplate restTemplate = new RestTemplate();
        
        try {
   
        	ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(uri,HttpMethod.GET,null,new ParameterizedTypeReference<Map<String, String>>(){});
            Map<String, String> resultado = responseEntity.getBody();
            System.out.println("Respuesta de la API: " + resultado.get("message"));
            
        } catch (RestClientException e) {
            System.out.println("Solicitud fallida");
        }
    }
}
