package com.realestate.dto;

public class AuthResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private String role;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String name, String email, String role) {
        this.token = token;
        this.id    = id;
        this.name  = name;
        this.email = email;
        this.role  = role;
    }

    public String getToken()  { return token; }
    public String getType()   { return type; }
    public Long   getId()     { return id; }
    public String getName()   { return name; }
    public String getEmail()  { return email; }
    public String getRole()   { return role; }

    public void setToken(String token)  { this.token = token; }
    public void setType(String type)    { this.type  = type; }
    public void setId(Long id)          { this.id    = id; }
    public void setName(String name)    { this.name  = name; }
    public void setEmail(String email)  { this.email = email; }
    public void setRole(String role)    { this.role  = role; }
}
