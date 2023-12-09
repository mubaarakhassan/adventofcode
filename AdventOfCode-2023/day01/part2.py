import re


def calculate_calibration_sum(file_path):
    total_sum = 0

    digit_mapping = {
        1: ['1', 'one'],
        2: ['2', 'two'],
        3: ['3', 'three'],
        4: ['4', 'four'],
        5: ['5', 'five'],
        6: ['6', 'six'],
        7: ['7', 'seven'],
        8: ['8', 'eight'],
        9: ['9', 'nine']
    }

    # Precompile the regular expression pattern
    pattern = re.compile('|'.join(map(re.escape, [item for sublist in digit_mapping.values() for item in sublist])))

    with open(file_path) as file:
        for line in file:
            first_digit = None
            last_digit = None
            
            # Find matches of digit representations in the line
            matches = pattern.finditer(line)
            
            # Process each match and determine the corresponding digit
            for match in matches:
                start, end = match.span()
                matched_text = line[start:end]

                # Find the corresponding digit based on the matched text
                for digit, words in digit_mapping.items():
                    if matched_text in words:
                        if first_digit is None:
                            first_digit = digit
                        last_digit = digit
                        break

            total_sum += int(first_digit) * 10 + int(last_digit)
    return total_sum


# Calculate the result using the function
print("The sum of all calibration values is:", calculate_calibration_sum("input.txt"))
