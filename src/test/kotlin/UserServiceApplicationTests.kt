package org.repl.springcloud

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(UserServiceApplication::class))
class UserServiceApplicationTests {

    @Test
    fun contextLoads() {
    }

}