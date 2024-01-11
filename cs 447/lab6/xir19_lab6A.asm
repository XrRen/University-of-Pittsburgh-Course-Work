get_bit:
	lw	t0,(a0)
	move	t1,a1
  	srlv	t1,t0,t1
  	andi	t1,t1,0x01
  	move	v0,t1
	jr	ra
set_bit:
	move	t0,a0 #number
	move	t1,a1 #position
	li	t3,0x01 #mask
  	sllv	t3,t1,t3
  	or	t1,t0,t3
  	move	v0,t1
	jr	ra
reset_bit:
	move	t0,a0 #number
	move	t1,a1 #position
	li	t3,0x01 #mask
  	sllv	t3,t1,t3
  	not	t3,t3
  	and	t1,t0,t3
  	move	v0,t1
	jr	ra