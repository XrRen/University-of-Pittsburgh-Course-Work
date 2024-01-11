## This file implements the functions that display the pixels based on the model

# Include the macros file so that we save some typing! :)
.include "macros.asm"
# Include the constants settings file with the board settings! :)
.include "constants.asm"
# We will need to access the pixel model, include the structure offset definitions
.include "pixel_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl pixel_draw

.text
# void pixel_draw()
#	1. This function goes through the array of pixels and for each
#		1.1. Gets its (x, y) coordinates and selected status
#		1.2. Prints it in the display using function display_set_pixel (display.asm)
#			1.2.1. If the pixel is not selected, print it using some color (Not COLOR_BLACK, as this is the background color)
#			1.2.2. If the pixel is selected, print it using another color
pixel_draw:
	enter	s0,s1,s2
	# Your code goes in here
	li	t0,3 #get the total amount of the pixels
	li	t6,0
_pixel_loop:
	
	move	a0,t6
	jal	pixel_get_element
	addi	t1,v0,0
		
	lw	s0,pixel_x(t1)
	lw	s1,pixel_y(t1)
	lw	s2,pixel_selected(t1)
	lw	s3,pixel_size(v0)
	
	bne	s2,zero,_select
_notSelect:
	move	a0,s0
	move	a1,s1
	li	a2,1
	jal	display_set_pixel
	j	_continu
_select:
	move	a0,s0
	move	a1,s1
	li	a2,5
	
	jal	display_set_pixel
_continu:
	addi	t6,t6,1
	
	ble	t6,3,_pixel_loop
_exit:
	leave	s0,s1,s2
