from math import log10

from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s = fetch()
ans1 = 0
ans2 = 0

def band(a, b):
    return a & b

def bxor(a, b):
    return a ^ b

def bor(a, b):
    return a | b

def solve(input_bits, known_gates):
    values = {}

    for k, v in input_bits.items():
        values[k] = v

    added = True
    while added:
        added = False

        for g, vs in known_gates.items():
            a, b = g

            if a not in values or b not in values:
                continue

            for func, o in vs:
                if o in values:
                    continue

                values[o] = func(values[a], values[b])
                added = True

    return values

def digits(x):
    if x == 0:
        return 1

    return int(log10(x)) + 1

a, b = s.split("\n\n")

inputs = {}
for l in lines(a):
    n, v = l.split(": ")
    n = n[0] + str(nums(n)[0])
    inputs[n] = int(v)

gates = {}
for inp in lines(b):
    a, g, b, _, out = inp.split(" ")

    if a.startswith("x"):
        a = "x" + str(nums(a)[0])

    if a.startswith("y"):
        a = "y" + str(nums(a)[0])

    if b.startswith("x"):
        b = "x" + str(nums(b)[0])

    if b.startswith("y"):
        b = "y" + str(nums(b)[0])

    if out.startswith("z"):
        out = "z" + str(nums(out)[0])

    if (a, b) not in gates:
        gates[(a, b)] = []

    if g == "AND":
        gates[(a, b)].append((band, out))
    elif g == "XOR":
        gates[(a, b)].append((bxor, out))
    elif g == "OR":
        gates[(a, b)].append((bor, out))
    else:
        raise ValueError("Unknown")

values = solve(inputs, gates)

bits = [nums(k)[0] for k in values.keys() if k.startswith("z")]
maxb = max(bits)
mdigs = max([digits(d) for d in bits])
brepr = ""
for i in range(0, maxb + 1):
    brepr += str(values[f"z{i}"])

brepr = ''.join(reverse(brepr))
ans1 = str(int(brepr, 2))

printc(ans1)
# submit(ans1)

# A full - adder consists of input x_i, y_i and o_i-1, while o_i-1 is the carry in from prev bit
# 1: x_i & y_i are xor-ed into a_i and and-ed into c_i
# 2: a_i & n_i-1 are xor-ed into z_i
# 3: a_i & o_i-1 and-ed into b_i
# 4: b_i & c_i are or-ed into o_i
#
# In order for the adder to work correctly, this scheme must be met

print("Testing carry: ")
for i in range(0, maxb):
    inputs = {}
    bitrepr = ""
    for u in range(0, i):
        inputs[f"x{u}"] = 0
        inputs[f"y{u}"] = 0
        bitrepr += "0"

    inputs[f"x{i}"] = 1
    inputs[f"y{i}"] = 1
    bitrepr += "1"

    for o in range(i + 1, maxb):
        inputs[f"x{o}"] = 0
        inputs[f"y{o}"] = 0
        bitrepr += "0"

    # 45th bit
    bitrepr += "0"

    bitrepr = ''.join(reverse(bitrepr))

    values = solve(inputs, gates)

    bits = [nums(k)[0] for k in values.keys() if k.startswith("z")]
    maxb = max(bits)
    mdigs = max([digits(d) for d in bits])
    result = ""
    for k in range(0, maxb + 1):
        result += str(values[f"z{k}"])

    result = ''.join(reverse(result))
    if f"{bitrepr[1:]}0" != result:
        print(f"Mismatch at {i}:")
        print(f"{bitrepr[1:]}0 expected")
        print(f"{result} result")

print("\n\nTesting addition:")

for i in range(0, maxb):
    inputs = {}
    bitrepr = ""
    for u in range(0, i):
        inputs[f"x{u}"] = 0
        inputs[f"y{u}"] = 0
        bitrepr += "0"

    inputs[f"x{i}"] = 1
    inputs[f"y{i}"] = 0
    bitrepr += "1"

    for o in range(i + 1, maxb):
        inputs[f"x{o}"] = 0
        inputs[f"y{o}"] = 0
        bitrepr += "0"

    # 45th bit
    bitrepr += "0"

    bitrepr = ''.join(reverse(bitrepr))

    values = solve(inputs, gates)

    bits = [nums(k)[0] for k in values.keys() if k.startswith("z")]
    maxb = max(bits)
    mdigs = max([digits(d) for d in bits])
    result = ""
    for k in range(0, maxb + 1):
        result += str(values[f"z{k}"])

    result = ''.join(reverse(result))
    if bitrepr != result:
        print(f"Mismatch at {i}:")
        print(f"{bitrepr} expected")
        print(f"{result} result")

# jss AND mdg -> z18
# y31 AND x31 -> z31
# vgg OR pph -> z27

# z18, hmt
# z31, hkh
# z27, bfq
# fjp, bng

# It seems like swaps only happen within one bit - adder structure. Check out https://en.wikipedia.org/wiki/Adder_(electronics)
# Manually look at the result mismatches and fix the input at the bits that produce a different output than expected
# You can do so by drawing the adders from the input or just manually checking out which wires were swapped.

ans2 = "bfq,bng,fjp,hkh,hmt,z18,z27,z31"

print(ans2)
# # submit(ans2)