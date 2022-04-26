package no.kristiania.pg5100exam.security

import no.kristiania.pg5100exam.security.filter.CustomAuthenticationFilter
import no.kristiania.pg5100exam.security.filter.CustomAuthorizationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@Configuration
class SecurityConfig(
    @Autowired private val userDetailService: UserDetailsService,
    @Autowired private val passwordEncoder: BCryptPasswordEncoder
): WebSecurityConfigurerAdapter() {

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder)
    }

    override fun configure(http: HttpSecurity) {
        val authenticationFilter = CustomAuthenticationFilter(authenticationManagerBean())
        authenticationFilter.setFilterProcessesUrl("/api/authentication")
        http.csrf().disable()
        http.sessionManagement().disable()
        http.authorizeRequests()
            .antMatchers("/api/authentication").permitAll()
            .antMatchers("/api/user/new").permitAll()
            .antMatchers("/api/user/all").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/user/auth/all").hasAnyAuthority("ADMIN")
            .antMatchers("/api/shelter/all").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/shelter/animal").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/shelter/new").hasAnyAuthority("ADMIN", "USER")
            .antMatchers("/api/shelter/update").hasAuthority("ADMIN")
            .antMatchers("/api/shelter/delete").hasAuthority("ADMIN")
            .antMatchers("/api/shelter/breed/all").hasAuthority("ADMIN")
            .antMatchers("/api/shelter/type/all").hasAuthority("ADMIN")
        http.authorizeRequests().anyRequest().authenticated()
        http.addFilter(authenticationFilter)
        http.addFilterBefore(CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter::class.java)
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager {
        return super.authenticationManagerBean()
    }
}