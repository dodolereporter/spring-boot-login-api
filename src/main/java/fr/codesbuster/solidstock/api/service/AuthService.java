package fr.codesbuster.solidstock.api.service;


import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.payload.dto.LoginDto;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    UserEntity getMe(String token);
}