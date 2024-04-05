package fr.codesbuster.solidstock.api.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String name;
    private String firstName;
    private String username;
    private String email;
    private String password;
    private int roleId;
    private int customerId;
}