package es.atz.software.products.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class UserDTO implements Serializable {

    private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private List<RoleDTO> roles;
}
