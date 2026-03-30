package com.self.user_service.mapper;

import com.self.user_service.dto.UserRequestDTO;
import com.self.user_service.dto.UserResponseDTO;
import com.self.user_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {


    @Mapping(source = "fullName", target = "name")
    @Mapping(source = "emailAddress", target = "email")
    @Mapping(source = "rawPassword", target = "password")
    @Mapping(source = "mobileNumber", target = "phoneNumber")
    @Mapping(source = "userAddress", target = "address")
    User toEntity(UserRequestDTO dto);


    @Mapping(source = "userId", target = "id")
    @Mapping(source = "name", target = "fullName")
    @Mapping(source = "email", target = "emailAddress")
    @Mapping(source = "phoneNumber", target = "mobileNumber")
    @Mapping(source = "address", target = "userAddress")
    UserResponseDTO toResponseDTO(User user);
}
