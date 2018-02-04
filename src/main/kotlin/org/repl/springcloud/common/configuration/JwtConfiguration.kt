package org.repl.springcloud.common.configuration

import org.repl.springcloud.common.security.CustomUserAuthenticationConverter

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter


@Configuration
class JwtConfiguration {
    @Value("\${jwt.signing.key}")
    private val signingKey: String? = null

    @Bean
    protected fun jwtTokenEnhancer(): JwtAccessTokenConverter {
        val jwtTokenConverter = JwtAccessTokenConverter()
        val defaultAccessTokenConverter = DefaultAccessTokenConverter()
        defaultAccessTokenConverter.setUserTokenConverter(CustomUserAuthenticationConverter())
        jwtTokenConverter.accessTokenConverter = defaultAccessTokenConverter
        jwtTokenConverter.setSigningKey(signingKey)
        return jwtTokenConverter
    }
}
