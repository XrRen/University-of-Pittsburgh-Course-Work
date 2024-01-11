.data
newline:	.asciiz "\n"
moveD:		.asciiz "Moving disk "
from:		.asciiz " from "
to:		.asciiz " to "

.text
.globl main

main:
    # Set up parameters for solve_hanoi: n, src, dest, aux
    li		a0, 5           # Height of the tower
    li		a1, 'A'         # Source pole
    li		a2, 'C'         # Destination pole
    li		a3, 'B'         # Auxiliary pole

    # Call the solve_hanoi function
    jal		solve_hanoi

    # Exit program
    li		v0, 10          # Exit syscall code
    syscall

# Recursive function to solve the Tower of Hanoi
# $a0 - n (number of disks)
# $a1 - src (source pole)
# $a2 - dest (destination pole)
# $a3 - aux (auxiliary pole)
solve_hanoi:
    # This is the exit condition
    beq		a0, 1, end_solve_hanoi

    subi	a0, a0, 1      
    move	t4, a3          
    move	a3, a2          
    move	a2, t4          
    jal		solve_hanoi        

    li 		v0, 4              
    la		a0, newline
    syscall
    li		v0, 4              
    la		a0, moveD
    syscall
    li		v0, 1              
    move	a0, a0          
    syscall
    li		v0, 4              
    la		a0, from
    syscall
    li		v0, 11             
    move	a0, a1          
    syscall
    li		v0, 4             
    la		a0, to
    syscall
    li		v0, 11             
    move	a0, a2          
    syscall
    li		v0, 4              
    la		a0, newline
    syscall

    move	t4, a1          
    move	a1, a3          
    move	a3, t4          
    jal		solve_hanoi        

end_solve_hanoi:
    jr		ra                 