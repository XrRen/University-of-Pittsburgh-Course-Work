.data
newline:    .asciiz "\n"

.text
.globl main
main:
    li $t0, 1          # Outer loop counter initialization
outer_loop:
    li $t1, 1          # Inner loop counter initialization
inner_loop:
    # Multiply $t0 by $t1 and print the result
    mul $t2, $t0, $t1
    move $a0, $t2      # Load the result into $a0 for printing
    li $v0, 1          # System call code for printing an integer
    syscall

    # Print a tab character
    li $v0, 11         # System call code for printing a character
    li $a0, 9          # ASCII code for the tab character
    syscall

    # Increment the inner loop counter ($t1) and check if it's less than or equal to 5
    addi $t1, $t1, 1
    ble $t1, 5, inner_loop

    # Print a newline character
    li $v0, 4          # System call code for printing a string
    la $a0, newline    # Load the address of the newline string
    syscall

    # Increment the outer loop counter ($t0) and check if it's less than or equal to 5
    addi $t0, $t0, 1
    ble $t0, 5, outer_loop

    # Exit the program
    li $v0, 10         # System call code for program exit
    syscall