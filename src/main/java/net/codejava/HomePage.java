package net.codejava;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
public class HomePage {

    ///////////// you start running the program from here after you deploy the server//////////////////
    ///////////// just copy "localhost:8080/homepage" into your browser to navigate ///////////////////
    ///////////// to my webpage, you can view the results from there. enjoy ////////////////////////////

    @RequestMapping("/homepage")
    public String homePage() {
        return "homepage";

    }
}
