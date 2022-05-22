package hac.ex4.controllers;

import hac.ex4.beans.Basket;
import hac.ex4.beans.Messages;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SpringSessionController {

    // check the corresponding code in BeanConfiguration.java
    @Resource(name="autowiredFieldSingletonScope")
    private Basket singletonBasket;

    // injection by ctor: match by name
    @Resource(name = "sessionBeanExample")
    private Messages sessionMessages;

    // injection of component by ctor : match by name
    // check the corresponding code in Label.java
    @Resource(name="autowiredBasketDependency")
    private Basket basket;

    // injection by ctor : match by qualifier (variable name)
    // @Autowired ONLY does not work since there are 4 possibilities
    // check the corresponding code in BeanConfiguration.java
    @Resource(name="autowiredFieldApplicationScope")
    private Basket applicationBasket;

    // injecting session scope bean
    // check the corresponding code in BeanConfiguration.java
    @Resource(name="sessionScopeBeanExample")
    private Basket sessionBasket;

    @GetMapping("/session")
    public String process(Model model) {
        model.addAttribute("sessionMessages", sessionMessages.getMessages());

        model.addAttribute("mybasket", basket);
        model.addAttribute("singletonBasket", singletonBasket);
        model.addAttribute("applicationBasket", applicationBasket);
        return "session";
    }

    /* without SPRING injection  it would look like this:
    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg, HttpServletRequest request) {
        List<String> messages = (List<String>) request.getSession().getAttribute("MY_SESSION_MESSAGES");
        if (messages == null) {
            messages = new ArrayList<>();
            request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        }
        messages.add(msg);
        request.getSession().setAttribute("MY_SESSION_MESSAGES", messages);
        return "redirect:/session";
    }
    */

    @PostMapping("/persistMessage")
    public String persistMessage(@RequestParam("msg") String msg) {

        sessionMessages.add(msg);
        return "redirect:/session";
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/session";
    }
}