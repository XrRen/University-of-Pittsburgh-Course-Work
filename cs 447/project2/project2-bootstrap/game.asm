## Xirui Ren xir19
.include "constants.asm"
.include "macros.asm"
.include "pixel_struct.asm"
.include "player_struct.asm"
.include "box_struct.asm"

.globl	game
.globl	board
.globl	player
.globl	box_win
.globl	matrix_element_address
.data
board:		.word  1, 1, 1, 1, 1, 1, 1, 1# creates a 9x8 matrix that will represent the board
         	.word  1, 0, 0, 0, 0, 0, 0, 1
         	.word  1, 1, 1, 0, 0, 0, 0, 1
         	.word  1, 1, 2, 0, 0, 0, 0, 1
         	.word  1, 1, 1, 1, 0, 0, 2, 1
         	.word  1, 0, 0, 0, 0, 0, 1, 1
         	.word  1, 0, 0, 0, 0, 0, 0, 1
        	.word  1, 0, 0, 2, 0, 0, 0, 1
         	.word  1, 1, 1, 1, 1, 1, 1, 1
         	
player:		.byte -1, -1, 6, -1, -1 # this represent the player
		.byte  6, 5, 5, 5, 6
		.byte -1, -1, 1, -1, -1
		.byte -1, 1, -1, 1, -1
		.byte -1, 7, -1, 7, -1 
		
box: 		.byte 11, 11, 11, 11, 11 # this represent the box
		.byte 11, 10, 11, 10, 11
		.byte 11, 11, 10, 11, 11
		.byte 11, 10, 11, 10, 11
		.byte 11, 11, 11, 11, 11
		
box_win: 	.byte 10, 10, 10, 10, 10 # this represent the box after land on the target
		.byte 10, 11, 10, 11, 10
		.byte 10, 10, 11, 10, 10
		.byte 10, 11, 10, 11, 10
		.byte 10, 10, 10, 10, 10
.text

array_element_address:
	push	ra
	# Row address is the base address + index * sizeof(row) = a0 + a1*(sizeof(word)*n_elements in row) = a0 + a1*(4*a2)
	mul	t0, a1, 4
	add	v0, a0, t0
	pop	ra
	jr	ra



# This function calculates the address of the element (i, j) in a matrix of words
# Inputs:
#	 a0: The base address of the matrix
#	 a1: The index (i) of the row
#	 a2: The index (j) of the column
#	 a3: The number of elements in a row
# Outputs:
#	 v0: The address of the element
matrix_element_address:  # Use this function in your project!
	push	ra
	# Row address is the base address + index * sizeof(row) = a0 + a1*(sizeof(word)*n_elements in row) = a0 + a1*(4*a3)
	mul	t0, a3, 4
	mul	t0, t0, a1
	add	v0, a0, t0
	# Element address is the row address + index * sizeof(element) = row address + a2*4
	mul	t0, a2, 4
	add	v0, v0, t0
	pop	ra
	jr	ra
# This method will display the the total move of the player made during one game
moves_dis:

	enter
	li	a0, 0
	li	a1, 53
	li	a2, 64
	li	a3,COLOR_BLUE
	# This is a macro defined in macros.asm
	jal	display_draw_hline

	li	a0, 2
	li	a1, 56
	# This is a macro defined in macros.asm
	lstr	a2, "MOVES: "
	jal	display_draw_text
	
	li	a0,0
	jal	player_get_element
	lw	t2,player_move(v0) #this will get the total move of the player
	

	li	a0,37
	li	a1,56
	move	a2,t2
	jal	display_draw_int

	leave

game:
	enter s0,s1,s2,s3,s4
	

_game_while:

	jal	handle_input # we first get the user's input

	li	a0, 2
	li	a1, 3
	lstr	a2, "Press Any"
	# This is a macro defined in macros.asm
	jal	display_draw_text

	li	a0, 2
	li	a1, 12
	lstr	a2, "Key to "
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0, 2
	li	a1, 21
	lstr	a2, "Begin the"
	# This is a macro def
	jal	display_draw_text
	
	li	a0, 2
	li	a1, 30
	lstr	a2, "Game"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0, 2
	li	a1, 39
	lstr	a2, "Press X to" 
	# This is a macro defined in macros.asm
	jal	display_draw_text

	li	a0, 2
	li	a1, 48
	lstr	a2, "Exit the"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0, 2
	li	a1, 57
	lstr	a2, "Game"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	jal	display_update_and_clear
	jal	wait_for_next_frame
	
	#any k except x will start the game
	lw	t0, x_pressed
	bnez	t0, _game_end
	
	lw	t0,up_pressed
	bnez	t0,_game_initial
	
	lw	t0,down_pressed
	bnez	t0,_game_initial
	
	lw	t0,left_pressed
	bnez	t0,_game_initial
	
	lw	t0,right_pressed
	bnez	t0,_game_initial
	
	lw	t0,b_pressed
	bnez	t0,_game_initial
	
	lw	t0,z_pressed
	bnez	t0,_game_initial
	
	lw	t0,c_pressed
	bnez	t0,_game_initial

	j	_game_while
	# stay the sane if no perfered key pressed
	# Leave if x was pressed	
_game_end:	#when x is presses
	li	a0, 5
	li	a1, 19
	lstr	a2, "Thank You"
	# This is a macro defined in macros.asm
	jal	display_draw_text

	li	a0, 23
	li	a1, 28
	lstr	a2, "For"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0, 11
	li	a1, 37
	lstr	a2, "Playing"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	# Clear the screen
	jal	display_update_and_clear
	jal	wait_for_next_frame

	leave	s0,s1,s2,s3,s4
	
_game_win:	#when all the boxes are on the targets
	li	a0, 2
	li	a1, 16
	lstr	a2, "Yeah!!!"
	# This is a macro defined in macros.asm
	jal	display_draw_text

	li	a0, 2
	li	a1, 25
	lstr	a2, "You Win"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0, 2
	li	a1, 34
	lstr	a2, "With"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	li	a0,0
	jal	player_get_element
	lw	t2,player_move(v0) #get the moves 
	

	li	a0,30
	li	a1,34
	move	a2,t2
	jal	display_draw_int 	#display the total moves
	
	li	a0, 2
	li	a1, 43
	lstr	a2, "Moves"
	# This is a macro defined in macros.asm
	jal	display_draw_text
	
	# Clear the screen
	jal	display_update_and_clear
	jal	wait_for_next_frame

	leave	s0,s1,s2,s3,s4
_game_initial: ## we will intial all the player's data and boxes' data
	addi	s3, s3,1
	li	a0,4
	li	a1,4
	jal	player_draw
	
	li	a0,6
	li	a1,3
	li	a2,0
	jal	box_draw
	
	li	a0,3
	li	a1,3
	li	a2,25
	jal	box_draw
	
	li	a0,3
	li	a1,6
	li	a2,50
	jal	box_draw
	
_game_begin: #begin the game
_main_row_loop:
	jal	moves_dis	#display the moves
	bge	s1, 9, _main_row_loop_end

	# Iterate all columns in a for loop
	# for(s1=0; s1<9; s1++)
	li	s2, 0
_main_col_loop:
	bge	s2, 8, _main_col_loop_end
	# for(s2=0; s2<8; s2++)
	# Get address of element i,j of matrix
	la	a0, board
	move	a1, s1 #y
	move	a2, s2 #x
	li	a3, 8
	jal	matrix_element_address

	# Load element from matrix from memory and print it
	lw	t1, (v0)
	beq	t1,1,_draw_pixel	#if it's one, it means it will be  wall
	beq	t1,2,_draw_target	#if t1 = 2, it means it will be a target
	move	a0,s2			#else, it will be a empty bilt
	move	a1,s1
	
	jal	empty_draw
	j	_continus
_draw_pixel:
	#this draws one piece of the wall
	move	a0,s2
	move	a1,s1
	jal	board_draw
	j	_continus

_draw_target:
	#this draws one piece of the target
	move	a0,s2
	move	a1,s1
	jal	target_draw

_continus:
	addi	s2, s2, 1	#incurment the counter
	j	_main_col_loop
_main_col_loop_end:		
	# The for loop iterating all columns ends here!!

	addi	s1, s1, 1
	j	_main_row_loop
	
_main_row_loop_end:
	
	li	s1, 0
_play:
	# Must update the frame and wait
	jal	player_update	#now we can move the player and push the boxes
	
	li	a0,0
	jal	box_get_element
	lw	s0,box_target(v0)	#after the player pushed the box, we want to know whether the boxes is on a target or not
	
	beqz	s0,_not_win	#if the first box not on the target, then for sure not win
	li	a0,0	#if it is on the target, we change the look of the box
	jal	box_get_element
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	la	a2,box_win
	jal	display_blit_5x5

_check_box2:	#then we check the second box, if it's on the target or not
	li	a0,25
	jal	box_get_element
	lw	s0,box_target(v0)
	beqz	s0,_not_win	#if not, we no we havn't win yet
	li	a0,0
	jal	box_get_element
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	la	a2,box_win	#else, change the looj of the second box and check the thrid box
	jal	display_blit_5x5_trans
_check_box3:
	li	a0,50
	jal	box_get_element
	lw	s0,box_target(v0)
	beqz	s0,_not_win
	li	a0,0
	jal	box_get_element
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	la	a2,box_win	#if on that target, change the look, and we know we win, so we display the win page.
	jal	display_blit_5x5_trans
_win:
	jal	display_update_and_clear
	jal	wait_for_next_frame
	j	_game_win
_not_win:	#if not win, display the box wheter it's on the target or not.
		
	li	a0,0
	jal	box_get_element
	lw	s0,box_target(v0)
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	
	beqz	s0,_notWin1
	la	a2,box_win
	jal	display_blit_5x5_trans
	j	_cont1
	
_notWin1:
	la	a2,box
	jal	display_blit_5x5_trans
_cont1:
	li	a0,25
	jal	box_get_element
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	lw	s0,box_target(v0)
	beqz	s0,_notWin2
	la	a2,box_win
	jal	display_blit_5x5_trans
	j	_cont2
_notWin2:
	la	a2,box
	jal	display_blit_5x5_trans
_cont2:
	li	a0,50
	jal	box_get_element
	lw	a0,box_x(v0)
	lw	a1,box_y(v0)
	lw	s0,box_target(v0)
	beqz	s0,_notWin3
	la	a2,box_win
	jal	display_blit_5x5_trans
	j	_final
_notWin3:
	la	a2,box
	jal	display_blit_5x5_trans
_final:
	li	a0,0
	jal	player_get_element
	lw	a0,player_x(v0)
	lw	a1,player_y(v0)
	la	a2,player
	jal	display_blit_5x5_trans
	
	
	jal	display_update_and_clear
	jal	wait_for_next_frame
	j	_game_begin	#loop back to the beginning of the game
	
	
	
