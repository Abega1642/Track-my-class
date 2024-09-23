# Track my class

Track my class is an attendance management app for HEI that tracks student absences and automates the COR process after 3 unjustified absences. 
It simplifies attendance tracking with real-time reports and easy management for administrators. 
The app ensures efficient monitoring of student participation while reducing manual effort.


# About the app:

## Client side :

This application will make life easier for teachers


In fact, it will just be enough for them to give the list of those who are absent during a race and boom it's over, 
the presence is already done with all the essential information necessary

And more, this application will help teachers in the difficulties of group attendance and a whole group combined. 

Indeed, it is often very difficult to make a presence during a private lesson with a particular group, but it is even 
worse to do it when the group is together in the race. For example, to attend a PROG2 course with J1 only, 
it is still possible; but when J1 and J2 are together during the same PROG2 course, it's a hassle to mark each time 
"p" for each student present and "a" for those who are absent.

This is where this `TRACK MY CLASS` app comes in.
The teacher will just need to list the students who are absent during their lessons (excused absence or not) and that's 
it! 

He/she will not have to worry about having marked this or that student because everything has been done by `Track My Class`
But one more important thing, no need to bother checking the attendance sheets to see unjustified absences throughout the month,... No, it's over! Track my class will do this for you.
In fact, it will only take one click and that's all you will find there all the people who need to switch to COR.

#### But how does it all work ?

## Technical side:

### POSTGRESQL directory :

This folder contains a file named `track_my_class.sql`.
This file contains the database structure used by this application.

It also contains some fictitious data to carry out small tests on the application.

It will therefore be enough to execute this file to create the POSTGRESQL database `track_my_class`.

### OPEN API SPECIFICATION directory :

This folder contains a file named `TrackMyClass.yaml` which contains all api specification of this application

### Controllers package :

This contains every controller for this applications `[AttendanceController, CorController, CourseController, DelayController, GroupAndLevelController, MissingController, StudentController, TeacherController]`.

Those controllers interact with `services` package.

### Services package :

This package contains some other package for every controller mentioned above such as : 
`attendanceServices`, `corServices`, `courseServices`, `delayServices`, `groupAndLevelServices`,
`missingServices`, `studentServices`,`teacherServices`.

Each of those mentioned package are responsible for every services that are available for each entity that they work for.

And all of them needs `DAO` dependencies.

### DAO package :

This DAO package holds every SQL request and operation for all services mentioned above.

### DTO package :

This package contains all classes that have direct interaction with the client.
Those classes are the simple representation of all entities that they represent behind.

### Entity package :

In this package are hold every entity that are useful for this applications.
But not only that, but it also contains some other packages like `matchers` and `mergers` that are so important for matching entities and merging entities.

### Exception handler package:

This package holds every classes that are used to handle every exception in this application

### RestException package: 

This package contains the class which is responsible for the rest exception handler of this application.


