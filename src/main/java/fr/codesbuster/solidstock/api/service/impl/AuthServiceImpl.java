package fr.codesbuster.solidstock.api.service.impl;


import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.dto.LoginDto;
import fr.codesbuster.solidstock.api.payload.dto.RegisterDto;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.security.JwtTokenProvider;
import fr.codesbuster.solidstock.api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        log.info(token);

        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // Vérifier si l'email existe déjà dans la base de données
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new APIException(HttpStatus.BAD_REQUEST, "Email is already exists!.");
        }

        UserEntity user = new UserEntity();
        user.setLastName(registerDto.getLastName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        // Récupérer le rôle correspondant au nom "USER"
        RoleEntity userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "Role USER not found!."));

        // Créer une liste de rôles pour l'utilisateur et ajouter le rôle récupéré
        List<RoleEntity> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);

        // Enregistrer l'utilisateur dans la base de données
        userRepository.save(user);

        return "User registered successfully!.";
    }


    @Override
    public UserEntity getMe(String token) {
        // Vérifiez et récupérez l'authentification à partir du token JWT
        String username = jwtTokenProvider.getUsername(token);
        log.info(username);
        //try by get user by username  or email
        UserEntity user = userRepository.findByEmail(username).orElseThrow(() -> new APIException(HttpStatus.BAD_REQUEST, "User not found!."));


        return user;
    }


}