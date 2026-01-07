# Bean Validation con i18n

Las anotaciones como @NotBlank, @Size, etc.:
- usan Bean Validation (Jakarta Validation)
- el atributo message no es el texto final, es una clave de mensaje
- si esa clave existe en messages.properties, Spring la resuelve según el Locale

Así quedaría el DTO:

```
@Data
public class UserRegisterDTO {

    @NotBlank(message = "{user.username.notblank}")
    @Size(min = 4, message = "{user.username.size}")
    private String username;

    @Size(max = 100, message = "{user.fullname.size}")
    private String fullName;

    @NotBlank(message = "{user.email.notblank}")
    private String email;

    @NotBlank(message = "{user.password.notblank}")
    private String password;

    @NotBlank(message = "{user.confirmPassword.notblank}")
    private String confirmPassword;
}
```

Debes añadir las propiedades a messages.properties:

```
user.username.notblank=Username cannot be empty
user.username.size=Username must have at least 4 characters

user.fullname.size=Full name cannot exceed 100 characters

user.email.notblank=Email cannot be empty

user.password.notblank=Password cannot be empty
user.confirmPassword.notblank=Password confirmation cannot be empty

```

Y a messages_es.properties:

```
user.username.notblank=El nombre de usuario no puede estar en blanco
user.username.size=El nombre de usuario debe tener al menos 4 caracteres

user.fullname.size=El nombre completo no puede superar los 100 caracteres

user.email.notblank=El email no puede estar en blanco

user.password.notblank=La contraseña no puede estar en blanco
user.confirmPassword.notblank=La confirmación de contraseña no puede estar en blanco

```