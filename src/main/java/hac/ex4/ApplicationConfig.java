package hac.ex4;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        auth.inMemoryAuthentication()
                .withUser("a").password(encoder.encode("p")).roles("ADMIN");
//                .and()
//                .withUser("user1").password(encoder.encode("user")).roles("USER")
//                .and()
//                .withUser("user2").password(encoder.encode("user")).roles("USER")
//                .and()
//                .withUser("user3").password(encoder.encode("user")).roles("USER");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .formLogin()
                //.loginPage("/login") // <=============== uncomment this for a custom login page (see also the controller)
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/admin", true)
                //.failureUrl("/login-error") // <===============  uncomment this for a custom login page (see also the controller)
                .and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
//                .antMatchers("/user/**").hasRole("USER")
                // custom error page for exceptions
                .and()
                .exceptionHandling()
                .accessDeniedPage("/403.html");
    }

}



//package hac.ex4;
//
////import hac.ex4.beans.Label;
//import hac.ex4.beans.Label;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Scope;
//import org.springframework.web.context.annotation.ApplicationScope;
//import org.springframework.web.context.annotation.RequestScope;
//import org.springframework.web.context.annotation.SessionScope;
//
///**
// * create some beans witn various scopes using QUALIFIERS (method names)
// */
//@Configuration
//public class BeanConfiguration {
//
//    /* singleton scope */
//    @Bean
//    @Scope("singleton")
//    public Label autowiredFieldSingletonScope() {
//        Label l =  new Label();
//        l.setLabel("I'm SINGLETON Label bean");
//        return l;
//    }
//
//    /* application scope */
//    @Bean
//    @ApplicationScope
//    public Label autowiredFieldApplicationScope() {
//        Label l =  new Label();
//        l.setLabel("I'm APPLICATION Label bean");
//        return l;
//    }
//
//    /* request scope */
//    @Bean
//    @RequestScope
//    public Label autowiredFieldRequestScope() {
//        Label l =  new Label();
//        l.setLabel("I'm REQUEST Label bean");
//        return l;
//    }
//
//    /* session scope
//    request.getSession().addAttribute ("sessionScopeBeanExample",new Label())
//    */
//    @Bean
//    @SessionScope
//    //@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
//    public Label sessionScopeBeanExample () {
//        Label l =  new Label();
//        l.setLabel("I'm SESSION Label bean");
//        return l;
//    }
//}
