package fr.cyberdodo.exampleLoginApi.service;


import fr.cyberdodo.exampleLoginApi.payload.LoginDto;
import fr.cyberdodo.exampleLoginApi.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);
}