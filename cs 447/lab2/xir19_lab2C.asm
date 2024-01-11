.data
	str: .asciiz "What is the first value?\n"
	
	a:	.word 0 # declare the input variable

	str1: .asciiz "what is the second value?\n"
	
	b:	.word 0
	
	c:	.word 0
	
	result:	.asciiz " - "
	
	equal:	.asciiz " = "
	
.text
.globl main
main: 
	la a0, str# this block print String
	li v0, 4
	syscall
	
	# the integer is inside v0 for now
	li v0, 5 # this block used for getting the users input
 	syscall # a = getInteger()
 	#now a = v0
 	sw v0, a
 	
 	la a0, str1
 	li v0,4
 	syscall
 	
 	li v0, 5
 	syscall
 	sw v0, b
	
	li v0, 11
	li a0, '\n'  # printChar('\n')
	syscall	
	
	
	lw a0 ,a
	li v0, 1
	syscall
	
	la a0, result
 	li v0,4
 	syscall
 	
 	lw a0, b
	li v0, 1
	syscall
	
	la a0, equal
 	li v0,4
 	syscall
	
	lw t0, a
	lw t1, b
	lw t2, c
	
	sub t2,t0,t1
	
	sw t2, c
	
	lw a0, c 
	li v0, 1
	syscall
	
	li v0, 10 # this block helps stops the program
	syscall # exit()
