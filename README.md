# imdb-demo-2021

This project is based on the popular website IMDb which offers movies and TV shows information. They have kindly made their dataset publicly available at IMDb Datasets. Your mission, should you choose to accept it, is to write a web application in Java that can fulfil the following requirements:

 ●1.. Two Actor / Actress names are entered.  It is informed whether the two names played in the same movie.
 ●2.. Actor / Actress name entered.  Returning the details found about the movie he played.


                  ******************************************************
                  
To run the program and see the result you have to :

1- set spring.jpa.hibernate.ddl-auto for the first run : "create" and for the next runs : "update"

2- You need to install the Lombok plugin for intellij IDEA.

3- there are 3 tsv sample files in classPath:/datasets/
After Running program, JOBs will start and read the files then insert the records into the DB.

4- when jobs finish, you can check the queries by this link:
http://localhost:8080/swagger-ui.html#!/

5- click on title-basics-controler to see the REST-APIs.



you can also check the project documentation in "imdbDemo-apidocs.rar" file.

