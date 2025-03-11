package com.example.SchoolWebApp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HolidaysController {

    @GetMapping("/holidays/{display}")
    public String displayHolidays(@PathVariable String display, Model model){
        if(null != display && display.equals("all")){
            model.addAttribute("festival", true);
            model.addAttribute("federal", true);
        }else if(null != display && display.equals("festival")){
            model.addAttribute("festival",true);
        }else if(null != display && display.equals("federal")){
            model.addAttribute("federal", true);
        }
        return "Holidays.html";
    }
}
