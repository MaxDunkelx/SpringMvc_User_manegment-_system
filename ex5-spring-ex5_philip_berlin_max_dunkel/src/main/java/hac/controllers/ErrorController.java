package hac.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;


/**
 * handle error requests.
 */
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {

    @GetMapping("/error")
    public String handleError(Model model, HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorMessage", "the page requested does not exist");
            }
            else if(statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorMessage", "Access Denied. You do not have permisions");
            }
        }
        return "error";
    }

}
