
#______ARGUMENTS INSTRUCTION______

#arg 0: <symbol>             specify the symbol that should be displayed in place of black pixels
#arg 1: <symbol>             specify the symbol that should be displayed in place of white pixels
#arg 2: <Absolute path>      the absolute path to the black-and-white BMP image on your disk


#______TO COMPILE AND STARTING THE PROGRAM FROM THE CONSOLE INSTRUCTION______

rm -rf target
mkdir target
javac -d target src/java/edu/school21/printer/*/*.java
java -cp target edu.school21.printer.app.Program . 0 /Users/smyriell/repo/java_psc/day04/ex00/ImagesToChar/src/java/it.bmp

# the console should be opened in the project’s root folder
# arguments could be changed