package hac.ex4.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
  This controller handles all exceptions/errors and displays a friendly
  error page.
 */
@Controller
public class CustomErrorController implements ErrorController {

    /**
     * Return error page.
     * @param error - The error message.
     * @return - error page.
     */
    @RequestMapping("/error")
    public String handleError(String error) {
        System.out.println(error);
        return "errors/error";
    }

}