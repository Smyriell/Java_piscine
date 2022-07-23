
#______ARGUMENTS INSTRUCTION______

#arg 0: <symbol>             specify the symbol that should be displayed in place of black pixels
#arg 1: <symbol>             specify the symbol that should be displayed in place of white pixels


#______TO COMPILE, ARCHIVE ASSEMBLY AND STARTUP INSTRUCTION______

rm -rf target
mkdir target
javac -d target src/java/edu/school21/printer/*/*.java
cp -R src/resources target
jar -cmf src/manifest.txt target/images-to-chars-printer.jar target
java -jar target/images-to-chars-printer.jar . 0

# the console should be opened in the project’s root folder
# arguments could be changed
