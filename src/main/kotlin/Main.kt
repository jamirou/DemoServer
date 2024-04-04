package com.example

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
fun Application.module() {
    routing {
        //Way 1
        get("/") {
            call.respondText ("Hello, World!")
        }
        //Way 2
        get("/2") {
            call.respondText ("This is the path num 2, As you can see, this is another way to add a new path")
        }
    }
}

@Suppress("unused")
fun Application.module2() {
    //Way 2 for adding new path
    routing {
        get("/3") {
            call.respondText ("Hello, Android!")
        }
    }
}

//@Suppress("unused")
//fun Application.module3() {
//    //Way 4 Adding new path
//    install(CallLogging) {
//        routing {
//            get("/book") {
//                call.respondText ("Hello, Android!")
//            }
//        }
//    }
//}