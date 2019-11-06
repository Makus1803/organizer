package com.luv2code.springdemo.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/hello")
@Controller
public class HelloWorldController {

    //controller method to show form

    @RequestMapping("/showForm")
    public String showForm(){
        return "helloworld-form";
    }


    //controller to process the html form
    @RequestMapping("/processForm")
    public String processForm(){
        return "helloworld";
    }


    //new controller method to  read form data and
    //add data to the model
    @RequestMapping("/processFormVersionTwo")
    public String letsShoutDude(HttpServletRequest request, Model model){

        //read the request parameter from the HTML
        String theName = request.getParameter("studentName");
        //conver the data to all caps
        theName = theName.toUpperCase();
        //create the message
        String result = "Yo! " + theName;
        //add message to the mode
        model.addAttribute("message", result);

        return "helloworld";
    }

    @RequestMapping("/processFormVersionThree")
    public String processFormVersionThree(
            @RequestParam("studentName") String theName,
            Model model){

        //conver the data to all caps
        theName = theName.toUpperCase();
        //create the message
        String result = "Hello " + theName;
        //add message to the mode
        model.addAttribute("message", result);

        return "helloworld";
    }


}
