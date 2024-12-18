from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch()
ans1 = 0
ans2 = 0

mem_sp = {}
_size = 70

def _next_nodes(g, c):
    x, y = c
    for dx, dy in D4:
        if (x+dx, y+dy) in g:
            if g[x+dx, y+dy] == 0:
                yield (x+dx, y+dy)

def bfs(graph: dict, start):
    ret = []

    q = []
    hq.heappush(q, (0, start, [start], set()))

    while q:
        cost, current, path, visited = hq.heappop(q)

        if current == (_size, _size):
            if len(ret) == 1 and cost > ret[0][0]:
                if cost > ret[0][0]:
                    break

            ret.append((cost, path))
            continue

        if current in visited:
            continue

        visited.add(current)

        for n in _next_nodes(graph, current) or []:
            if n not in graph:
                continue

            if n not in visited:
                hq.heappush(q, (cost + 1, n, path + [n], visited))

    return ret

for y in range(_size + 1):
    for x in range(_size + 1):
        mem_sp[(x, y)] = 0

s1 = s1.split("\n")

s1 = [nums(t) for t in s1]
c = 0
for x, y in s1:
    mem_sp[(x, y)] = 1
    c += 1

    if c == 1024:
        cost, path = bfs(mem_sp, (0, 0))[0]
        printc(cost)

    # Solution for p2 in bf, takes a few seconds. The optimization is binary search on the input to reduce search complexity
    if c > 1024:
        if (len(bfs(mem_sp, (0, 0)))) == 0:
            printc(f"{x},{y}")
            break