# Xirui Ren

# Include the macros file so that we save some typing! :)
.include "macros.asm"
# Include the constants settings file with the board settings! :)
.include "constants.asm"
# We will need to access the pixel model, include the structure offset definitions
.include "pixel_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl empty_draw

.text
# this will draw out the empty space
empty_draw:
	enter	s0,s1,s2,s3
	# Your code goes in here
	move	s1,a0 #x
	move	s2,a1 #y
	move	s3,a0
	# Get pixel s0
	li	a0,0
	jal	empty_get_element
	mul	s1,s1,5
	mul	s2,s2,5
	mul	s3,s3,5
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,1
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,2
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,3
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,4
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,5
	jal	empty_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,6
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,7
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,8
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,9
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,10
	jal	empty_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,11
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,12
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,13
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,14
	jal	empty_get_element
	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,15
	jal	empty_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,16
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,17
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,18
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,19
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,20
	jal	empty_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,21
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,22
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,23
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	li	a0,24
	jal	empty_get_element

	addi	s1,s1,1
	sw	s1,pixel_x(v0)
	sw	s2,pixel_y(v0)
	li	t0,COLOR_BLACK
	sw	t0,pixel_color(v0)
	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	lw	a2, pixel_color(v0)
	jal	display_set_pixel
	
	leave	s0,s1,s2,s3

