import re


color_limits = [('red', 12), ('green', 13), ('blue', 14)]
     
     
def parse_cube(line):
    # Parse cubes and check if the set is valid for each line
    cube_pattern = re.compile(r'(\d+) (red|blue|green)')
    color_counts = {color: 0 for color, _ in color_limits}

    for match in cube_pattern.finditer(line):
        count, color = match.groups()
        color_counts[color] += int(count)

    return color_counts


def is_cube_set_valid(color_counts):
    # Check if the revealed cubes in each cube are within the specified limits.
    return all(color_counts[color] <= limit for color, limit in color_limits)


def get_game_number(line):
    game_id_pattern = re.compile(r'Game (\d+)')
    return game_id_pattern.search(line).group(1)


def calculate_sum_of_ids(file_path):
    total_sum = 0

    with open(file_path) as file:
        for line in file:
            for cube in line.split(';'):
                color_counts = parse_cube(cube)
                
                if not is_cube_set_valid(color_counts):
                    break
            else:
                # If all sets are valid, add the game number to the total sum
                game_number = get_game_number(line)
                total_sum += int(game_number)

    return total_sum


print("The sum of all calibration values is:", calculate_sum_of_ids("input.txt"))
