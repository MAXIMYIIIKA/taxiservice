package by.nichipor.taxiservice.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

/**
 * Created by Max Nichipor on 08.08.2016.
 */

@EnableWebSecurity
@ComponentScan("by.nichipor.taxiservice")
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/resources/**", "/register*", "/main").permitAll()
                    .antMatchers("/admin**/**").access("hasRole('ROLE_ADMIN')")
                    .antMatchers("/user", "/order").access("hasRole('ROLE_USER')")
                    .antMatchers("/dispatch", "/dispatcher").access("hasRole('ROLE_DISPATCHER')")
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .and()
                .logout().logoutUrl("/logout")
                    .permitAll()
                    .and()
                .exceptionHandling().accessDeniedPage("/403");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(new BCryptPasswordEncoder())
                .usersByUsernameQuery(
                        "select username,password, enabled from users where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");
    }

}
