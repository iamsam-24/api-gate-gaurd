package com.coprod.gatekeeper.management.service;

import com.coprod.gatekeeper.management.dto.request.LoginRequest;
import com.coprod.gatekeeper.management.dto.response.LoginResponse;
import com.coprod.gatekeeper.management.entity.ManagementUsers;
import com.coprod.gatekeeper.management.entity.OrgStatus;
import com.coprod.gatekeeper.management.exception.ApiException;
import com.coprod.gatekeeper.management.repository.ManagementUsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class AuthService {

    private final ManagementUsersRepository managementUsersRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;
    private final long expirationSecond;

    public AuthService(ManagementUsersRepository managementUsersRepository,
                       PasswordEncoder passwordEncoder,
                       JwtEncoder jwtEncoder,
                       @Value("${jwt.expiration.time}")
                       long expirationSecond) {
        this.managementUsersRepository = managementUsersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
        this.expirationSecond = expirationSecond;
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest){

        //get the users from the repo

       ManagementUsers users= managementUsersRepository.findByUsername(loginRequest.getUsername()).
               orElseThrow(()->InvalidCredentials());
        //now check password matching condition

     if(!users.isEnabled() ||!passwordEncoder.matches(loginRequest.getPassword(),users.getPasswordHash())){
         throw InvalidCredentials();
     }

        //an extra check for organisation

        if(users.getOrganization()!=null && users.getOrganization().getStatus()!= OrgStatus.ACTIVE){
            throw new com.coprod.gatekeeper.management.exception.ApiException(HttpStatus.FORBIDDEN,"organisation status does not looks good");
        }


        //now check the issue of the jwt token give all the details

        //this is all copied from built in just change other things

        Instant issuedAt= Instant.now();
        String role= users.getRole().getName();
        Long OrganizationId= users.getOrganization() == null ? null : users.getOrganization().getId();

        JwtClaimsSet.Builder claims= JwtClaimsSet.builder().
                issuer("gatekeeper")
                .issuedAt(issuedAt)
                .expiresAt(issuedAt.plusSeconds(expirationSecond))
                .subject(users.getUsername())
                .claim("role",role);

        //check organisaxtion

        if(OrganizationId !=null){
            claims.claim("organizationId",OrganizationId);
        }


        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        String token = jwtEncoder.encode(
                JwtEncoderParameters.from(header, claims.build())).getTokenValue();

        return new LoginResponse(token,"Bearer",expirationSecond,role,OrganizationId);

    }

    private RuntimeException InvalidCredentials(){
        return new ApiException(HttpStatus.UNAUTHORIZED,"Invalid username or password");
    }
}
