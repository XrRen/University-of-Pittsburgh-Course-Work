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
	enter	s0
	# Your code goes in here
	move	s0, zero
_pixel_draw_loop:
	bge	s0, n_pixels, _pixel_draw_exit # n_pixels means the total pixel preent

	# Get pixel s0
	move	a0, s0
	jal	pixel_get_element

	# Setup arguments with coordinates
	lw	a0, pixel_x(v0)
	lw	a1, pixel_y(v0)
	# Decide on colour
	lw	t0, pixel_selected(v0)
	li	a2, COLOR_WHITE
	beqz	t0, _pixel_draw_color_not_selected
	li	a2, COLOR_RED
_pixel_draw_color_not_selected:
	jal	display_set_pixel

	inc	s0
	j	_pixel_draw_loop
_pixel_draw_exit:
	leave	s0
