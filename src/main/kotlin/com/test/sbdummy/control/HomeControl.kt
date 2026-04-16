package com.test.sbdummy.control

import com.test.sbdummy.data.User
import com.test.sbdummy.service.DemoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class HomeControl(
    private val demoService: DemoService,
) {

    @GetMapping("/")
    fun home(model: Model): String {
        model.addAttribute("title", "Hello World")
        return "index"
    }

    @GetMapping("/home")
    fun hello(): String {
        return "Hello World!"
    }

    @GetMapping("/users")
    fun users(model: Model): Any {
        println(demoService.getData())
        model.addAttribute("users", demoService.getData())
        return "users"
    }

    @GetMapping("/add-users")
    fun addusers(
        model: Model,
    ): String {
        model.addAttribute("userForm", User())
        return "add-user"
    }

    @PostMapping("/submit")
    fun addUsers(
        @ModelAttribute userForm: User,
    ): String {
        demoService.addData(userForm)
        return "redirect:/success"
    }

    @GetMapping("/success")
    fun success(): String = "success"
}