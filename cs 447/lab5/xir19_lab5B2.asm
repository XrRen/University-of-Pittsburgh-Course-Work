.text
.globl main
main:
	li	s0, 0x1337BEEF
	li	s1, 0x00C0FFEE

	jal	function

	li	v0, 10
	syscall		# terminate program

function:
	sw 	ra, 0(sp)
    	addi 	sp, sp, -4
	sw 	s0, 0(sp)
    	addi 	sp, sp, -4
	sw 	s1, 0(sp)
   	addi 	sp, sp, -4

	li	s0, 54
	addi	s1, s0, 100

	addi 	sp, sp, 4
    	lw 	s1, 0(sp)
	addi 	sp, sp, 4
    	lw 	s0, 0(sp)
	addi 	sp, sp, 4
    	lw 	ra, 0(sp)

	jr	ra