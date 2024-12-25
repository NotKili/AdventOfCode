from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s = fetch()
ans1 = 0
ans2 = 0

s = s.split("\n\n")

locks = []
keys = []

for ss in s:
    mp = cmap(ss)

    # Lock
    if mp[(0, 0)] == "#":
        vals = ()
        for x in range(0, 5):
            for y in range(0, 6):
                if mp[(x, 1 + y)] == ".":
                    vals = vals + (y,)
                    break
        locks.append(vals)
    else:
        vals = ()
        for x in range(0, 5):
            for y in range(0, 6):
                if mp[(x, 5 - y)] == ".":
                    vals = vals + (y,)
                    break
        keys.append(vals)

locks = list(set(locks))
keys = list(set(keys))

for l in locks:
    for k in keys:
        valid = True
        for lx, kx in zip(l, k):
            if lx + kx <= 5:
                continue

            valid = False
            break
        if valid:
            ans1 += 1

printc(ans1)
# submit(ans1)


# print(ans2)
# # submit(ans2)