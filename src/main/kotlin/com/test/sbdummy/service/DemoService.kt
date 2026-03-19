package com.test.sbdummy.service

import com.test.sbdummy.repository.DemoRepository

class DemoService(
    private val demoRepository: DemoRepository
) {
    fun getData(Id: Int){
        demoRepository.findById(Id)
    }
}