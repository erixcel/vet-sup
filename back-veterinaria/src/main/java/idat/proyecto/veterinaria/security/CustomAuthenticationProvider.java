package idat.proyecto.veterinaria.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import idat.proyecto.veterinaria.entity.Rol;
import idat.proyecto.veterinaria.entity.Usuario;
import idat.proyecto.veterinaria.repository.UsuarioRepository;
import idat.proyecto.veterinaria.token.JwtUtil;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        Optional<Usuario> optionalUsuario = Optional.ofNullable(usuarioRepository.findByUsername(username));
        if (!optionalUsuario.isPresent()) {
            throw new BadCredentialsException("Usuario no encontrado");
        }

        Usuario usuario = optionalUsuario.get();

        if (usuario.getEliminado()) {
            throw new BadCredentialsException("Usuario eliminado");
        }

        if (!passwordEncoder.matches(password, usuario.getPassword())) {
            throw new BadCredentialsException("Contraseña incorrecta");
        }

        String jwtToken = jwtUtil.generateToken(usuario);

        Set<Rol> roles = usuario.getRoles();

        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol : roles) {
            authorities.add(new SimpleGrantedAuthority(rol.getNombre()));
        }
        
        return new UsernamePasswordAuthenticationToken(jwtToken, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

