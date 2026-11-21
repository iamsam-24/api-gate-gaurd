package com.coprod.gatekeeper.management.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class LoginRequest {

    @NotNull
    private String username;

    @NotNull
    private String password;
}
