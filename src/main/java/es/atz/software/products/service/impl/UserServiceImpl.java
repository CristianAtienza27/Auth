package es.atz.software.products.service.impl;

import es.atz.software.products.domain.Role;
import es.atz.software.products.domain.User;
import es.atz.software.products.dto.UserDTO;
import es.atz.software.products.repository.UserRepository;
import es.atz.software.products.security.PasswordService;
import es.atz.software.products.service.EmailService;
import es.atz.software.products.service.UserService;
import es.atz.software.products.utils.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private EmailService emailService;

    public UserDTO registerUser(UserDTO userDTO) {
        UserMapper userMapper = UserMapper.INSTANCE;
        User user = userMapper.fromDTOWithPassword(userDTO, passwordService);
        user.setRoles(List.of(new Role(1L)));
        User savedUser = userRepository.save(user);
        emailService.sendEmailVerify(userDTO);
        return userMapper.toDTO(savedUser);
    }

    public UserDTO getUserById(Long id) {
        UserMapper userMapper = UserMapper.INSTANCE;
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDTO(user);
    }
}
