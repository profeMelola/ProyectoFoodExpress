package es.daw.foodexpressmvc.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class DashboardController {

    @GetMapping("/home")
    public String showDashboard(Model model, Principal principal){
        //registry.addViewController("/home").setViewName("dashboard");
        if (principal != null){
            model.addAttribute("username", principal.getName());
        }
        return "dashboard";
    }


}
