.data
	str: .asciiz "Enter a number between 0 and 99: \n"
	
	a:	.word 0 # declare the input variable
	b:	.word 0
	win:	.asciiz "You win!"
	lost:	.asciiz "You lost!"
	warm:	.asciiz "You're warm"
	cold:	.asciiz "You're cold"
	off:	.asciiz "Your guess is off from the range"
.text
.globl main
main:
	li	v0, 42
	li	a0, 0
	li	a1, 99
	syscall       # v0 = syscall(GET_RANDOM_INT, 0, 5);
	sw v0, b
	la a0, str
 	li v0,4
 	syscall
	
	li v0, 5 # this block used for getting the users input
 	syscall # a = getInteger()
 	#now a = v0
 	sw v0, a
 	li s0,0
 Loop_top:
 	bge s0, 5,loop_break
	lw 	t0,a
	ble 	t0, 0, BLOCK_B
  	bge 	t0, 100, BLOCK_B
  	j BLOCK_A
  	addi s0,s0,1
  	j loop_top
  	
BLOCK_A:
	bgt t0, b,BLOCK_C 
	bgt b,t0, BLOCK_D
BLOCK_B:
	la a0, off
 	li v0,4
 	syscall
 	
BLOCK_C:
	