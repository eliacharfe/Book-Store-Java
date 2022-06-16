package hac.ex4;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configure the application
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    /**
     * Configure type of users (usernames + passwords).
     * @param auth - AuthenticationManagerBuilder
     * @throws Exception - The exception thrown in case of an error.
     */
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("admin").password(encoder.encode("password")).roles("ADMIN")
                .and()
                .withUser("u1").password(encoder.encode("p")).roles("USER")
                .and()
                .withUser("u2").password(encoder.encode("p")).roles("USER")
                .and()
                .withUser("u3").password(encoder.encode("p")).roles("USER");
    }

    /**
     * Configure security in app.
     * @param http - HttpSecurity.
     * @throws Exception - The exception thrown in case of an error.
     */
    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/errors/403.html");
    }
}



//.loginPage("/login") // <=============== uncomment this for a custom login page (see also the controller)
//.loginProcessingUrl("/login")
//                .defaultSuccessUrl("/admin", true)
//.failureUrl("/login-error") // <===============  uncomment this for a custom login page (see also the controller)

//                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/purchaseitem", "/purchase-all-shopping-basket").hasAuthority("USER")
//                .antMatchers("/**").permitAll()