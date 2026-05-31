package com.test.sbdummy.service

import org.springframework.stereotype.Service

@Service
class SecurityService {
    fun getMessage(): String {
        return "Hello from Security Module"
    }
}