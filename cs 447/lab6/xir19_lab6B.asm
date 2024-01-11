.data
  returned: .asciiz "The function returned\n"
  opcode:	.asciiz "opcode = "
  rs:		.asciiz "rs = "
  rt:		.asciiz "rt = "
  immediate:	.asciiz "immediate =  "
  newline:	.asciiz "\n"
.text
# Prints the different fields of an I-type instruction
# decode_instruction(a0: memory address of instruction)
decode_instruction:
  # Implement B.1 here
  	lw	t0,0(a0)
  	srl	t1,t0,26
  	andi	t1,t1,0x3f
  	la 	a0, opcode
	li 	v0, 4
	syscall
	
  	move	a0,t1
  	li	v0,1
  	syscall
  	la 	a0, newline
	li 	v0, 4
	syscall
	
	srl	t1,t0,21
  	andi	t1,t1,0x1f
  	la 	a0, rs
	li 	v0, 4
	syscall
	move	a0,t1
  	li	v0,1
  	syscall
  	la 	a0, newline
	li 	v0, 4
	syscall
	
	srl	t1,t0,16
  	andi	t1,t1,0x1f
  	la 	a0, rt
	li 	v0, 4
	syscall
	move	a0,t1
  	li	v0,1
  	syscall
  	la 	a0, newline
	li 	v0, 4
	syscall
	
	srl	t1,t0,0
  	andi	t1,t1,0xffff
  	la 	a0, immediate
	li 	v0, 4
	syscall
	
  	move	a0,t1
  	li	v0,34
  	syscall
  	la 	a0, newline
	li 	v0, 4
	syscall
  	jr ra


# Encodes the fields of an I-type instruction and returns it
# encode_instruction(a0: opcode, a1: rs, a2: rt, a3: immediate)
encode_instruction:
  # Implement B.2 here
  	sll	t0,a0,26
  	sll	t1,a1,21
  	sll	t2,a2,16
  	or	t0,t0,t1
  	or	t0,t0,t2
  	sll	t1,a3,0
  	or	t0,t1,t0
 	move	a0,t0
  	li	v0,34
  	syscall
	la 	a0, newline
	li 	v0, 4
	syscall
  	jr ra