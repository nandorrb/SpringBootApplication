package com.company.use_case;



import com.company.anotation.UseCase;
import com.company.dto.LoginInfoDto;
import com.company.dto.TokenDTO;
import com.company.form.LoginForm;
import com.company.service.AuthService;
import com.company.service.IJWTTokenService;
import com.company.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@UseCase
public class AuthUseCase {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final IJWTTokenService jwtTokenService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthUseCase(AuthenticationManager authenticationManager, AuthService authService,
                       IJWTTokenService jwtTokenService, UserService userService, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    public LoginInfoDto login(LoginForm loginForm) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginForm.getUsername(), loginForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Handle login process
        return authService.login(loginForm.getUsername());
    }

}
