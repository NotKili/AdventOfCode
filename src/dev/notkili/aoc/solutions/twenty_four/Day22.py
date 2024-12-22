from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s1 = fetch()
ans1 = 0
ans2 = 0

def mix(secret, value):
    return secret ^ value

def prune(secret):
    return secret % 16777216

def evolve(secret):
    secret = prune(mix(secret, secret * 64))
    secret = prune(mix(secret, secret // 32))
    secret = prune(mix(secret, secret * 2048))
    return secret

initial = [nums(l)[0] for l in lines(s1)]
buyers = initial
steps = {0: buyers}
seq = {}

for k in range(len(initial)):
    seq[initial[k]] = [buyers[k] % 10]

for i in range(1, 2001):
    buyers = [evolve(b) for b in buyers]
    steps[i] = buyers
    for k in range(len(initial)):
        seq[initial[k]].append(buyers[k] % 10)

ans1 = sum(buyers)

printc(ans1)

seqs = ctr()
sell_by_seq = {}
for monkey in initial:
    tmp = set()
    tmps = {}
    sell_by_seq[monkey] = tmps
    ms = seq[monkey]
    for k1, k2, k3, k4, k5 in zip(ms, ms[1:], ms[2:], ms[3:], ms[4:]):
        t = (k2 - k1, k3 - k2, k4 - k3, k5 - k4)
        if t in tmp:
            continue

        tmps[t] = k5
        tmp.add(t)
        seqs[t] += 1

interesting = set()
for seq, ct in seqs.items():
    interesting.add(seq)

for iseq in interesting:
    tmp = 0
    for monkey in initial:
        tmp += sell_by_seq[monkey].get(iseq, 0)
    ans2 = max(ans2, tmp)

printc(ans2)