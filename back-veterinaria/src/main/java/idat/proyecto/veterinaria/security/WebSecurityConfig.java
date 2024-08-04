package idat.proyecto.veterinaria.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import idat.proyecto.veterinaria.token.JwtAuthenticationFilter;

@SuppressWarnings("deprecation")
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider customAuthProvider;
    
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthProvider);
    }
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/mascotas/**","/citas/**").hasAnyAuthority("RECEPCIONISTA","GROOMER","VETERINARIO")
                .antMatchers("/clientes/**","/boletas/**").hasAuthority("RECEPCIONISTA")
                .antMatchers("/tratamientos/**").hasAuthority("VETERINARIO")
                .antMatchers("/banios/**").hasAuthority("GROOMER")
                .antMatchers("/productos/**").hasAnyAuthority("ALMACENERO","RECEPCIONISTA")
                .antMatchers("/categorias/**","/proveedores/**").hasAnyAuthority("ALMACENERO")
                .antMatchers("/razas/**","/especies/**").hasAnyAuthority("VETERINARIO")
                .antMatchers("/usuarios/**","/roles/**","/pedidos/**").hasAuthority("ADMINISTRADOR")
                .and()
            .httpBasic()
                .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.cors();
    }
}



