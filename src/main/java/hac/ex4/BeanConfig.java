package hac.ex4;

import hac.ex4.beans.BasketList;
import hac.ex4.listeners.SessionListenerCounter;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.context.annotation.SessionScope;

@Configuration
//@EnableGlobalMethodSecurity
public class BeanConfig {

    @Bean
    public ServletListenerRegistrationBean<SessionListenerCounter> sessionListenerWithMetrics() {
        ServletListenerRegistrationBean<SessionListenerCounter> listenerRegBean = new ServletListenerRegistrationBean<>();

        listenerRegBean.setListener(new SessionListenerCounter());
        return listenerRegBean;
    }

    @Bean
    @SessionScope
    //@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public BasketList basketBean() {
        return new BasketList();
    }

//    @Bean
//    @ApplicationScope
//    public BasketList basketBeanApplication () {
//        return new BasketList();
//    }
}
