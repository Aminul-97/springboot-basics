package com.test.sbdummy.service

import com.test.sbdummy.data.User
import com.test.sbdummy.model.Demo
import com.test.sbdummy.repository.DemoRepository
import org.springframework.stereotype.Service

@Service
class DemoService(
    private val demoRepository: DemoRepository
) {
    fun getData(): Any {
        return demoRepository.findAll()
    }

    fun addData(user: User){
        val data = Demo(
            name = user.name,
            email = user.email,
            accountNum = user.accountNum
        )
        demoRepository.save(data)
    }
}