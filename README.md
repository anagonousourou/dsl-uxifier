# UXifier
the implementation of a DSL focused on the specification of a specific family of UI

__Authors__:
  * [ANAGONOU Patrick]()
  * [ANIGLO Jonas]()
  * [FRANCIS Anas]()
  * [ZABOURDINE Soulaiman]()

## Repository Organization

  * `uxfier` implementation of DSL
  * `subject` the pdf subject of the project
  * `report` the pdf report the project
  * `ecommerce` mock backend for displaying products for testing
  * `uml` diagram of classes


## Test

Then in another terminal go in the folder `uxfier` run the command `gradlew run --args app.uxifier`

## Edition
To edit the code or create new scripts open `uxfier` in IntellIJ
Go to File > Settings 

Search File Types (in the Editor subsection of Settings)

Add the pattern `*.uxifier` to the groovy files.

Open the file `uxfier/app/src/main/groovy/uxifier/eshop.gdsl` in IntelliJ. A message should be displayed on top of the editor , click *Activate* to enable the GroovyDSL support

Now you can create files with the extension `uxifier` in the default package (under `uxfier/app/src/main/groovy/` directory) and you should get autocompletion, syntax highlight and type checks for your script.

To generate the code run `gradlew run --args <path/to>/<script name>.uxifier` in a terminal open in the uxfier directory.

A new folder should be created under `uxfier/app` go inside the folder and run `ǹpm install` then `npm run serve` 

Go to http://localhost:9000 and look the result