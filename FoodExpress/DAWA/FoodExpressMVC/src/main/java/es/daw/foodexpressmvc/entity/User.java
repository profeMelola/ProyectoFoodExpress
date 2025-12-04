package es.daw.foodexpressmvc.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column // nullable iría false y longitud por defecto???
    //@Column(name="FULL_NAME")
    //private String fullname; // KK
    private String fullName;

    @Column
    private String email;

    // Relación bidireccional. Usuario es el lado propietario, porque tiene la tabla intermedia
    // fetch = FetchType.EAGER indica que los roles se cargan siempre junto con el usuario,
    // lo cual es necesario porque Spring Security los necesita inmediatamente para construir las autoridades.
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;

    public User(){
        roles = new HashSet<>();
    }

    public void addRole(Role rol) {
        roles.add(rol);
        //rol.getUsers().add(this);
        rol.addUser(this);
    }

    public void removeRole(Role rol) {
        roles.remove(rol);
        //rol.getUsers().remove(this);
        rol.removeUser(this);
    }


    // --------------------- 5 MÉTODOS DE LA INTERFACE UserDetails -----------------

    // Devuelve los roles convertidos en objetos GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                // si en bd el rol no tiene el prefijo ROLE_
                //.map (rol -> (GrantedAuthority) () -> "ROLE_"+rol.getName())
                .map(rol -> (GrantedAuthority) rol::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Indica si la cuenta del usuario ha expirado.
     * Devuelve true si la cuenta no ha expirado.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
        // // Devuelve true si la fecha actual es anterior o igual a la de expiración
        //    return !LocalDate.now().isAfter(accountExpirationDate);
    }

    /**
     * Indica si la cuenta del usuario está bloqueada.
     * Devuelve true si la cuenta no está bloqueada.
     */
    @Override
    public boolean isAccountNonLocked() { return true; }

    /**
     * Indica si las credenciales del usuario (contraseña) han expirado.
     * Devuelve true si las credenciales no han expirado.
     */
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    /**
     * Indica si la cuenta del usuario está habilitada.
     * Devuelve true si la cuenta está activa.
     */
    @Override
    public boolean isEnabled() { return true; }

}