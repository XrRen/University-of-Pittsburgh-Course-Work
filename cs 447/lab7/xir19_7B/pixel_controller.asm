## Collaborated with Connor Clifford


## This file implements the functions that control the pixels based on the keyboard input

# Include the macros file so that we save some typing! :)
.include "macros.asm"
# Include the constants settings file with the board settings! :)
.include "constants.asm"
# We will need to access the pixel model, include the structure offset definitions
.include "pixel_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl pixel_update


.text
# void pixel_update(int current_frame)
#	1. This function goes through the array of pixels and finds the one that is selected (maybe you want to implement this as a different function?)
#	2. If no pixel is selected, then select pixel 0
#	3. Reads the state of the keyboard buttons and move the selected pixel accordingly
#		3.1. The pixel moves up to one pixel up/down and up to one pixel left/right according to the keyboard input.
#		3.2. The pixel must not leave the bounds of the display (check the .eqv in constants.asm)
#	4. Every 60 frames (use the input): If the user pressed the action button (B) the current selected pixel is deselected and the next pixel is selected (the array loops around).
#		You may need some variables!
pixel_update:
	enter	s0,s1,s2,s3,s4,s5
	# Your code goes in here
	li	t0,3 #get the total amount of the pixels
	li	t6,0
	move	a0,t6
	#bne	s4,zero,_select
_pixel_loop:
	
	move	a0,t6
	jal	pixel_get_element
	addi	t1,v0,0
		
	lw	s0,pixel_x(t1)
	lw	s1,pixel_y(t1)
	lw	s2,pixel_selected(t1)
	##lw	s3,pixel_size(v0)
	
	bne	s2,zero,_select
	addi	t6,t6,1
	
	ble	t6,3,_pixel_loop
_notSelect:
	li	a0,0
	jal	pixel_get_element
	addi	t1,v0,0
	
	lw	s0,pixel_x(t1) #pixel 0
	lw	s1,pixel_y(t1)
	lw	s2,pixel_selected(t1)	

	addi	s3,s3,1
	sw	s3,pixel_selected(t1)
	j	_continu
	
_select:
	move	a0,t6
	jal	pixel_get_element
	addi	t1,v0,0
	sw	s0,pixel_y(t1)
	sw	s1,pixel_y(t1)
	lw	s2,pixel_selected(t1)	

	addi	s2,s2,1
	sw	s2,pixel_selected(t1)
_continu:
	
	
_keyCheck:
	li	s2,KEY_U 
	li	s3,KEY_D
	li	s4,KEY_L
	li	s5,KEY_R
	li	s6,KEY_B
	jal	input_get_keys_pressed
	move	t0,v0
	beq	t0,s2,_moveDown
	beq	t0,s3,_moveUp
	beq	t0,s4,_moveLeft
	beq	t0,s5,_moveRight
	beq	t0,s6,_moveB
	j	_finished
_moveDown:
 	ble    s1,0,_display_down
	beq	s1,zero,_keyCheck
	
	subi	s1,s1,1
	
	move	a0,s0
	move	a1,s1
	li	a2,5
	sw	s0,pixel_x(t1)
	sw	s1,pixel_y(t1)
_display_down:
	jal	display_set_pixel
	j	_finished
_moveUp:
	beq    s1,63,_display_up
	addi	s1,s1,1
	move	a0,s0
	move	a1,s1
	li	a2,5
	sw	s0,pixel_x(t1)
	sw	s1,pixel_y(t1)
_display_up:
	jal	display_set_pixel
	j	_finished
_moveLeft:
	beq    s0,0,_display_left
	beq	s0,zero,_keyCheck
	
	subi	s0,s0,1
	move	a0,s0
	move	a1,s1
	li	a2,5
	sw	s0,pixel_x(t1)
	sw	s1,pixel_y(t1)
_display_left:
	jal	display_set_pixel
	j	_finished
_moveRight:
  	beq    s0,63,_display_right
	addi	s0,s0,1
	move	a0,s0
	move	a1,s1
	li	a2,5
	sw	s0,pixel_x(t1)
	sw	s1,pixel_y(t1)
_display_right:
	jal	display_set_pixel
	j	_finished
_moveB:
 	addi    s7,s7,1
   	bne    	s7,3,_go
    	li	s7,0
_go:
    	move    a0,s7
    	jal    	pixel_get_element
    	move    t1,v0

    	lw    s0,pixel_x(t1) #pixel 0
    	lw    s1,pixel_y(t1)
    	lw    s2,pixel_selected(t1)

    	j	_keyCheck
    	#j	_finished
_finished:
	leave	s0,s1,s2,s3,s4,s5

# Perhaps implement this function?
pixel_find_selected:
	enter
	# Your code goes in here

	leave
