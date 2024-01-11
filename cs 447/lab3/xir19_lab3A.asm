.data
	a:	.byte 0
	b:	.byte 16
	c:	.byte 33

.text
.globl main
main:
	lb t0,	a
	lb t1,	b
	lb t2,	c
	
	sub t0,t1,t2
	
	sb t0,	a
	
	lb a0, a
	li v0, 1
	syscall
	
	li v0, 11
	li a0, '\n'  # printChar('\n')
	syscall	
	
	la	t0,b
	sb	t0,b
	li	v0,34
	lb	a0,b
	syscall
