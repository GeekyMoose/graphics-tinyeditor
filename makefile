# -----------------------------------------------------------------------------
# Task1 Computer Graphics
#
# Date:		March 7, 2016
# Author:	Constantin MASSON
# -----------------------------------------------------------------------------

JFLAGS		= -g
JC			= javac
JVM			= java
.SUFFIXES	= .java .class

BUILD		= target
BIN			= bin


.PHONY: all
all: clean growthTree cpResources
	$(JC) -d $(BUILD) -sourcepath src src/main/java/com/tinyeditor/MainApp.java

.PHONY: run
run:
	$(JVM) -cp $(BUILD) main.java.com.tinyeditor.MainApp

.PHONY: clean
clean:
	-rm -rf $(BUILD)
	-rm -rf $(BIN)

.PHONY: growthTree
growthTree:
	@mkdir $(BUILD)
	@mkdir $(BIN)

.PHONY: cpResources
cpResources:
	@mkdir -p $(BUILD)/main/java/com/tinyeditor/views
	cp -r src/main/java/com/tinyeditor/views/*.fxml $(BUILD)/main/java/com/tinyeditor/views/.
	cp -r tmp $(BUILD)/.
