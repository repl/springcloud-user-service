package org.repl.springcloud.common.security

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter


class CustomUserAuthenticationConverter : DefaultUserAuthenticationConverter() {
    override fun extractAuthentication(map: Map<String, *>): Authentication {
        val user = UserDetails(map["uid"] as String, map["user_name"] as String, map["client_id"] as String)

        return UsernamePasswordAuthenticationToken(user, "N/A",
                super.extractAuthentication(map).authorities)
    }
}