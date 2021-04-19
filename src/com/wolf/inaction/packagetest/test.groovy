import com.wolf.inaction.packagetest.business.*
import com.wolf.inaction.packagetest.thirdparty.MathLib
import com.wolf.inaction.packagetest.thirdparty.MathLib as OrigMathLib
import com.wolf.inaction.packagetest.thirdparty.MathLib as TwiceHalfMathLib
import com.wolf.inaction.packagetest.thirdparty2.MathLib as IncMathLib

def canoo = new Vendor1()
canoo.name = 'Canoo Engineering AG'
canoo.product = 'UltraLightClient(ULC)'
assert canoo.dump() =~ /ULC/

// 有些已经存在的代码使用thirdparty的库
//assert 15 == new MathLib().twice(5)

// 使用类别名重命名原始类，然后使用继承来修复它，不能修改原来已经使用的代码
// using import as for local library modifications
class MathLib extends OrigMathLib {
    Integer twice(Integer value) {
        return value * 2
    }
}

// nothing changes below here
def mathlib = new MathLib()// usage code for library remains unchanged
assert 10 == mathlib.twice(5)
assert 2 == mathlib.half(5)

// using import as for avoiding name clashes
def math1 = new TwiceHalfMathLib()
def math2 = new IncMathLib()
assert 3 == math1.half(math2.increment(5))


