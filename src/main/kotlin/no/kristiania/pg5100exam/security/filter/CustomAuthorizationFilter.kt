package no.kristiania.pg5100exam.security.filter

import no.kristiania.pg5100exam.security.jwt.JwtUtil
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_FORBIDDEN

class CustomAuthorizationFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.cookies?.firstOrNull{it.name == "access_token"}?.value
        when {
            token.isNullOrBlank() -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/login") -> filterChain.doFilter(request, response)
            request.servletPath.contains("/api/register") -> filterChain.doFilter(request, response)
            else -> {
                try {
                    val decodedToken = JwtUtil.decodeToken(token)
                    val email = decodedToken.subject
                    val authority =
                        decodedToken.getClaim("authorities").asList(String::class.java).map { SimpleGrantedAuthority(it) }
                    val authenticationToken = UsernamePasswordAuthenticationToken(email, null, authority)
                    SecurityContextHolder.getContext().authentication = authenticationToken
                    filterChain.doFilter(request, response)
                } catch (e: Exception) {
                    logger.error("Authorization error ${e.message}")
                    val error = mapOf("error_message" to e.message)
                    response.contentType = APPLICATION_JSON_VALUE
                    response.status = SC_FORBIDDEN
                    jacksonObjectMapper().writeValue(response.outputStream, error)
                }
            }
        }
    }
}