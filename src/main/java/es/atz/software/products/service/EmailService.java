package es.atz.software.products.service;

import es.atz.software.products.dto.UserDTO;

public interface EmailService {

    void sendEmailVerify(UserDTO userDTO);
}
