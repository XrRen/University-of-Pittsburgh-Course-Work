.data

	list:			.word	0:10
	request_for_input:	.asciiz	"\nInsert a number: "
	message_list_contents:	.asciiz "\nThe contents of the array are: "

.text
.globl main

main:
	# variable i is in register s0
	move	s0, zero			# for( i = 0;
_main_for_insert:
	bge	s0, 10, _main_end_for_insert	#      i < 10;
                                  		#      i++) (*) check before main_endForInsert
	## For implementation
	# Print request for input
	li	v0, 4
	la	a0, request_for_input
	syscall					# print_string("\nInsert a number: "); // syscall

	# Get user input
	li	v0, 5
	syscall
	move	s1, v0                  	# The result of the syscall is in v0

	# write list[i]
	# Calculate the memory address of list[i]
	la	t1, list			# Load the address of the list
	li	t2, 4				# Load the size of a word
	mul	t3, s0, t2 			# Multiply i by the size of a word (i*4)
	add	t4, t1, t3 			# Calculate the memory address of element i (list +i*4)
	# Store the user value
	sw	s1, 0(t4)			# list[i] = s1

	addi	s0, s0, 1			# (*) i++
	j	_main_for_insert
_main_end_for_insert:


	# Exit
	li v0, 10
	syscall