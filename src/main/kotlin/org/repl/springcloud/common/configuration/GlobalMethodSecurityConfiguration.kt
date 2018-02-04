package org.repl.springcloud.common.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
class GlobalMethodSecurityConfiguration : WebSecurityConfigurerAdapter() {

    @Autowired
    @Qualifier("customAuthenticationProvider")
    private val authenticationProvider: AuthenticationProvider? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        //@formatter:off
        http.authorizeRequests()
                .antMatchers(HttpMethod.GET, "/info", "/metrics", "/health", "/env", "/dump", "/trace", "/jolokia/",
                        "/turbine.stream", "/configprops", "/hystrix.stream", "/logfile")
                .permitAll().antMatchers(HttpMethod.POST, "/jolokia/", "/refresh").permitAll()
                .antMatchers(HttpMethod.HEAD, "/refresh", "/logfile").permitAll()
                //.antMatchers(HttpMethod.GET, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers(HttpMethod.POST, "/users/login").permitAll()

                .anyRequest().authenticated().and().httpBasic()

        http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        //@formatter:on
    }

    @Autowired
    @Throws(Exception::class)
    protected fun configureGlobal(auth: AuthenticationManagerBuilder, authenticationProvider: AuthenticationProvider) {
        auth.authenticationProvider(authenticationProvider)
    }

    @Autowired
    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth!!.authenticationProvider(authenticationProvider)
    }
}