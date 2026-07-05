package com.frank.biblioteca.user.infrastructure.mapper;

import com.frank.biblioteca.user.domain.factory.UserFactory;
import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.infrastructure.entity.UserEntity;

public class UserMapper {

    public static UserEntity toEntity(UserModel domain) {
        return new UserEntity(domain.getId(), domain.getName(), domain.getEmail(),
            domain.getPassword(), domain.getStatus().name(), domain.getCreatedAt(), domain.getRole().name());
    }

    public static UserModel toDomain(UserEntity entity) {
        UserModel user = UserFactory.getInstance(entity.getName(), entity.getEmail(), entity.getPassword(), entity.getRole());
        if ("INACTIVE".equals(entity.getStatus())) user.setInactive();
        return user;
    }
}
