package idat.proyecto.veterinaria.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrypPassword {
	
	public void main(String[] args) {
        String password = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        System.out.println(hashedPassword);
    }
}
