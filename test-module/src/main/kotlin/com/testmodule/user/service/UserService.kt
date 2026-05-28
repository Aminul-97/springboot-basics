package com.testmodule.user.service

import org.springframework.stereotype.Service

@Service
class UserService {

    fun getMessage(): String {
        return "Hello from User Module"
    }
}