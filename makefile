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



# -----------------------------------------------------------------------------
# Compilation - Execution
# -----------------------------------------------------------------------------
.PHONY: all
all: clean compile

.PHONY:compile
compile: growthTree cpResources
	$(JC) -d $(BUILD) -cp src/main/java src/main/java/com/tinyeditor/main/Main.java

.PHONY: run
run:
	$(JVM) -cp $(BUILD) com.tinyeditor.main.Main

.PHONY: jar
jar: compile manifest
	cd $(BUILD);echo "cd in $(BUILD)";\
	jar -cvmf manifest.mf tinyeditor.jar *

.PHONY: manifest
manifest:
	echo "Manifest-Version: 1.0" > $(BUILD)/manifest.mf
	echo "Created-By: Constantin (Makefile generation)" >> $(BUILD)/manifest.mf
	echo "Main-Class: com.tinyeditor.main.Main" >> $(BUILD)/manifest.mf


# -----------------------------------------------------------------------------
# Various
# -----------------------------------------------------------------------------
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
	@mkdir -p $(BUILD)/resources
	cp -r src/main/java/resources/* $(BUILD)/resources/.
