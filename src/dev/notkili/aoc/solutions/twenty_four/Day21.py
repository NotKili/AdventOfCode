from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s = fetch()
ans1 = 0
ans2 = 0

num_pad = {
    (0, 0): '7',
    (1, 0): '8',
    (2, 0): '9',
    (0, 1): '4',
    (1, 1): '5',
    (2, 1): '6',
    (0, 2): '1',
    (1, 2): '2',
    (2, 2): '3',
    (0, 3): None, #Does not exist
    (1, 3): '0',
    (2, 3): 'A',
}
rev_num_pad = {v: k for k, v in num_pad.items()}

dir_pad = {
    (0, 0): None, # Does not exist
    (1, 0): '^',
    (2, 0): 'A',
    (0, 1): '<',
    (1, 1): 'v',
    (2, 1): '>',
}
rev_dir_pad = {v: k for k, v in dir_pad.items()}

@lru
def count_press(seq, depth, enter_num=True, start="A"):
    pad = num_pad if enter_num else dir_pad
    rev_pad = rev_num_pad if enter_num else rev_dir_pad

    if not seq:
        return 0

    start = rev_pad[start]
    goal = rev_pad[seq[0]]
    dx, dy = goal[0] - start[0], goal[1] - start[1]
    presses = ('>' if dx > 0 else '<' if dx < 0 else '') * abs(dx) + ('v' if dy > 0 else '^' if dy < 0 else '') * abs(dy)

    if depth == 0:
        return len(presses) + 1 + count_press(seq[1:], depth, enter_num, pad[goal])

    def path_valid(p):
        tx, ty = start
        for sp in p:
            match sp:
                case "^": ty -= 1
                case "v": ty += 1
                case ">": tx += 1
                case "<": tx -= 1
            if (tx, ty) not in pad or pad[tx, ty] is None:
                return False
        return True

    def rec():
        for p in perms(presses):
            p = ''.join(p)
            if path_valid(p):
                yield count_press(p + 'A', depth - 1, False)

    return min(rec()) + count_press(seq[1:], depth, enter_num, pad[goal])

for l in lines(s):
    pc = count_press(l, 2)
    ans1 += nums(l)[0] * pc

printc(ans1)
# submit(ans1)

for l in lines(s):
    pc = count_press(l, 25)
    ans2 += nums(l)[0] * pc

printc(ans2)
# submit(ans2)