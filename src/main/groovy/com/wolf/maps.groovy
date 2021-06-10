def aa = [:]
aa.computeIfAbsent('a') { new HashSet() }.add(1)
aa.computeIfAbsent('a') { new HashSet() }.add(2)
aa.computeIfAbsent('b') { new HashSet() }.add(1)
println aa

// 返回的是一个包装map，当get返回空时调用closure并存储，然后返回其value
def bb = [:].withDefault { k -> new HashSet() }
println bb.get('a')
bb.get('a').add(1)
bb.get('a').add(2)
bb.get('b').add(1)
println bb

// dot使用
def map = [a: 1, b: 2]
assert [a: 1, b: 2, c: 3] == [c: 3, *: map]
