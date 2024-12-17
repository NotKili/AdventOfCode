from aoc_utils_notkili.aoc_utils import *

from z3 import *

sys.setrecursionlimit(10 ** 6)

s1 = fetch().split("\n")
ans1 = 0

p = nums(s1[4])

def sim(a, b, c):
    _a = a
    _b = b
    _c = c
    _ip = 0

    def opr_val(opr, lit=False):
        if lit:
            return opr

        if 0 <= opr <= 3:
            return opr

        if opr == 4:
            return _a

        if opr == 5:
            return _b

        if opr == 6:
            return _c

    while _ip < len(p):
        opc = p[_ip]
        opr = p[_ip + 1]

        match opc:
            case 0:
                _a = _a >> opr_val(opr)
            case 1:
                _b = _b ^ opr_val(opr, True)
            case 2:
                _b = opr_val(opr) & 7
            case 3:
                if _a != 0:
                    _ip = opr_val(opr, True)
            case 4:
                _b = _b ^ _c
            case 5:
                yield opr_val(opr) & 7
            case 6:
                _b = _a >> opr_val(opr)
            case 7:
                _c = _a >> opr_val(opr)

        if opc != 3 or _a == 0:
            _ip += 2

printc(",".join([str(o) for o in sim(nums(s1[0])[0], nums(s1[1])[0], nums(s1[2])[0])]))

# My initial solution was a brute force attempt with d&q trying out multiple step sizes because there seemed to be
# a pattern in the input in how long the output is depending on the initial A and how much of the input is equal to the output.
# Then I wrote a d&q algorithm trying to get close to the best position in order to minimize computation overhead
# I personally did not like that solution, but I had no better idea until I read about Z3 - Theorem (https://ericpony.github.io/z3py-tutorial/guide-examples.htm)
# and decided to give that a try, as I did not want to submit my messy bf solution to git.
#
# Reverse Engineering
# A = start
# B = 0
# C = 0
# 
# prog = 2,4,1,1,7,5,1,5,4,0,0,3,5,5,3,0
# 
#1 = (2, 4) := B = A % 8		    // B = [0, 1, 2, 3, 4, 5, 6, 7]
#2 = (1, 1) := B = B ^ 1		    // B = [1, 0, 3, 2, 5, 4, 7, 6]
#3 = (7, 5) := C = A // 2 ** B		// C = A // 2 ** B
#4 = (1, 5) := B = B ^ 5		    // B = [5, 4, 7, 6, 1, 0, 3, 2]
#5 = (4, 0) := B = B ^ C		    // B = B ^ C
#6 = (0, 3) := A = A // 2 ** 3		// A = A // 8
#7 = (5, 5) := print B % 8		    // print B % 8
#8 = (3, 0) := jump #1 if A != 0	// if A != 0: Jump to #1
def solve(op):
    solver = Optimize()
    # Must use a BitVec because of the ^ operations, Z3 uses its own types and Int does not support ^
    _a = BitVec('a', 128)
    start_a = _a
    _b = BitVecVal(0, 128)
    _c = BitVecVal(0, 128)

    def opr_val(opr, lit=False):
        if lit:
            return opr

        if 0 <= opr <= 3:
            return opr

        if opr == 4:
            return _a

        if opr == 5:
            return _b

        if opr == 6:
            return _c

    for r in op:
        for opc, opr in zip(op[::2], op[1::2]):
            match opc:
                case 0:
                    _a = _a >> opr_val(opr)
                case 1:
                    _b = _b ^ opr_val(opr, True)
                case 2:
                    _b = opr_val(opr) & 7
                case 4:
                    _b = _b ^ _c
                case 5:
                    solver.add(opr_val(opc) % 8 == r)
                case 6:
                    _b = _a >> opr_val(opr)
                case 7:
                    _c = _a >> opr_val(opr)

    solver.add(_a == 0)
    solver.minimize(start_a)

    if solver.check() == sat:
        return solver.model().eval(start_a)
    else:
        raise Exception("No result found")

printc(solve(p))

# s2 = fetch()
# ans2 = 0
# 
# 
# printc(ans2)
# # submit(ans2)