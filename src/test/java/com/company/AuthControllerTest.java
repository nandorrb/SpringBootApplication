package com.company;

import com.company.controller.AuthController;
import com.company.dto.LoginInfoDto;
import com.company.form.LoginForm;

import com.company.service.IJWTTokenService;
import com.company.use_case.AuthUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUseCase authUseCase;

    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private IJWTTokenService jwtTokenService;

    private LoginForm loginForm;
    private LoginInfoDto loginInfoDto;

    @BeforeEach
    void setUp() {
        loginForm = new LoginForm();
        loginForm.setUsername("dangblack");
        loginForm.setPassword("123456");

        loginInfoDto = new LoginInfoDto();
        loginInfoDto.setFullname("dangblack");

    }

    @Test
    @WithMockUser
    void login_ShouldReturnLoginInfoDto() throws Exception {
        Authentication auth = Mockito.mock(Authentication.class);
        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(auth);

        Mockito.when(authUseCase.login(any(LoginForm.class))).thenReturn(loginInfoDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"dangblack\", \"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullname").value("dangblack"));
    }
}
