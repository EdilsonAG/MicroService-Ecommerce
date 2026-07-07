package com.example.demo.security;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jose.jwk.RSAKey;
import com.example.demo.security.customclaim.JwtCustomClaimsToken;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;

/*
    PARA TESTE
    http://localhost:8080/oauth2/authorize?response_type=code&client_id=web&state=abc&redirect_uri=https://oauth.pstmn.io/v1/callback&scope=write&code_challenge=sZu8qJLr6w9AMOgnOadOGInRavwidXBsofO-44bxvXw&code_challenge_method=S256

*/

@Configuration
public class AuthorizationServerConfigJWT {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)

                .oidc(Customizer.withDefaults()); // Habilita OpenID Connect

        return http.formLogin(Customizer.withDefaults()).build();
    }

     @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomClaimsToken() {
        return new JwtCustomClaimsToken();
    }

    @Bean
    public OAuth2AuthorizationService authorizationService(
            JdbcTemplate jdbcTemplate,
            RegisteredClientRepository registeredClientRepository) {

        return new JdbcOAuth2AuthorizationService(
                jdbcTemplate,
                registeredClientRepository);
    }

    @Bean
    public RegisteredClientRepository registeredClientRepositorye(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return new JdbcRegisteredClientRepository(jdbcTemplate);

    }

    @Bean
    CommandLineRunner teste(RegisteredClientRepository repo, PasswordEncoder passwordEncoder) {
        CommandLineRunner commandLineRunner = new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {
                if (repo.findByClientId("web") == null) {

                    RegisteredClient web = RegisteredClient.withId(UUID.randomUUID().toString())
                            .clientId("web")
                            .clientSecret(passwordEncoder.encode("web1234"))
                            .clientAuthenticationMethod(
                                    ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                            .authorizationGrantType(
                                    AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                            .redirectUri("https://oauth.pstmn.io/v1/callback")
                            .redirectUri("http://localhost:5173/callback")
                            .scope("read")
                            .scope("write")
                            .tokenSettings(TokenSettings.builder()
                                    .accessTokenTimeToLive(Duration.ofMinutes(10))
                                    .refreshTokenTimeToLive(Duration.ofDays(1))
                                    .reuseRefreshTokens(false)
                                    .build())
                            .clientSettings(ClientSettings.builder()
                                    .requireAuthorizationConsent(false)
                                    .requireProofKey(true)
                                    .build())
                            .build();

                    repo.save(web);
                }
            }
        };

        return commandLineRunner;

    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {

        char[] keyStorePassword = "123456".toCharArray();
        String keyAlias = "algafood";

        KeyStore keyStore;
        try {
            keyStore = KeyStore.getInstance("JKS");
            try (InputStream is = new ClassPathResource("keys/algafood.jks").getInputStream()) {
                keyStore.load(is, keyStorePassword);
            }
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        RSAKey rsaKey;
        try {
            RSAPublicKey publicKey = (RSAPublicKey) keyStore.getCertificate(keyAlias).getPublicKey();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyStore.getKey(keyAlias, keyStorePassword);

            rsaKey = new RSAKey.Builder(publicKey)
                    .privateKey(privateKey)
                    .keyID(UUID.randomUUID().toString())
                    .build();
        } catch (Exception ex) {
            throw new IllegalStateException(ex);
        }

        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
}
