package idat.proyecto.veterinaria.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.repository.UsuarioRepository;
import idat.proyecto.veterinaria.response.Response;
import idat.proyecto.veterinaria.token.JwtUtil;

@Service
public class AuthServiceImpl implements AuthService{
	
	@Autowired
    private AuthenticationManager authenticationManager;
	
	@Autowired
    private UsuarioRepository usuarioRepository;
	
	@Autowired
    private JwtUtil jwtUtil;

	@Override
	public ResponseEntity<?> authentication(Usuario usuario) {	
		try {
	        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword());
	        Authentication authentication = authenticationManager.authenticate(auth);
	        String jwtToken = (String) authentication.getPrincipal();
	        return new ResponseEntity<>(Response.createMapAuth("Successful Authentication!", jwtToken), HttpStatus.OK);
	    } catch (BadCredentialsException e) {
	        return new ResponseEntity<>(Response.createMapAuth("Incorrect Credentials!",null), HttpStatus.UNAUTHORIZED);
	    }
	}
    
    @Override
    public ResponseEntity<?> verifyToken(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String jwtToken = authorizationHeader.substring(7);
            if (jwtUtil.validateToken(jwtToken)) {
                Long remaining = jwtUtil.getRemainingTime(jwtToken);
                return new ResponseEntity<>(Response.createMapValidToken("Token valid!", true, remaining), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(Response.createMapValidToken("Token invalid!", false, null), HttpStatus.UNAUTHORIZED);
    }
    
    @Override
    public ResponseEntity<?> getUsuario(String authorizationHeader) {
    	try {
    		String jwtToken = authorizationHeader.substring(7);
            String username = jwtUtil.getUsernameFromToken(jwtToken);
            Usuario usuario = usuarioRepository.findByUsername(username);
            return new ResponseEntity<>(usuario, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(Response.createMapError("The user can't be obtained!", e.getMessage()), HttpStatus.UNAUTHORIZED);
		}
        
    }
    
    @Override
    public ResponseEntity<?> testing() {
		return new ResponseEntity<>(Response.createMessage("The user can't be obtained!"), HttpStatus.OK);
    }

}
