package com.frank.biblioteca.user.domain.factory;

import com.frank.biblioteca.user.domain.model.UserModel;
import com.frank.biblioteca.user.domain.model.value_object.UserRole;

public class UserFactory {
    
    public static UserModel getInstance(String name, 
        String email, String password, String role) {
        return new UserModel(name, email, password, UserRole.valueOf(role));
    }
}
