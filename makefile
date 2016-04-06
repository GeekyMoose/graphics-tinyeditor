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
all: clean compile

.PHONY:compile
compile: growthTree cpResources
	$(JC) -d $(BUILD) -sourcepath src src/main/java/com/tinyeditor/MainApp.java

.PHONY: run
run:
	$(JVM) -cp $(BUILD) main.java.com.tinyeditor.MainApp

.PHONY: jar
jar: compile manifest
	cd $(BUILD);echo "cd in $(BUILD)";\
	jar -cvmf manifest.mf tinyeditor.jar main

.PHONY: manifest
manifest:
	echo "Manifest-Version: 1.0" > $(BUILD)/manifest.mf
	echo "Created-By: Constantin (Makefile generation)" >> $(BUILD)/manifest.mf
	echo "Main-Class: main.java.com.tinyeditor.MainApp" >> $(BUILD)/manifest.mf


.PHONY: clean
clean:
	-rm -rf $(BUILD)
	-rm -rf $(BIN)

.PHONY: growthTree
growthTree:
	@mkdir -p $(BUILD)
	@mkdir -p $(BIN)

.PHONY: cpResources
cpResources:
	@mkdir -p $(BUILD)/main/java/resources
	cp -r src/main/java/resources/* $(BUILD)/main/java/resources/.
