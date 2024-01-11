# Xirui Ren (xir19)
.globl main
main:   #is a lable, represent the memory address of function main
# li instruction means load immediate 
li	a0, 0x4321 #hex number
# a0 is an argument register
li	v0, 1
syscall		#system call
