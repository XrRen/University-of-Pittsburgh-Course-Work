## This file implements the functions that display the boxs based on the model

# Include the macros file so that we save some typing! :)
.include "macros.asm"
# Include the constants settings file with the board settings! :)
.include "constants.asm"
# We will need to access the box model, include the structure offset definitions
.include "box_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl box3_draw

.text
# a0 for the starting pixel of the box
box3_draw:
	enter	s0,s1,s2,s3,s4
	
	move	s4,a2
	# Your code goes in here
	move	s1,a0 #x
	move	s2,a1 #y
	move	s3,a0 #store x
	# Get box s0
	move	a0,s4
	jal	box_get_element
	mul	s1,s1,5
	mul	s2,s2,5
	mul	s3,s3,5
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)

	# Setup arguments with coordinates
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
	
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
	
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
	
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
	
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	addi	s4,s4,1
	move	a0,s4
	jal	box_get_element

	addi	s1,s1,1
	sw	s1,box_x(v0)
	sw	s2,box_y(v0)
	sw	zero,box_target(v0)
		
	leave	s0,s1,s2,s3,s4
