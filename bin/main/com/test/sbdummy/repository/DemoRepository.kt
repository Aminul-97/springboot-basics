package com.test.sbdummy.repository

import com.test.sbdummy.model.Demo
import org.springframework.data.jpa.repository.JpaRepository

interface DemoRepository : JpaRepository<Demo, Int>