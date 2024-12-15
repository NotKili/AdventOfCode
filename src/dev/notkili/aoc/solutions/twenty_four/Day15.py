from aoc_utils_notkili.aoc_utils import *
import sys

sys.setrecursionlimit(10 ** 6)

s = fetch()

# Part 1, brute
ans1 = 0
s1, s2 = s.split("\n\n")
s1 = cmap(s1)
s2 = s2.replace("\n", "")
mapper = {'^': N, '>': E, 'v': S, '<': W}
s2 = [mapper[c] for c in chars(s2)]

rx, ry = reverse(s1)['@'][0]

for dx, dy in s2:
    nx = rx + dx
    ny = ry + dy

    if s1[nx, ny] == "#":
        continue

    if s1[nx, ny] == ".":
        s1[rx, ry] = "."
        s1[nx, ny] = "@"
        rx = nx
        ry = ny
        continue

    n = 1
    while True:
        if s1[rx + n * dx, ry + n * dy] == "O":
            n += 1
            continue

        break

    if s1[rx + n * dx, ry + n * dy] == "#":
        continue

    while n > 0:
        s1[rx + n * dx, ry + n * dy] = s1[rx + (n - 1) * dx, ry + (n - 1) * dy]
        n -= 1

    rx = nx
    ry = ny

for bx, by in reverse(s1)["O"]:
    ans1 += 100 * by + bx

# Part 2, recursive tree
ans2 = 0
s1, s2 = s.split("\n\n")
s1 = cmap(s1)
repl_func = lambda k, v: [((k[0] * 2, k[1]), v), ((k[0] * 2 + 1, k[1]), v)] if v != 'O' and v != '@' else [((k[0] * 2, k[1]), "["), ((k[0] * 2 + 1, k[1]), "]")] if v == 'O' else [((k[0] * 2, k[1]), "@"), ((k[0] * 2 + 1, k[1]), ".")]
s1 = upscale(s1, repl_func)
s2 = s2.replace("\n", "")
mapper = {'^': N, '>': E, 'v': S, '<': W}
s2 = [mapper[c] for c in chars(s2)]

rx, ry = reverse(s1)['@'][0]

for dx, dy in s2:
    nx = rx + dx
    ny = ry + dy

    if s1[nx, ny] == "#":
        continue

    if s1[nx, ny] == ".":
        s1[rx, ry] = "."
        s1[nx, ny] = "@"
        rx = nx
        ry = ny
        continue

    if dy == 0:
        n = 1
        while True:
            if s1[rx + n * dx, ry + n * dy] == "[" or s1[rx + n * dx, ry + n * dy] == "]":
                n += 1
                continue
            break

        if s1[rx + n * dx, ry + n * dy] == "#":
            continue

        while n > 0:
            s1[rx + n * dx, ry + n * dy] = s1[rx + (n - 1) * dx, ry + (n - 1) * dy]    
            n -= 1

        s1[rx, ry] = "."
        rx = nx
        ry = ny
    else:
        def can_move_vert(x, y):
            if s1[x, y] == "[":
                xl = x
                xr = x + 1
            elif s1[x, y] == "]":
                xl = x - 1
                xr = x
            else:
                return s1[x, y] == '.'

            if s1[xl, y + dy] == '.' and s1[xr, y + dy] == '.':
                return True
            
            if s1[xl, y + dy] == '#' or s1[xr, y + dy] == '#':
                return False
            
            return can_move_vert(xl, y + dy) and can_move_vert(xr, y + dy)
            
        if not can_move_vert(rx, ry + dy):
            continue
                
        def move_vert(x, y):
            if s1[x, y] == "[":
                xl = x
                xr = x + 1
            elif s1[x, y] == "]":
                xl = x - 1
                xr = x
            else:
                return

            # Swap
            if s1[xl, y + dy] == '.' and s1[xr, y + dy] == '.':
                s1[xl, y + dy] = s1[xl, y]
                s1[xr, y + dy] = s1[xr, y]
                s1[xl, y] = '.'
                s1[xr, y] = '.'
                return
            
            if s1[xl, y + dy] == '#' or s1[xr, y + dy] == '#':
                return
            
            move_vert(xl, y + dy)
            move_vert(xr, y + dy)
            s1[xl, y + dy] = s1[xl, y]
            s1[xr, y + dy] = s1[xr, y]
            s1[xl, y] = '.'
            s1[xr, y] = '.'
        
        move_vert(rx, ry + dy)
                
        s1[rx, ry] = "."
        s1[rx, ry + dy] = "@"
        rx = nx
        ry = ny

for bx, by in reverse(s1)["["]:
    ans2 += 100 * by + bx

print(ans1, ans2)
# printc(ans1)
# printc(ans2)
# submit(ans1)
# submit(ans2)