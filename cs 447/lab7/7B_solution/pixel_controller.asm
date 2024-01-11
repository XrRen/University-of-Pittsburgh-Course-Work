## This file implements the functions that control the pixels based on the keyboard input

# Include the convenience file so that we save some typing! :)
.include "macros.asm"
# Include the game settings file with the board settings! :)
.include "constants.asm"
# We will need to access the pixel model, include the structure offset definitions
.include "pixel_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl pixel_update


.data
	# Keeps track of the last frame where we updated the selected pixel
	last_update:	.word	0
.text
# void pixel_update(current_frame)
#	1. This function goes through the array of pixels and finds the one that is selected (maybe you want to implement this as a different function?)
#	2. If no pixel is selected, then select pixel 0
#	3. Reads the state of the keyboard buttons and move the selected pixel accordingly
#		3.1. The pixel moves up to one pixel up/down and up to one pixel left/right according to the keyboard input.
#		3.2. The pixel must not leave the bounds of the display (check the .eqv in game.asm)
#	4. Every 60 frames (use the input): If the user pressed the action button (B) the current selected pixel is deselected and the next pixel is selected (the array loops around).
#		You may need some variables!
pixel_update:
	enter	s0, s1
	move	s1, a0
	# Your code goes in here
	jal	pixel_find_selected
	move	s0, v0
	# If none is selected, then select 0
	bgez	s0, _pixel_update_found
	move	s0, zero
	move	a0, s0
	jal	pixel_get_element
	# Select pixel
	li	t0, 1
	sw	t0, pixel_selected(v0)
_pixel_update_found:

	# Get address of selected pixel
	move	a0, s0
	jal	pixel_get_element
	move	t0, v0

	# Now lets check user input
	# Start with up/down to update the y coordinate
	lw	t1, up_pressed
	lw	t2, pixel_y(t0)
	sub	t2, t2, t1
	lw	t1, down_pressed
	add	t2, t2, t1
	# Check y bounds
	bltz	t2, _pixel_update_skip_y
	bge	t2, DISPLAY_H, _pixel_update_skip_y
	sw	t2, pixel_y(t0)
_pixel_update_skip_y:

	# Now with left/right to update the x coordinate
	lw	t1, left_pressed
	lw	t2, pixel_x(t0)
	sub	t2, t2, t1
	lw	t1, right_pressed
	add	t2, t2, t1
	# Check x bounds
	bltz	t2, _pixel_update_skip_x
	bge	t2, DISPLAY_W, _pixel_update_skip_x
	sw	t2, pixel_x(t0)
_pixel_update_skip_x:

	# Now with action to update the selected pixel
	# Check if it is time for the next update
	lw	t1, last_update
	addi	t1, t1, 60
	bge	t1, s1, _pixel_update_no_action
	# It is time! do it, do it now! If the action was pressed...
	lw	t1, action_pressed
	beqz	t1, _pixel_update_no_action
	# Deselect current and increment index of selected pixel
	sw	zero, pixel_selected(t0)
	inc	s0
	# Did we overflow the number of pixels?
	rem	s0, s0, n_pixels
	# Select next pixel here
	move	a0, s0
	jal	pixel_get_element
	li	t0, 1
	sw	t0, pixel_selected(v0)
	sw	s1, last_update
_pixel_update_no_action:
	leave	s0, s1


pixel_find_selected:
	enter	s0
	# Loop all pixels
	move	s0, zero
_pixel_find_selected_loop:
	# If the for exits here, then no pixel is selected
	bge	s0, n_pixels, _pixel_find_selected_exit_none
	# Get information about the pixel
	move	a0, s0
	jal	pixel_get_element
	lw	t1, pixel_selected(v0)
	# If loop exits here, pixel with index in s0 is selected
	bnez	t1, _pixel_find_selected_exit_s0
	inc	s0
	j	_pixel_find_selected_loop
_pixel_find_selected_exit_none:
	# No pixel is selected, return index -1
	li	v0, -1
	j	_pixel_find_selected_exit
_pixel_find_selected_exit_s0:
	# If pixel found, put index in v0
	move	v0, s0
_pixel_find_selected_exit:
	leave	s0
