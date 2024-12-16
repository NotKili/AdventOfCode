from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch()
ans1 = 0

s1 = cmap(s1)
start_pos = reverse(s1)['S'][0]
goal_pos = reverse(s1)['E'][0]

def manhattan(a, b):
    ax, ay = a
    bx, by = b
    
    abs1 = abs(ax - bx)
    abs2 = abs(ay - by)
    
    return abs1 + abs2 + (999 if abs1 == 0 or abs2 == 0 else 0)

def next_nodes(k):
    r = []
    pos, dir = k
    x, y = pos
    dx, dy = dir

    if s1[x + dx, y + dy] != '#':
        r.append(((x + dx, y + dy), dir))

    ndir = D4l[dir]
    dx, dy = ndir
    if s1[x + dx, y + dy] != '#':
        r.append((pos, ndir))

    ndir = D4r[dir]
    dx, dy = ndir
    if s1[x + dx, y + dy] != '#':
        r.append((pos, ndir))

    return r

def transition_costs(cur, nex):
    if cur[1] == nex[1]:
        return 1

    return 1000

def bfs():
    ret = []

    q = []
    hq.heappush(q, (0, (start_pos, E), [(start_pos, E)], set()))
    
    part_of_best = set()

    while q:
        cost, current, path, visited = hq.heappop(q)
        current_pos, current_dir = current

        if len(ret) > 0:
            est = cost + manhattan(current_pos, goal_pos)
            if est > ret[0][0]:
                continue

        if current_pos == goal_pos:
            if len(ret) >= 1:
                if cost != ret[0][0]:
                    return ret
            
            for ppos, pdir in path:
                part_of_best.add(ppos)

            ret.append((cost, path))
            continue

        visited.add(current)

        for n in next_nodes(current):
            if n[0] not in s1:
                continue

            if n[0] in part_of_best:
                continue
    
            if n in visited:
                continue

            hq.heappush(q, (cost + transition_costs(current, n), n, path + [n], visited))

    return ret


best = bfs()

ans1 = best[0][0]
printc(ans1)

ans2 = len(set([pos for _, path in best for pos, _ in path]))
printc(ans2)