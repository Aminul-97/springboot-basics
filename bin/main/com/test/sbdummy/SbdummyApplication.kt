package com.test.sbdummy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.test.sbdummy", "com.testmodule"])
class SbdummyApplication

fun main(args: Array<String>) {
	runApplication<SbdummyApplication>(*args)
}
