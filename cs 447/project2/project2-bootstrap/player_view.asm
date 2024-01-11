#Xirui Ren
## This file implements the functions that display the players based on the model

# Include the macros file so that we save some typing! :)
.include "macros.asm"
# Include the constants settings file with the board settings! :)
.include "constants.asm"
# We will need to access the player model, include the structure offset definitions
.include "player_struct.asm"

# This function needs to be called by other files, so it needs to be global
.globl player_draw

.text
#this will initialize the position of the player and the move of the player
player_draw:
	enter	s0,s1,s2,s3
	# Your code goes in here
	move	s1,a0 #x
	move	s2,a1 #y
	move	s3,a0 #store x
	# Get player s0
	li	a0,0
	jal	player_get_element
	mul	s1,s1,5
	mul	s2,s2,5
	mul	s3,s3,5
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)

	# Setup arguments with coordinates
	
	li	a0,1
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,2
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,3
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,4
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,5
	jal	player_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,6
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,7
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)

	li	a0,8
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,9
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,10
	jal	player_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,11
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,12
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,13
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,14
	jal	player_get_element
	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,15
	jal	player_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,16
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,17
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,18
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,19
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,20
	jal	player_get_element
	move	s1,s3
	addi	s2,s2,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,21
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,22
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,23
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	li	a0,24
	jal	player_get_element

	addi	s1,s1,1
	sw	s1,player_x(v0)
	sw	s2,player_y(v0)
	
	leave	s0,s1,s2,s3
