package es.atz.software.products.utils.mapper;

import es.atz.software.products.domain.User;
import es.atz.software.products.dto.UserDTO;
import es.atz.software.products.security.PasswordService;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring") // MapStruct gestionado por Spring
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "roles", source = "roles")
    UserDTO toDTO(User user);

    @Mapping(target = "roles", source = "roles")
    User toEntity(UserDTO userDTO);

    // Método personalizado para codificar la contraseña
    @Mapping(target = "password", ignore = true) // Ignoramos el mapeo automático de la contraseña
    User fromDTOWithPassword(UserDTO userDTO, @Context PasswordService passwordService);

    @AfterMapping
    default void encodePassword(@MappingTarget User user, UserDTO userDTO, @Context PasswordService passwordService) {
        user.setPassword(passwordService.encodePassword(userDTO.getPassword()));
    }
}
