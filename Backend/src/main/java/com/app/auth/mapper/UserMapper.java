package com.app.auth.mapper;

import com.app.auth.dto.request.SignupRequestDto;
import com.app.auth.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(SignupRequestDto signupRequestDto);
}