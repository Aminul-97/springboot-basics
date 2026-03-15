package com.test.sbdummy.control

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class HomeControl {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("title", "Hello World")
        return "index"
    }

    @GetMapping("/home")
    fun hello(): String {
        return "Hello World!"
    }
}