Welcome to Version 1.0 of the Roche Sample Annotator! Here are a few things about the application thus far:

To add to the list of desired tests for the chemical samples and/or the charge numbers,
please edit the corresponding csv files in the system folder of the master folder.
Excel will yell at you in the saving process, just make sure it saves as a .csv file and keeps the formatting.
Overwriting the original file is not a problem.

The master folder is established upon the first run of the program and currently cannot be
modified once designated.

Images and their edited counterparts are saved in the same project folder, so the option to begin
a new project is only necessary when you wish for a series of images to be separate from another group.

---------------------------------------------------------------------------------------------------------------------------------------------------
One thing that is VITAL to get the program to run and requires some effort on your part (sorry) is adding "opencv_java310.dll" to the same folder
as the java PATH environment variable on your machine. Assuming your machine is running Windows, follow these steps to add this external library:

1. Go to Control Panel -> System and Security -> System -> Advanced Configuration -> Advanced Options Tab -> Environment Variables
2. From here, scroll down under System Variables until you see the PATH option. You should see a path similar to C:\Program Files\Java\jre1.8.0_71\bin
3. Open up file explorer (a different program on your Windows machine) and navigate to the path found in step 2.
4. In this folder, you should see a variety of files with the extension .dll. Please add "opencv_java310.dll" to this folder.

In the next version, I hope to make this an automatic process, but using code to mess with system properties like this has proven difficult
and will require more research on my part. I hope these instructions did not provide too much frustration!
---------------------------------------------------------------------------------------------------------------------------------------------------

Please let me know of any problems, questions, or concerns that arise. I can be easily reached
through email at chriscoraggio1@gmail.com.

Hope this software helps! It was exciting to develop something for real-world application!

Chris Coraggio