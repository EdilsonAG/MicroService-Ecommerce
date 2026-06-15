package com.example.demo.security.controller;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.MediaType;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.security.domain.model.User;
import com.example.demo.security.domain.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private AuthService authService;


    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody DadosUserDTO.RegisterRequest registerRequest){
       
        try {
            authService.registrarUsuario(registerRequest);
            System.out.println("chegou pra registrar");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            throw new IllegalAccessError();
        }
        
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            System.out.println("CHEGOU NO LOGIN");
            // 1. Autentica as credenciais
            UsernamePasswordAuthenticationToken userAndPass = new UsernamePasswordAuthenticationToken(
                    loginRequest.email(),
                    loginRequest.password());
                System.out.println(userAndPass);
            Authentication authentication = authenticationManager.authenticate(userAndPass);
        
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);

            new HttpSessionSecurityContextRepository()
                    .saveContext(securityContext, request, response);
           

            // System.out.println("HTTPS SESSEION SECURITY CRIADA");

            return ResponseEntity.ok().build();

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Email ou senha incorretos");
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<?> callback(
            @RequestParam String code,
            @RequestParam String codeVerifier, HttpServletRequest request,
            HttpServletResponse response) {

        HttpSession session = request.getSession(false);
        if (session != null) {
            try {
                session.invalidate();
            } catch (IllegalStateException ignored) {
                // já estava invalidated pelo Authorization Server, tudo bem
            }
        }

        // Troca o code pelo token chamando o Authorization Server
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        // params.add("redirect_uri", "http://localhost:5173/AltassCookies/callback");
        // params.add("redirect_uri",
        // "https://edilsonag.github.io/AltassCookies/callback");
        params.add("redirect_uri", "https://oauth.pstmn.io/v1/callback");
        params.add("code_verifier", codeVerifier);
        params.add("client_id", "web");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("web", "web1234");

        System.out.println("antes de fazer o post para pegar o token");
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                // "http://localhost:8080/oauth2/token",
                // "https://api.bytefire.com.br/oauth2/token",
                "http://localhost:8080/oauth2/token",
                new HttpEntity<>(params, headers),
                Map.class);

        System.out.println("DEPOIS de fazer o post para pegar o token");
        String accessToken = (String) tokenResponse.getBody().get("access_token");

        // Seta o cookie HttpOnly — JS não consegue ler!

        // altass.cookies.cookie.secure
        ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
                .httpOnly(true)
                .secure(true) // true em produção
                .path("/")
                .maxAge(299)
                .sameSite("Lax") // ← isso resolve o campo vazio
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // HttpSession session = request.getSession(true);
        // session.setAttribute("access_token", accessToken);

        // ResponseCookie clearSession = ResponseCookie.from("ECCOMERCESESSION", "")
        // .httpOnly(true)
        // .secure(true)
        // .path("/")
        // .maxAge(0) // expira imediatamente
        // .sameSite("Lax")
        // .build();
        // response.addHeader(HttpHeaders.SET_COOKIE, clearSession.toString());

        return ResponseEntity.ok().build();
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request) {
        // pega o cookie access_token
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return ResponseEntity.status(401).build();

        String token = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("access_token"))
                .map(Cookie::getValue)
                .findFirst()
                .orElse(null);

        if (token == null)
            return ResponseEntity.status(401).build();

        try {
            jwtDecoder.decode(token); // já existe no seu AuthorizationServerConfigJWT
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

    @GetMapping("/session")
public ResponseEntity<?> session(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null) return ResponseEntity.status(401).body("sem sessão");
    
    Object ctx = session.getAttribute("SPRING_SECURITY_CONTEXT");
    return ResponseEntity.ok(ctx.toString());
}
}
