1
CS 149 / SE 149
Operating Systems
Spring Semester 2015
Department of Computer Science
San José State University
Instructor: Ron Mak
Assignment #1
Assigned: Tuesday, January 27
Due: Monday, February 2 at 11:59 pm
Team assignment, 100 points max
Linux up and running
The purpose of this assignment is to give everyone practice installing Linux in a virtual
machine. You may choose Debian (preferred) or Ubuntu.
Virtual machine
Install VirtualBox. Then install Linux (Debian or Ubuntu) in a virtual machine controlled
by VirtualBox.
What to run
Start up Linux in a in a virtual machine. Open a terminal window and first run the
command
grep username /etc.passwd
Where username is your username that you chose while installing Linux. Then compile
and run forktest.c
gcc –o forktest forktest.c
./forktest
What to turn in
This is a team assignment, so each team should collect a screenshot from each team
member. Name each screenshot file after your team name followed by the member’s
name, such as SuperCoder-Jones.png. In order for the team to get a perfect score,
each screenshot must be good, so help each other get Linux up and running!
2
Each team should email the zip file to ron.mak@sjsu.edu Your subject line should be:
CS 149-section Assignment #1 team name
Be sure to CC all the members of your team so that when I send you your team score, I
can just do a “Reply all”.
forktest.c:
#include <stdio.h>
main()
{
 printf("Parent: Process started\n");
 printf("Parent: Forking a child.\n");

 if (fork() != 0) {
 // Parent
 int status;
 printf("Parent: Wait for child to complete.\n");
 waitpid(-1, &status, 0);
 printf("Parent: Terminating.\n");
 }
 else {
 // Child
 printf("Child: Process started.\n");
 printf("Child: Start 10 second idle:");

 int i;
 for (i = 10; i >= 0; i--) {
 printf("%3d", i); fflush(stdout);
 sleep(1);
 }
 printf(" done!\n");
 printf("Child: Terminating.\n");
 }
}
3
Example screenshot: