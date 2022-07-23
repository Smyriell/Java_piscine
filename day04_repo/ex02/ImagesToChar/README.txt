
#______ARGUMENTS INSTRUCTION______

#arg 0: --white<COLOR>             specify the color that should be displayed in place of black pixels
#arg 1: --black<COLOR>             specify the color that should be displayed in place of white pixels
#Avaliable colors:  BLACK  RED  GREEN  YELLOW  BLUE  MAGENTA  CYAN  WHITE  NONE


#______TO DOWNLOAD EXTERNAL LIBRARIES, COMPILE, ARCHIVE ASSEMBLY AND STARTUP INSTRUCTION______

rm -rf lib
mkdir lib
curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.2/JCDP-4.0.2.jar -o lib/JCDP-4.0.2.jar
curl https://repo1.maven.org/maven2/com/beust/jcommander/1.82/jcommander-1.82.jar -o lib/jcommander-1.82.jar

rm -rf target
mkdir target
cd target
jar xf ../lib/jcommander-1.82.jar
jar xf ../lib/JCDP-4.0.2.jar
rm -rf META-INF
cd ..

javac -d target -cp lib/\* src/java/edu/school21/printer/*/*.java
cp -R src/resources target
jar -cmf src/manifest.txt target/images-to-chars-printer.jar target
java -jar target/images-to-chars-printer.jar --white=WHITE --black=MAGENTA

# the console should be opened in the project’s root folder
# arguments could be changed
