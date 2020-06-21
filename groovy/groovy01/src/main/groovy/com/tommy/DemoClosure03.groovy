package com.tommy
// Closure
def c = {
    k, v ->
        print "hello ${k} : ${v}"

}
// method
def method(Closure closure, para1, para2) {
    closure(para1, para2)
}

method(c, 'tommy', 'TT')

