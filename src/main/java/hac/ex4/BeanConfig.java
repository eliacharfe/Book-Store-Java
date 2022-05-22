package hac.ex4;

import hac.ex4.beans.Basket;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * create some beans witn various scopes using QUALIFIERS (method names)
 */
@Configuration
public class BeanConfig {

    /* singleton scope */
    @Bean
    @Scope("singleton")
    public Basket autowiredFieldSingletonScope() {
        Basket basket =  new Basket();
        basket.setBasket("I'm SINGLETON Basket bean");
        return basket;
    }

    /* application scope */
    @Bean
    @ApplicationScope
    public Basket autowiredFieldApplicationScope() {
        Basket basket =  new Basket();
        basket.setBasket("I'm APPLICATION Basket bean");
        return basket;
    }

    /* request scope */
    @Bean
    @RequestScope
    public Basket autowiredFieldRequestScope() {
        Basket basket =  new Basket();
        basket.setBasket("I'm REQUEST Basket bean");
        return basket;
    }

    /* session scope
    request.getSession().addAttribute ("sessionScopeBeanExample",new Label())
    */
    @Bean
    @SessionScope
    //@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Basket sessionScopeBeanExample () {
        Basket basket =  new Basket();
        basket.setBasket("I'm SESSION Basket bean");
        return basket;
    }
}
