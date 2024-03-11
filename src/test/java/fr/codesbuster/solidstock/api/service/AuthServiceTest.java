package fr.codesbuster.solidstock.api.service;

import fr.codesbuster.solidstock.api.entity.RoleEntity;
import fr.codesbuster.solidstock.api.entity.UserEntity;
import fr.codesbuster.solidstock.api.exception.APIException;
import fr.codesbuster.solidstock.api.payload.LoginDto;
import fr.codesbuster.solidstock.api.payload.RegisterDto;
import fr.codesbuster.solidstock.api.repository.RoleRepository;
import fr.codesbuster.solidstock.api.repository.UserRepository;
import fr.codesbuster.solidstock.api.security.JwtTokenProvider;
import fr.codesbuster.solidstock.api.service.impl.AuthServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@Transactional
@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private AuthServiceImpl authService;

   // @Test
    void login_ValidCredentials_ReturnsToken() {
        // Arrange
        LoginDto loginDto = new LoginDto("testUser", "testPassword");
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        String expectedToken = "testToken";
        Mockito.when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

        // Act
        String result = authService.login(loginDto);

        // Assert
        assertEquals(expectedToken, result);
    }

    //@Test
    void register_ValidUser_RegistersUser() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("testName", "testUser", "testEmail", "testPassword");

        Mockito.when(passwordEncoder.encode(registerDto.getPassword())).thenReturn("encodedPassword");

        // Act
        String result = authService.register(registerDto);

        // Assert
        ArgumentCaptor<UserEntity> userCaptor = ArgumentCaptor.forClass(UserEntity.class);
        verify(userRepository).save(userCaptor.capture());
        UserEntity savedUser = userCaptor.getValue();
        assertEquals(registerDto.getName(), savedUser.getName());
        assertEquals(registerDto.getUsername(), savedUser.getUsername());
        assertEquals(registerDto.getEmail(), savedUser.getEmail());
        assertEquals("encodedPassword", savedUser.getPassword());
        assertEquals(1, savedUser.getRoles().size());
        assertEquals("USER", savedUser.getRoles().iterator().next().getName());
        assertEquals("User registered successfully!.", result);
    }

    //@Test
    void register_ExistingUsername_ThrowsAPIException() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("testName", "testUser", "testEmail", "testPassword");
        Mockito.when(userRepository.existsByUsername(registerDto.getUsername())).thenReturn(true);

        // Act and Assert
        assertThrows(APIException.class, () -> authService.register(registerDto));
    }

   // @Test
    void register_ExistingEmail_ThrowsAPIException() {
        // Arrange
        RegisterDto registerDto = new RegisterDto("testName", "testUser", "testEmail", "testPassword");
        Mockito.when(userRepository.existsByEmail(registerDto.getEmail())).thenReturn(true);

        // Act and Assert
        assertThrows(APIException.class, () -> authService.register(registerDto));
    }

   // @Test
    void getMe_ValidToken_ReturnsUser() {
        // Arrange
        String token = "testToken";
        String username = "testUser";
        UserEntity user = new UserEntity();
        user.setUsername(username);
        Mockito.when(jwtTokenProvider.getUsername(token)).thenReturn(username);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // Act
        UserEntity result = authService.getMe(token);

        // Assert
        assertEquals(user, result);
    }
}
