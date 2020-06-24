package com.tommy
// Closure
def c = {
    println "hello tommy"
}
// method
def method(Closure closure) {
    closure()
}

method(c)

