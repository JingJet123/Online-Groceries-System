Users required to add in GlassFish Server Administration Console:
----------------------------------------------------------------
1. Open the GlassFish Server Domain Console at Netbeans IDE
2. Open "server-config" under Configurations 
3. Dropdown the "Security" then dropdown the "Realms"
4. Select "file"
5. Click the "Manage Users" button
6. Then click the "New" button to add users
Admin
=====
Username: jackieChan
Password: jackie@345
Staff 1
======
Username: tomHolland
Password: spider@Tom1	
Staff 2
======
Username: zendaya123
Password: zendaya@345
Staff 3
======
Username: andrew019
Password: andrewGar!123


Ways to redirect to admin side 
--------------------------------
- Remove "index.jsp" or any page and replace to "ToStaffDashboard" at the end of url


Database Authentication
------------------------
Database name: grocerydb
Username: nbuser
Password: nbuser

INSERT RECORDS BACKUP:
If not able to open the database and do not have any records, please refer the following steps:
1. Open the createsql.txt under sql folder to create the tables for the entities
2. Then uncomment the code at line 159 in index.jsp:
<a href="InsertRecordToDB">Insert Record</a>
NOTE: "InsertRecordToDB" file is located in java/"Source Packages" folder
3. Run the application and click the Insert Record under the Category dropdown in index.jsp webpage
4. After insert all the records, please re-run the application in order to have the records
5. Then comment back the code at line 159 in index.jsp in order to have the best UI view

DROP TABLE & DELETE RECORDS:
If accidentally made some errors regarding database, refer to the following steps:
1. Open the dropsql.txt under sql folder and drop all the entities table
2. Then repeat the steps for INSERT RECORDS again
