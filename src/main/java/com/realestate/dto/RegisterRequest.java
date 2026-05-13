package com.realestate.dto;

import com.realestate.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    private String phone;

    private User.Role role = User.Role.BUYER;

    public String getName()      { return name; }
    public String getEmail()     { return email; }
    public String getPassword()  { return password; }
    public String getPhone()     { return phone; }
    public User.Role getRole()   { return role; }

    public void setName(String name)          { this.name = name; }
    public void setEmail(String email)        { this.email = email; }
    public void setPassword(String password)  { this.password = password; }
    public void setPhone(String phone)        { this.phone = phone; }
    public void setRole(User.Role role)       { this.role = role; }
}
