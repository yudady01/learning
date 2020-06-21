package com.tommy
// Closure
def c = {
    print "hello tommy"
}
// method
def method(Closure closure) {
    closure()
}

method(c)

