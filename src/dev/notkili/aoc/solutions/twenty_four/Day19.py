from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch().split("\n")
patterns = set(s1[0].split(", "))
min_size = min([len(p) for p in patterns])
max_size = max([len(p) for p in patterns])
towels = s1[2:]
ans1 = 0

def is_valid(tow, at=0):
    if at == len(tow):
        return True

    for i in reversed(range(min_size, max_size + 1)):
        if at + i > len(tow):
            continue

        if at + i == len(tow) and tow[at:at + i] in patterns:
            return True

        if tow[at:at + i] in patterns and is_valid(tow, at + i):
            return True

    return False

for towel in towels:
    if is_valid(towel):
        ans1 += 1

printc(ans1)

@lru
def count_valid(tow, at=0):
    count = 0

    for i in range(min_size, max_size + 1):
        if at + i > len(tow):
            continue

        if at + i == len(tow) and tow[at:at + i] in patterns:
            count += 1
            continue

        if tow[at:at + i] in patterns:
            count += count_valid(tow, at + i)
        else:
            pass

    return count

ans2 = 0
for towel in towels:
    if is_valid(towel):
        ans2 += count_valid(towel)

printc(ans2)