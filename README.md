# skimitly-restock-eval-app
This is an application to evaluate the restock algrithm used by skimitly. It evaluates a restocking algorithm against SkiMitly's actual order history. The two input files are supposed to contain the data of restock events and another to contain order events.

If this application determines that the algorithm was successful then the output will include:
	__SUCCESS__ message and the remaining inventory
Else output will include:
	__OUT OF STOCK__ message and which product SkiMitly ran out of, and when it ran out.
	
## Technical details
	Programming langauge: Java (version 1.8.0)
	Testing frameworks: JMockit (version 1.46), JUnit(version 4.12)
	Build tool: Apache Maven (version 3.6.0)
	
## Assumptions
1. Items in the oreders.json are the subset of items in the restocks.json file. In simple words, whatever are the products in the orders.json has to be there in the restocks.json file even if that particular item has restock quantity as 0 for all the months. If such item exist which is not there in restocks file then evaluation for that item will be skipped.
2. Both the files will have at least an empty array i.e. these two files should contain at least []. Having these files as blank, will generate the exception and the application will fail.
3. Though the application is supposed to generate either of the two expected outputs: SUCCESS or OUT OF STOCK, there is third possible output which is assumed. It will get generated when the restocks.json file has the empty array i.e. no restocking occurred in a year.
4. All the events are of the same year. While processing, this application takes into consideration only the month of all the events.
5. Whatever is the restock quantity in the month of January, it is the number of total items available in the inventory at the start of the year.

## Steps for evaluation
1. Read the file restocks.json
2. Generate the mapping of an item and its monthwise restock quantity
3. Read the file orders.json
4. Generate the mapping of an item and its monthwise order quantity
5. Use these two mappings to do the further processing of evaluation. Possible scenarios:
5.1 Item listed in the restocks file is also listed in the orders file - determine whether restock was successful or not. 
5.2 Item listed in the restocks file is not there in the orders file - calculate the sum of restock quantity for all the months for that item.
5.3 Mapping generated in the step 2 above is empty - return response containing the message as "No restock"


## Design decisions
The design decision is based on the intuition, experimenting with the sample data and the observations based on that. The very basic idea of the designing is that whatever is the quantity ordered in each month, needs to be subtracted from the quantity restocked in that month. Whatever is the leftover quantity needs to be carried over to the next month. If in any month, number of items oredered is less than [the number of items restocked + carried over quantity from the last month] then it is definitely OUT OF STOCK scenario. These steps will be repeated for each item restocked. 

## Instructions for running the application

**On Windows** (verified on Windows 10)

### Build steps
		
	Step 1:
	Make sure that JDK (at least version 8) is installed on the system. 
	It can be downloaded from https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html
	
	Step 2:
	The build tool used for this application is Maven. 
	It requires that JAVA_HOME environment variable is set. 
	The steps can be found at https://www.thewindowsclub.com/set-java_home-in-windows-10
	
	Step 3:
	Install git bash client. It can be downloaded from https://git-scm.com/download/win
	
	Step 4:
	After successful installations of JDK and git, go to any directory
	
	Step 5:
	Open git bash client in that directory and execute this command
	C:\> git clone https://github.com/skatkar/skimitly-restock-eval-app.git
	
	Step 6:
	Upon successfully downloading all the files from this git repository, go to the directory named skimitly-restock-eval-app.
	
	Step 7:
	Execute below command. Wait for the build to complete. At the end it will show message as BUILD SUCCESS and total time taken to build.
	C:\skimitly-restock-eval-app> mvnw.cmd clean package
	
	Step 8:
	Once the build is successful, it will generate the target directory at the same location. Go inside the directory and check whether two jar files named: restock-eval-app.jar and restock-eval-app-jar-with-dependencies.jar are generated or not.
	
	Step 9:
	If these jar files are verified then go back to the directory where the repository was cloned.
	
### Execute steps

	Step 1:
	At the same location, execute the below command. This step will use the json files downloaded from the git repository. These files are considered as default files.
	C:\skimitly-restock-eval-app> java -jar target/restock-eval-app-jar-with-dependencies.jar
			
	Step 2:
	If you want to use different json files than the available at this location then the application expects the accessible directory location.
	C:\skimitly-restock-eval-app> java -jar target/restock-eval-app-jar-with-dependencies.jar C:\input_files
	
	Make sure that both files (restocks.json and orders.json) are available at this location and are readable.
	
	Step 3:
	Verify the result displayed on the console
	
**On Ubuntu** (verified on Ubuntu 18.04)
### Build steps
		
	Step 1:
	Make sure that JDK (at least version 8) is installed on the system. 
	It can be downloaded from (https://thishosting.rocks/install-java-ubuntu/)
	
	Step 2:
	Install git bash client. It can be downloaded from (https://www.digitalocean.com/community/tutorials/how-to-install-git-on-ubuntu-18-04)
	
	Step 3:
	After successful installations of JDK and git, go to any directory
	
	Step 4:
	Clone the github repository using:
	ubuntu$ git clone https://github.com/skatkar/skimitly-restock-eval-app.git
			
	Step 5:
	If the code successfully cloned then go to the directory named skimitly-restock-eval-app
	
	Step 6:
	Provide the execute permission to the mvnw file using 
	ubuntu/skimitly-restock-eval-app$ chmod 754 mvnw
	
	Step 7:
	Build the code using 
	ubuntu/skimitly-restock-eval-app$ ./mvnw clean package
	
	Step 8:
	Next build steps are similar to the build steps for Windows (Mentioned above in Step 8 and 9)
	
### Execute steps
	Execute steps are similar to the steps for Windows mentioned above
