from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch()

ans1 = 0
ans2 = 0

s1 = cmap(s1)
rs1 = reverse(s1)

def bfs():
    ret = []
    q = []
    hq.heappush(q, (0, rs1["S"][0], [rs1["S"][0]], set()))

    while q:
        cost, current, path, visited = hq.heappop(q)

        if s1[current] == "E":
            if len(ret) == 1 and cost > ret[0][0]:
                if cost > ret[0][0]:
                    break

            ret.append((cost, path))
            continue

        if current in visited:
            continue

        visited.add(current)

        x,y = current
        for n in [(x+dx,y+dy) for dx, dy in D4 if (x+dx,y+dy) in s1 and s1[(x+dx,y+dy)] != "#"]:
            if n not in s1:
                continue

            if n not in visited:
                hq.heappush(q, (cost + 1, n, path + [n], visited))

    return ret

paths = bfs()

fastest, path = paths[0]

costs = {}
for c, p in paths:
    for i, pos in enumerate(p):
        if pos not in costs:
            costs[pos] = len(p) - i
        else:
            if costs[p] > len(p) - i:
                costs[p] = len(p) - i

def manhattan(x, y):
    return abs(x[0] - y[0]) + abs(x[1] - y[1])

def get_neighbours(graph, pos, dist):
    x, y = pos

    for dy in range(y - (dist + 1), y + (dist + 1)):
        for dx in range(x - (dist + 1), x + (dist + 1)):
            np = (dx, dy)

            if np not in graph:
                continue

            if manhattan(pos, np) > dist:
                continue

            yield np

def bfs_cheat(dist):
    ret = []
    q = []
    cheats = {}
    hq.heappush(q, (0, rs1["S"][0], [rs1["S"][0]], set()))

    while q:
        cost, current, path, visited = hq.heappop(q)

        if cost >= fastest:
            continue

        if s1[current] == "E":
            break

        if current in visited:
            continue

        visited.add(current)

        for n in get_neighbours(s1, current, dist):
            if s1[n] == '#':
                continue

            nc = cost + costs[n] + manhattan(current, n) - 1
            if nc >= fastest:
                continue

            ch = (current, n)

            if ch not in cheats:
                cheats[ch] = nc

            if cheats[ch] <= nc:
                continue

            cheats[ch] = nc

        x, y = current
        for n in [(x+dx,y+dy) for dx, dy in D4 if (x+dx,y+dy) in s1 and s1[(x+dx,y+dy)] != "#"]:
            if n not in visited:
                hq.heappush(q, (cost + 1, n, path + [n], visited))

    for k,v in cheats.items():
        ret.append(v)

    return ret

ret = bfs_cheat(2)
ct = ctr(ret)
for c in sorted(list(set(ret))):
    if fastest - c >= 100:
        ans1 += ct[c]

printc(ans1)
# submit(ans1)

ret = bfs_cheat(20)
ct = ctr(ret)
for c in sorted(list(set(ret))):
    if fastest - c >= 100:
        ans2 += ct[c]

printc(ans2)
# # submit(ans2)