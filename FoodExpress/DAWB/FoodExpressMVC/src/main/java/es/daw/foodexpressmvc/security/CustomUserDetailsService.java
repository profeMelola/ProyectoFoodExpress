package es.daw.foodexpressmvc.security;

import es.daw.foodexpressmvc.entity.User;
import es.daw.foodexpressmvc.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security trabaja con un sistema de autenticación basado en UserDetailsService, que carga los usuarios desde la base de datos.
 * User, al implementar UserDetails, tu entidad User es compatible con Spring Security y puedes personalizar la lógica de autenticación y autorización.
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Cargar el usuario desde de la base de datos
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        return user;

    }
}