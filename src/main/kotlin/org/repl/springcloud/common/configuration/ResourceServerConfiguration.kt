package org.repl.springcloud.common.configuration

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.web.util.matcher.RequestMatcher
import javax.servlet.http.HttpServletRequest

@Configuration
@EnableResourceServer
class ResourceServerConfiguration : ResourceServerConfigurerAdapter() {
    internal var log = LoggerFactory.getLogger(ResourceServerConfiguration::class.java)

    private val RESOURCE_ID = "all-myproduct-resources"

    @Autowired
    internal var jwtAccessTokenConverter: JwtAccessTokenConverter? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.requestMatcher(OAuthRequestedMatcher())
                .authorizeRequests()
                .anyRequest().authenticated()
    }

     private class OAuthRequestedMatcher : RequestMatcher {
         override fun matches(request: HttpServletRequest): Boolean {
            val auth = request.getHeader("Authorization")
            // Determine if the client request contained an OAuth Authorization
            return auth != null && auth.toLowerCase().startsWith("bearer")
        }
    }

    @Throws(Exception::class)
    override fun configure(resources: ResourceServerSecurityConfigurer?) {
        log.info("Configuring ResourceServerSecurityConfigurer ")
        resources!!.resourceId(RESOURCE_ID).tokenStore(JwtTokenStore(jwtAccessTokenConverter))
    }
}