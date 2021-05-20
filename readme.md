## Name Sorter

This is a simple application to sort a list of names in a file in alphabetical order first by last 
name and then by any given names.

#### Input Data
##### Data validity
* Should only contain letters and new line characters
* Special characters and numbers will be considered as invalid data
##### Data Preparation
* Each name should be recorded on a new line (Empty lines will be removed from the result)
* A name should contain at-least one given name and a last name
* Names should be in order `fname(1) ... fname(n) lname`

#### Build
Clone project
```
git clone https://github.com/pubudusitinamaluwa/name-sorter
```
Switch to project directory and run
```
./gradlew assemble
```
Assembled Jar `name-sorter.jar` can be found in `build/libs`

#### Execution
The application requires a path to a file with names to sort as the only required argument.
```
java -jar /path/to/name-sorter.jar /path/to/unsorted-names-list.txt
```
##### Result:
* Sorted name list will be printed to console
* `sorted-names-list.txt` file will be created in working directory with sorted names
* Every run will overwrite `sorted-names-list.txt` if already present
