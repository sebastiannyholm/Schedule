			 #######################################
			 #######   	    READ ME 		########
			 #######       ----------       ########
#################### 	    Schedule        ######################
##################################################################			

INTRODUCTION
------------
The Schedule application displays and allows a company to plan
and schedule projects, tasks and individual assignments.
These tasks and assignments can be assigned to any available
employee, whom are displayed during task management and 
assignment creation.
The application allows for employee expansion and deletion.

The program features the registration of time worked on an
assignment along with the registration of absence in terms of
sickness or vacation.

The timekeeping system embedded within the program is based on
the Calendar and GregorianCalendar classes from the standard
import java.util library.


SYSTEM NAVIGATION
-----------------
When lauching the application for the first time the GUI will
be run and you will be greeted by the welcome panel.
Here you can log in using a 4-digit initials log in code.

The system has built-in 3 employees, two of which are admini-
strators and have full control over the system, including the 
removal of employees. The last employee is the Absence Manager.
The Absence Manager is in charge of monitoring and registering
the absence of employees.

The initials of the built-in employees are as follows:
  
		 Initials  |        Name        |   Role
		------------------------------------------
		  "seny"   |  Sebastian Nyholm  |   admin
		  "luvi"   |  Lukas Villumsen   |   admin
		  "abma"   |  Absence Manager   |  absence

Logging in as "seny" and you arrive at the Main Menu.
You have the following options:

	~ My agenda			~ My projects
	~ Check absence 	~ Manage employees
						~ Log out


"My agenda" 
	takes the user to his/her assignments for the day.
Here the user can pick a task and start working on it by click-
ing the "Check task" button.
In this area, the user can ask another employee for assistance
by filling out the form on the right.
When commencing work on the chosen task, all action needed from
the user, is for him/her to start the timer located on the bot-
tom left.
The employee can, if he/she so desires, change the registered
time as incicated on the panel.

"Check absence"
	transfers the user to the absence panel.
Here the user can quickly get an overview of which employees
are to be expected absent on the specific day (today).

"My projects"
	redirects the user to his/Her projects.
The possession of project is defined by being its project leader.
From here the user can create, delete and manage these projects.
When managing projects the user can, like with projects, create,
delete and manage tasks connected to the chosen project.
While managing tasks the user can create assignments. These 
assignments are individually tied to one unique employee.
"Hour of day" indicates at which point in time, on the chosen date,
the user is expected to do this job. 
Below a user can enter how many hours of work the employee chosen 
for the assignment is expected to finish in.

"Manage employees" (admin only)
	puts the user in a panel which offers to either add or remove 
an employee from the system.


REQUIREMENTS / JDK Version notes
--------------------------------
The program is developed at a JDK Compliance level of 1.8 and have
thus far been tested functional at a complicance level of 1.7


FAQ
---
Whenever a date is indicated to be selected, a small box labeled
"..." located just to the right of the field, will be displayed.
Clicking this button will open up a calendar view for easy
navigation through the dates, without having to worry about the
syntax.
