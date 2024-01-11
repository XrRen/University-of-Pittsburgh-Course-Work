## Xirui Ren xir19

## This file implements the functions that control the players based on the keyboard input


.include "macros.asm"

.include "constants.asm"
# We will need to access the player model, include the structure offset definitions
.include "player_struct.asm"
.include "box_struct.asm"



.globl player_update


.text

# for different boxes, we know every starting pixel of the box, 0, 25, 50,75. So we can use call it directly


player_update:
	enter	s0,s1,s2,s3,s4,s5
	# Your code goes in here
	li	t0,0 #get the total amount of the players
	li	t6,0
	li	t3,0
	li	s5,0
	li	t7,0
	
	#bne	s4,zero,_select
		
_keyCheck: # this will check what key did the player pressed
	li	s2,KEY_UP
	li	s3,KEY_DOWN
	li	s4,KEY_LEFT
	li	s0,KEY_RIGHT
	jal	input_get_keys_pressed
	move	t0,v0
	beq	t0,s2,_moveUp
	beq	t0,s3,_moveDown
	beq	t0,s4,_moveLeft
	beq	t0,s0,_moveRight 
	j	_finished
	
_moveUp: #here is the move up operation
	li	s5,0
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row	#we first get the current postion of the player
	lw	s0,player_x(v0) #col
	subi	s1,s1,5		#then we subtract 5 from the y postion
	div	s1,s1,5		#and get the postion that correspond to the board position
	div	s0,s0,5
	
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_up	#if it the board position is a wall, we know we are not able to move
	
	li	a0,0			#else, we got the box position and compare it with our player position
					# if the box position is right on top the the player and the payer pressed move up, we now will check the box with the wall
					# if the box is different, we check the next box until we find the box that the player actually wants to move
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s1,s1,5
	
	bne	s2,s0,_check_box_2_u
	bne	s1,s3,_check_box_2_u
	li	s5,0
	j	_check_againist_u_wall
_check_box_2_u:
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s1,s1,5
	
	bne	s2,s0,_check_box_3_u
	bne	s1,s3,_check_box_3_u
	li	s5,25
	j	_check_againist_u_wall
_check_box_3_u:
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s1,s1,5
	
	bne	s2,s0,_playerU_loop
	bne	s1,s3,_playerU_loop
	li	s5,50
	#after we know the postion of the box that the player actually want to move, now we have to check wether it's againist a wall and able to move or not.
_check_againist_u_wall:
	move	a0,s5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s1,s1,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_up
	beq	t1,2,_on_up_target
	#after we know the position we want to move it neither a wall or target, we check if it's a box position
_check_againist_box_u:
	move	a0,s5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s1,s1,5
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_2_ub 	#we check box by box. if it is a box position, we do nothing
	bne	s1,s3,_check_box_2_ub
	j	_display_up
_check_box_2_ub:
	move	a0,s5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s1,s1,5	
	
	li	a0,25	#we check box by box. if it is a box position, we do nothing
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_3_ub
	bne	s1,s3,_check_box_3_ub
	j	_display_up
_check_box_3_ub:
	move	a0,s5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s1,s1,5
	
	li	a0,50	#we check box by box. if it is a box position, we do nothing
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_box_move_up
	bne	s1,s3,_box_move_up
	j	_display_up

_on_up_target:	#if the postion we want to move is a target, we need to update the target flag and store the new box position and new player position
	move	a0,s5
	jal	box_get_element
	lw	s4,box_target(v0)
	li	s4,1
	sw	s4,box_target(v0)
	
	lw	a0,box_x(v0)
	
	lw	a1,box_y(v0)
	subi	a1,a1,5
	la	a2,box_win
	jal	display_blit_5x5
	sw	a1,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	lw	s1,player_y(v0)
	subi	s1,s1,5
	sw	s1,player_y(v0)
	
	j	_display_up
_box_move_up:	#we move the box up
	move	a0,s5
	jal	box_get_element
		
	lw	s1,box_y(v0)
	
	subi	s1,s1,5
	
	sw	s1,box_y(v0)
	addi	s5,s5,1
	ble	s5,t4,_box_move_up
_playerU_loop:	#we move the player up
	
	move	a0,t6
	jal	player_get_element
		
	lw	s1,player_y(v0)
	
	subi	s1,s1,5
	
	sw	s1,player_y(v0)
	
	addi	t6,t6,1
	ble	t6,25,_playerU_loop

_display_up:	#then we incurment the total moves made after a successful movement and display it
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_move(v0)
	addi	s1,s1,1
	sw	s1,player_move(v0)
	
	lw	a0,player_x(v0)
	lw	a1,player_y(v0)
	la	a2,player
	jal	display_blit_5x5_trans

	
	j	_finished
	
_moveDown:	#this will move the player and box down if both of them are movable
	li	t5,0
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0)
	lw	s0,player_x(v0)
	
	addi	s1,s1,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_down
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s1,s1,5
	
	bne	s2,s0,_check_box_2_d
	bne	s1,s3,_check_box_2_d
	li	t5,0
	j	_check_againist_d_wall
	#we got the box position and compare it with our player position
	# if the box position is right below the the player and the payer pressed move down, we now will check the box with the wall
	# if the box is different, we check the next box until we find the box that the player actually wants to move
_check_box_2_d:
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s1,s1,5
	
	bne	s2,s0,_check_box_3_d
	bne	s1,s3,_check_box_3_d
	li	t5,25

	j	_check_againist_d_wall
_check_box_3_d:
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s1,s1,5
	
	bne	s2,s0,_playerD_loop
	bne	s1,s3,_playerD_loop
	li	t5,50


_check_againist_d_wall:
	move	a0,t5
	jal	box_get_element
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s1,s1,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_down
	beq	t1,2,_on_down_target
#we will check if the position we want to move to have a box or not
_check_againist_box_d:
	move	a0,t5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s1,s1,5
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_2_db
	bne	s1,s3,_check_box_2_db
	j	_display_up
_check_box_2_db:
	move	a0,t5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s1,s1,5
	
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_3_db
	bne	s1,s3,_check_box_3_db
	j	_display_down
_check_box_3_db:
	move	a0,t5
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s1,s1,5
	
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_box_move_down
	bne	s1,s3,_box_move_down
	j	_display_down
#	if the position is a target, we change the win flag of the box to true.
_on_down_target:
	move	a0,t5
	jal	box_get_element
	li	s4,1
	sw	s4,box_target(v0)
	
	lw	a0,box_x(v0)
	
	lw	a1,box_y(v0)
	addi	a1,a1,5
	la	a2,box_win
	jal	display_blit_5x5
	sw	a1,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	lw	s1,player_y(v0)
	addi	s1,s1,5
	sw	s1,player_y(v0)
	
	j	_display_down
_box_move_down:
	move	a0,t5
	jal	box_get_element
		
	lw	s1,box_y(v0)
	
	addi	s1,s1,5
	
	sw	s1,box_y(v0)
	
	addi	t5,t5,1
	ble	t5,t4,_box_move_down

	
_playerD_loop:
	
	move	a0,t6
	jal	player_get_element
		
	lw	s1,player_y(v0)
	
	addi	s1,s1,5
	
	sw	s1,player_y(v0)
	
	addi	t6,t6,1
	ble	t6,25,_playerD_loop
	j	_display_down

_display_down:
	li	a0,0
	jal	player_get_element
	
	lw	t2,player_move(v0)
	addi	t2,t2,1
	sw	t2,player_move(v0)
	
	lw	a0,player_x(v0)
	lw	a1,player_y(v0)
	la	a2,player
	jal	display_blit_5x5_trans
	
	j	_finished
	
_moveLeft:
	li	t8,0
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0)
	lw	s0,player_x(v0)
	
	subi	s0,s0,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_left
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s0,s0,5
	
	bne	s2,s0,_check_box_2_l
	bne	s1,s3,_check_box_2_l
	li	t8,0
	j	_check_againist_l_wall
	#else, we got the box position and compare it with our player position
	# if the box position is at the left of the the player and the payer pressed move left, we now will check the box with the wall
	# if the box is different, we check the next box until we find the box that the player actually wants to move
_check_box_2_l:
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s0,s0,5
	
	bne	s2,s0,_check_box_3_l
	bne	s1,s3,_check_box_3_l
	li	t8,25
	j	_check_againist_l_wall
_check_box_3_l:
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	subi	s0,s0,5
	
	bne	s2,s0,_playerL_loop
	bne	s1,s3,_playerL_loop

	li	t8,50

_check_againist_l_wall:
	move	a0,t8
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	subi	s0,s0,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_left
	beq	t1,2,_on_left_target
# then we want to check whether the box we want to move is againist a wall or not
_check_againist_box_l:
	move	a0,t8
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s0,s0,5
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_2_lb
	bne	s1,s3,_check_box_2_lb
	j	_display_up
_check_box_2_lb:
	move	a0,t8
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s0,s0,5
	
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_3_lb
	bne	s1,s3,_check_box_3_lb
	j	_display_left
_check_box_3_lb:
	move	a0,t8
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	subi	s0,s0,5
	
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_box_move_left
	bne	s1,s3,_box_move_left
	j	_display_left
# if the position we want to move to is a target, then we change the target flag to 1.
_on_left_target:
	move	a0,t8
	jal	box_get_element
	li	s4,1
	sw	s4,box_target(v0)
	
	lw	s0,box_x(v0)
	subi	s0,s0,5
	move	a0,s0
	lw	a1,box_y(v0)
	la	a2,box_win
	jal	display_blit_5x5
	sw	s0,box_x(v0)

	li	a0,0
	jal	player_get_element
	lw	s0,player_x(v0)
	subi	s0,s0,5
	sw	s0,player_x(v0)
	
	j	_display_left
_box_move_left:
	move	a0,t8
	jal	box_get_element
		
	lw	s0,box_x(v0)
	
	subi	s0,s0,5
	
	sw	s0,box_x(v0)
	
	addi	t3,t3,1
	ble	t3,t4,_box_move_left
	
_playerL_loop:
	
	move	a0,t6
	jal	player_get_element
		
	lw	s0,player_x(v0)
	
	subi	s0,s0,5
	
	sw	s0,player_x(v0)
	
	addi	t6,t6,1
	ble	t6,25,_playerL_loop
	j	_display_left
_display_left:
	li	a0,0
	jal	player_get_element
	
	lw	t2,player_move(v0)
	addi	t2,t2,1
	sw	t2,player_move(v0)
	
	lw	a0,player_x(v0)
	lw	a1,player_y(v0)
	la	a2,player
	jal	display_blit_5x5_trans
	
	j	_finished	
	
_moveRight:
	li	t9,0
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0)
	lw	s0,player_x(v0)
	
	addi	s0,s0,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_left
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s0,s0,5
	
	bne	s2,s0,_check_box_2_r
	bne	s1,s3,_check_box_2_r
	li	t9,0
	j	_check_againist_r_wall
#else, we got the box position and compare it with our player position
# if the box position is at the right of the the player and the payer pressed move right, we now will check the box with the wall
# if the box is different, we check the next box until we find the box that the player actually wants to move
_check_box_2_r:
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s0,s0,5
	
	bne	s2,s0,_check_box_3_r
	bne	s1,s3,_check_box_3_r
	li	t9,25
	j	_check_againist_r_wall
_check_box_3_r:
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	li	a0,0
	jal	player_get_element
	
	lw	s1,player_y(v0) #row
	lw	s0,player_x(v0) #col
	addi	s0,s0,5
	
	bne	s2,s0,_playerR_loop
	bne	s1,s3,_playerR_loop

	li	t9,50

_check_againist_r_wall:
	move	a0,t9
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	addi	s0,s0,5
	div	s1,s1,5
	div	s0,s0,5
	
	la	a0, board
	move	a1,s1
	move	a2,s0
	li	a3,8
	jal	matrix_element_address
	
	lw	t1, (v0)
	
	beq	t1,1,_display_right
	beq	t1,2,_on_right_target
#after that, we check whether there's box againist the box we want to move or not
_check_againist_box_r:
	move	a0,t9
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s0,s0,5
	
	li	a0,0
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_2_rb
	bne	s1,s3,_check_box_2_rb
	j	_display_up
_check_box_2_rb:
	move	a0,t9
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s0,s0,5
	
	li	a0,25
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_check_box_3_rb
	bne	s1,s3,_check_box_3_rb
	j	_display_right
_check_box_3_rb:
	move	a0,t9
	jal	box_get_element
	
	lw	s1,box_y(v0)
	lw	s0,box_x(v0)
	
	addi	s0,s0,5
	
	li	a0,50
	jal	box_get_element
	lw	s2,box_x(v0)
	lw	s3,box_y(v0)
	
	bne	s2,s0,_box_move_right
	bne	s1,s3,_box_move_right
	j	_display_right
	
#if the position we want to move is a target, we change the target flag of the box to 1 to represent it's on the target.
_on_right_target:

	move	a0,t9
	jal	box_get_element
	li	s4,1
	sw	s4,box_target(v0)
	
	lw	s0,box_x(v0)
	addi	s0,s0,5
	move	a0,s0
	lw	a1,box_y(v0)
	sw	s0,box_x(v0)

	li	a0,0
	jal	player_get_element
	lw	s0,player_x(v0)
	subi	s0,s0,5
	sw	s0,player_x(v0)
	
	j	_display_right
_box_move_right:
	move	a0,t9
	jal	box_get_element
		
	lw	s0,box_x(v0)
	
	addi	s0,s0,5
	
	sw	s0,box_x(v0)
	
	addi	t3,t3,1
	ble	t3,t4,_box_move_right
	
_playerR_loop:
	
	move	a0,t6
	jal	player_get_element
		
	lw	s0,player_x(v0)
	
	addi	s0,s0,5
	
	sw	s0,player_x(v0)
	
	addi	t6,t6,1
	ble	t6,25,_playerR_loop
_display_right:
	li	a0,0
	jal	player_get_element
	
	lw	t2,player_move(v0)
	addi	t2,t2,1
	sw	t2,player_move(v0)
	
	lw	a0,player_x(v0)
	lw	a1,player_y(v0)
	la	a2,player
	jal	display_blit_5x5_trans

#we exit after move is done.
_finished:
	
	
	leave	s0,s1,s2,s3,s4,s5
