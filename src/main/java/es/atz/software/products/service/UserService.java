package es.atz.software.products.service;

import es.atz.software.products.dto.UserDTO;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);

    UserDTO getUserById(Long id);
}
