import re


color_limits = [('red', 12), ('green', 13), ('blue', 14)]


def parse_cube(line):
    # Parse cubes and calculate the maximum set of cubes for each line
    cube_pattern = re.compile(r'(\d+) (red|blue|green)')
    color_counts = {color: 0 for color, _ in color_limits}

    for match in cube_pattern.finditer(line):
        count, color = match.groups()
        color_counts[color] += int(count)

    return color_counts


def calculate_power(color_counts):
    # Calculate the power of a set of cubes
    return color_counts['red'] * color_counts['green'] * color_counts['blue']


def calculate_max_set(line):
    # Calculate the maximum set of cubes for a game
    max_set = {color: 0 for color, _ in color_limits}

    for cube in line.split(';'):
        color_counts = parse_cube(cube)
        for color, count in color_counts.items():
            max_set[color] = max(max_set[color], count)

    return max_set


def calculate_sum_of_powers(file_path):
    total_power = 0

    with open(file_path) as file:
        for line in file:
            max_set = calculate_max_set(line)
            power = calculate_power(max_set)
            total_power += power

    return total_power


print("The sum of the power of maximum sets is:", calculate_sum_of_powers("input.txt"))
