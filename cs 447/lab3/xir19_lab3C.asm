.data
  a: .byte 0x12, 0x34, 0x56, 0x78
	b: .word 0x12345678
.text
.globl main
main:
	la t0, a
	add t0,t0,1
	lbu t1,(t0)
	li v0,1
	move a0,t1
	syscall