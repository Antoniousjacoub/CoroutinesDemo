package com.linkdev.coroutinesdemo.basic_coroutine_concepts

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/*
Coroutines are light-weight threads.
They are launched with launch coroutine builder in a context of some CoroutineScope.
Here we are launching a new coroutine in the GlobalScope,
meaning that the lifetime of the new coroutine is limited only by the lifetime of the whole application.
*/

/*
You can achieve the same result by replacing
GlobalScope.launch { ... } with thread { ... },
and delay(...) with Thread.sleep(...)
*/

fun main() {
    GlobalScope.launch {
        // launch a new coroutine in background and continue
        delay(1000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(2000L) // block main thread for 2 seconds to keep JVM alive
}

/*fun main() {
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main thread continues here immediately
    runBlocking {     // but this expression blocks the main thread
        delay(2000L)  // ... while we delay for 2 seconds to keep JVM alive
    }
}*/

/*The result is the same, but this code uses only non-blocking delay.
 The main thread invoking runBlocking blocks until the coroutine inside runBlocking completes.
This example can be also rewritten in a more idiomatic way,
 using runBlocking to wrap the execution of the main function:*/

/*fun main() = runBlocking<Unit> { // start main coroutine
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(1000L)
        println("World!")
    }
    println("Hello,") // main coroutine continues here immediately
    delay(2000L)      // delaying for 2 seconds to keep JVM alive
}*/


/*Delaying for a time while another coroutine is working is not a good approach.
Let's explicitly wait (in a non-blocking way) until the background Job that we have launched is complete:
 */

//fun main() = runBlocking {
//    val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
//        delay(1000L)
//        println("World!")
//    }
//    println("Hello,")
//    job.join() // wait until child coroutine completes
//}