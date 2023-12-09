def calculate_calibration_sum(file_path):
    total_sum = 0
    
    with open(file_path) as file:
        for line in file:
            digits = [int(char) for char in line if char.isdigit()]
            
            first_digit = digits[0]
            
            # If only one digit is present, treat it as both the first and last digit
            last_digit = digits[-1] if len(digits) > 1 else first_digit
            
            # Combine the digits to form a two-digit number then add the result to the total sum
            total_sum += first_digit * 10 + last_digit
            
    return total_sum


print("The sum of all calibration values is:", calculate_calibration_sum("input.txt"))
