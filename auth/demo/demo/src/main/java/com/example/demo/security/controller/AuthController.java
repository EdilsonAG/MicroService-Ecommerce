package com.example.demo.security.controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
import org.springframework.security.oauth2.jwt.JwtException;
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

import com.example.demo.security.domain.excecao.NegocioException;
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

    @Value("${token.redirecionamento.url}")
    private String urlRedirecionamento;

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> registrar(@RequestBody DadosUserDTO.RegisterRequest registerRequest) {

        try {

            authService.registrarUsuario(registerRequest);
            System.out.println("chegou pra registrar");
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email ja cadastrado");
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

            HttpSessionSecurityContextRepository asdf = new HttpSessionSecurityContextRepository();
            asdf.saveContext(securityContext, request, response);
            asdf.toString();
            // System.out.println("HTTPS SESSEION SECURITY CRIADA");
            HttpSession session = request.getSession(false);
            // System.out.println("sessao:" + session.getId());
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
            HttpServletResponse response, @RequestParam String teste) {

        System.out.println("chegou no /callback");
        HttpSession session = request.getSession(false);
        System.out.println("sessao:");
        //System.out.println(session.getId());
        if (session != null) {
            try {
                System.out.println("sessao foi invalidada");
                session.invalidate();
            } catch (IllegalStateException ignored) {
                // já estava invalidated pelo Authorization Server, tudo bem
            }
        }
                System.out.println("chegou no /callback");

        
 String referer = request.getHeader("Referer");
System.out.println(referer);

// estou fazendo essa gambiarra pra ficar dinamico pra teste
//  String origin = request.getHeader("Origin");
  String urlfinal = "";
// System.out.println(origin);

//         if(origin == null){
//             urlfinal = "";
//             urlfinal = "http://localhost:5173/callback";
//             System.out.println(urlfinal);
//         }

        if(teste.equals("A")){
            urlfinal = "http://localhost:5173/callback";
        }

        if(teste.equals("B")){
            urlfinal = "https://altasscookies.bytefire.com.br/callback";
        }

        // Troca o code pelo token chamando o Authorization Server
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("code", code);
        // params.add("redirect_uri", "http://localhost:5173/AltassCookies/callback");
        // params.add("redirect_uri",
        // "https://edilsonag.github.io/AltassCookies/callback");
        params.add("redirect_uri", urlfinal);
        params.add("code_verifier", codeVerifier);
        params.add("client_id", "web");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setBasicAuth("web", "web1234");

        System.out.println("antes de fazer o post para pegar o token");
        System.out.println("também temos o code");
        System.out.println(code);
        System.out.println("e também o codeverifier");
        System.out.println(codeVerifier);
        ResponseEntity<Map> tokenResponse = restTemplate.postForEntity(
                // "http://localhost:8080/oauth2/token",
                // "https://api.bytefire.com.br/oauth2/token",
                "http://localhost:8080/oauth2/token",
                new HttpEntity<>(params, headers),
                Map.class);

        System.out.println("DEPOIS de fazer o post para pegar o token");
        String accessToken = (String) tokenResponse.getBody().get("access_token");
        String refreshToken = (String) tokenResponse.getBody().get("refresh_token");

        System.out.println("RefreshTOKEN \n\n\n");
        System.out.println(refreshToken);
        HttpSession session2 = request.getSession(true);
        session2.setAttribute("access_token", accessToken);
        session2.setAttribute("refresh_token", refreshToken);

        System.out.println("nova sessao criada");
        System.out.println(session2.getId());
        System.out.println(session2.getAttributeNames());
        System.out.println(session2.getServletContext().getContextPath());
        System.out.println(session2.getServletContext().getServerInfo());
        // session.setAttribute("refresh_token", refreshToken);
        session2.setMaxInactiveInterval(299);

        String token = (String) session2.getAttribute("access_token");

        System.out.println(token);
        Cookie co[] = request.getCookies();

        Collections.list(session2.getAttributeNames())
                .forEach(name -> System.out.println(
                        name + " = " +
                                session2.getAttribute(name)));

        // ==============é oque funciona====================
        // ResponseCookie cookie = ResponseCookie.from("access_token", accessToken)
        // .httpOnly(true)
        // .secure(true) // true em produção
        // .path("/")
        // .maxAge(299)
        // .sameSite("Lax") // ← isso resolve o campo vazio
        // .build();

        // response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        // =================================

        return ResponseEntity.ok().build();
    }

    // provavelmente auqi vou implemnetar um refresh token
    // @GetMapping("/validate")
    // public ResponseEntity<?> validate(HttpServletRequest request) {
    // // pega o cookie access_token
    // Cookie[] cookies = request.getCookies();
    // if (cookies == null)
    // return ResponseEntity.status(401).build();

    // String token = Arrays.stream(cookies)
    // .filter(c -> c.getName().equals("access_token"))
    // .map(Cookie::getValue)
    // .findFirst()
    // .orElse(null);

    // if (token == null)
    // return ResponseEntity.status(401).build();

    // try {
    // jwtDecoder.decode(token); // já existe no seu AuthorizationServerConfigJWT
    // return ResponseEntity.ok().build();
    // } catch (Exception e) {
    // return ResponseEntity.status(401).build();
    // }
    // }

    // @GetMapping("/validate")
    // public ResponseEntity<?> validate(HttpServletRequest request) {
    // HttpSession session = request.getSession(false);
    // if (session == null)
    // return ResponseEntity.status(401).build();

    // String token = (String) session.getAttribute("access_token");
    // String refresh = (String) session.getAttribute("refresh_token");
    // if (token == null)
    // return ResponseEntity.status(401).build();

    // try {

    // jwtDecoder.decode(token);
    // return ResponseEntity.ok()
    // .header("X-Access-Token", token)
    // .build();
    // } catch (Exception e) {
    // return ResponseEntity.status(401).build();
    // }
    // }

    @GetMapping("/validate")
    public ResponseEntity<?> validate(HttpServletRequest request) {
        System.out.println("chegou pra validar");

        HttpSession session = request.getSession(false);
        // System.out.println(session.getId());
        if (session == null){
            System.out.println("aqui não vai dar certo");
            return ResponseEntity.status(401).build();
        }
        System.out.println("De fato a uma sessao");
        String token = (String) session.getAttribute("access_token");
        if (token == null){
                        System.out.println("a uma sessao porem esta sem o token");

            return ResponseEntity.status(401).build();
        }
        try {

            System.out.println("antes de validar");
            jwtDecoder.decode(token); // válido, retorna normalmente
            System.out.println("token valido dentro try\n\n\n");
            System.out.println(token);
            System.out.println("\n\n\n");
            return ResponseEntity.ok()
                    .header("X-Access-Token", token)
                    .build();
        } catch (JwtException e) {
            // token expirado → tenta refresh
            String refreshToken = (String) session.getAttribute("refresh_token");
            System.out.println("refreshtoken\n\n\n");
            System.out.println(refreshToken);
            if (refreshToken == null)
                return ResponseEntity.status(401).build();

            try {
                // chama o Authorization Server para renovar
                RestTemplate restTemplate = new RestTemplate();

                MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
                params.add("grant_type", "refresh_token");
                params.add("refresh_token", refreshToken);
                params.add("client_id", "web");

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
                headers.setBasicAuth("web", "web1234");

                ResponseEntity<Map> response = restTemplate.postForEntity(
                        "http://localhost:8080/oauth2/token",
                        new HttpEntity<>(params, headers),
                        Map.class);

                String newAccessToken = (String) response.getBody().get("access_token");
                String newRefreshToken = (String) response.getBody().get("refresh_token");

                System.out.println("new accestoken\n\n");
                System.out.println(newAccessToken);
                System.out.println("newRefreshToken\n\n");
                System.out.println(newRefreshToken);

                session.setAttribute("access_token", newAccessToken);
                session.setAttribute("refresh_token", newRefreshToken);

                return ResponseEntity.ok()
                        .header("X-Access-Token", newAccessToken)
                        .build();
            } catch (Exception ex) {
                return ResponseEntity.status(401).build();
            }
        }
    }

    @GetMapping("/session")
    public ResponseEntity<?> session(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null)
            return ResponseEntity.status(401).body("sem sessão");

        Object ctx = session.getAttribute("SPRING_SECURITY_CONTEXT");
        return ResponseEntity.ok(ctx.toString());
    }
}
