.data
matrix: .word	1,  2,  3,  4,  5,
		6,  7,  8,  9,  10, 
		11, 12, 13, 14, 15, 
		16, 17, 18, 19, 20, 
		21, 22, 23, 24, 25
space:	.asciiz " "
row:	.word 0
rowAndCol:	.word 0
# a 5x5 martix
.text
# This function calculates the address an element in a array of words
# Inputs:
#	 a0: The base address of the array
#	 a1: The index of the element
# Outputs:
#	 v0: The address of the element
.globl main
array_element_address:
# C.1 goes here
	move	t2,a1
	move	t1,a0
	mul	t0,t2,4
	add	t0,t0,t1
	move	v0,t0
	jr	ra
# This function calculates the address of the element (i, j) in a matrix of words
# Inputs:
#	 a0: The base address of the matrix
#	 a1: The index (i) of the row
#	 a2: The index (j) of the column
#	 a3: The number of elements in a row
# Outputs:
#	 v0: The address of the element
matrix_element_address:
# C.2 goes here
	move	t2,a1
	move	t1,a0
	move	t3,a3
	move	t4,a2
	mul	t0,t2,20
	mul	t4,t4,4
	add	t0,t0,t1
	add	t0,t0,t4
	move	v0,t0
	jr	ra

main:
	li s0, 0
	li s1, 0   

_loopRow:
    	

	la	a0,matrix
	move	a1,s0
	jal	array_element_address
	lw	a0,(v0)
	li	v0,1
	syscall
	li 	v0, 4              
	la	a0, space
	syscall

   	addi s0, s0, 1
	bge s0, 24, _out
    	j _loopRow
_out:
	li s0,0
	li s1,0
	j	_loopEntir
	addi s0,s0,1
	bge s0, 4, _exit
	j _out
 _loopEntir:
 
	la	a0,matrix
	move	a1,s0
	move	a2,s1
	li	a3,5
	jal	matrix_element_address
	lw	a0,(v0)
	li	v0,1
	syscall
	li 	v0, 4              
	la	a0, space
	syscall
	
	addi s1, s1, 1
	bge s1, 4, _out
    	j  _loopEntir
_exit:
	li v0, 10           # Load the exit system call code
    	syscall
# C.3 goes here
# C.4 goes here
