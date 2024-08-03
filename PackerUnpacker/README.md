
<h1>Packer and Unpacker Project</h1>
<h2>Project Title</h2>
<h3>Packer and Unpacker</h3>

<h2>Description</h2>
The Packer and Unpacker project provides a utility to compress multiple files into a single packed file and subsequently extract them back to their original form. This tool is useful for bundling files for easy distribution and storage.
<br><br>

<h2>Installation</h2>
<h3>Prerequisites</h3>
         1. Java Development Kit (JDK) 8 or higher<br>
         2. A text editor or Integrated Development Environment (IDE)<br>
         3. Command-line interface (CLI) or terminal<br>
<h3>Installation Steps</h3>
<ul>
<h5>1.Clone the Repository:</h5>
                                    
                 git clone https://github.com/bharatigore/ProJavaHub.git
                 cd PackerUnpacker
<h5>2.Navigate to Source Directory:</h5>

                  cd src
<h5>3.Compile Java Files:</h5>
                                            
                  javac *.java                            

<h5>4.Create JAR File:</h5>
Ensure you have manifest.txt and MANIFEST.MF files created as below:<br>
                 
                  manifest.txt:
                  Main-Class: PackerMain
MANIFEST.MF:
             
                  Manifest-Version: 1.0
                  Main-Class: PackerMain
Run the following commands to create the JAR file:

                  jar -cvfm packer.jar manifest.txt *.class
Verify the JAR file 

                   jar -tf packer.jar
</ul>
<h3>Usage</h3>
To run the Packer and Unpacker project, use the following command:

                     java -jar packer.jar
<h2>Features</h2>
<b>Packing: </b>Compress multiple files into a single packed file.<br>
<b>Unpacking: </b>Extract files from a packed file to their original state.<br>
<b>Graphical User Interface (GUI):</b> User-friendly GUI for packing and unpacking files.<br>
<b>Command-Line Interface (CLI):</b> Optional CLI for advanced users.<br>
<h3>Contributing</h3>
To contribute to this project:

Fork the repository.<br>
Create a new branch for your feature or bugfix.<br>
Commit your changes and push the branch.<br>
Open a Pull Request with a detailed description of your changes.<br>
