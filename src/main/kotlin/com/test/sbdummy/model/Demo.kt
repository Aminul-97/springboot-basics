package com.test.sbdummy.model

import jakarta.persistence.*

@Entity
@Table(name = "demo")
class Demo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String,
    val email: String,
    val accountNum: String
)