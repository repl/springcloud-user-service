package org.repl.springcloud.common.security

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.AuthenticationServiceException
import org.repl.springcloud.common.exception.ServiceException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.authentication.BadCredentialsException
import org.repl.springcloud.user.dto.UserDto
import org.repl.springcloud.user.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider

import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

@Primary
@Component("customAuthenticationProvider")
class CustomAuthenticationProvider : AuthenticationProvider {

    @Autowired
    private val userService: UserService? = null

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val username = authentication.getName().trim()
        val password = authentication.getCredentials().toString().trim()
        val user: UserDto?
        try {
            user = userService!!.findUserByUserHandle(username)
            if (user == null || !user.emailAddress!!.equals(username, ignoreCase = true)) {
                throw BadCredentialsException("Unable to find user with specified userHandle - " + username)
            }
            val passwordInDb = userService!!.getPassword(user)
            if (!userService!!.matchPassword(password, passwordInDb)) {
                throw BadCredentialsException("Wrong password.")
            }
            val authorities = user.roles!!.map { role -> SimpleGrantedAuthority("ROLE_" + role) }
            return UsernamePasswordAuthenticationToken(UserDetails(user.id!!, user.userHandle!!),
                    password, authorities)
        } catch (e: ServiceException) {
            logger.error("User Authentication Failed due to ServiceException", e)
            throw AuthenticationServiceException("User authentication failed.")
        } catch (e: AuthenticationException) {
            logger.info("User Authentication Exception", e)
            throw e
        } catch (e: Exception) {
            logger.error("User Authentication Fatal Error", e)
            throw e
        }
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(CustomAuthenticationProvider::class.java)
    }
}
