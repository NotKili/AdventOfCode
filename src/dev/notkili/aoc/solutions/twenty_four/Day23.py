from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch()
ans1 = 0
ans2 = 0

def find_fully_connected(current, potential, visited, cliques, min_size = 0, max_size = 100000):
    if not potential and not visited:
        if len(current) < min_size:
            return

        if len(current) > max_size:
            return

        cliques.append(current)
        return

    if min_size <= len(current) <= max_size:
        cliques.append(current)

    for v in list(potential):
        find_fully_connected(current | {v}, potential & conns[v], visited & conns[v], cliques, min_size, max_size)
        potential.remove(v)
        visited.add(v)

conns = {}
for l in lines(s1):
    a, b = l.split("-")
    if a not in conns:
        conns[a] = set()

    if b not in conns:
        conns[b] = set()

    conns[a] |= {b}
    conns[b] |= {a}

sub_graphs = []
find_fully_connected(set(), set(conns.keys()), set(), sub_graphs, 3, 3)
sub_graphs = [(a, b, c) for a, b, c in sub_graphs if any([k.startswith("t") for k in [a, b, c]])]

res = set()
for s in sub_graphs:
    if any([p in res for p in perms(s)]):
        continue
    res |= {s}

ans1 = len(res)

printc(ans1)
# submit(ans1)

sub_graphs = []
find_fully_connected(set(), set(conns.keys()), set(), sub_graphs)

mx = max(sub_graphs, key=len)

ans2 = ",".join(sorted(list(mx)))

printc(ans2)
# # submit(ans2)