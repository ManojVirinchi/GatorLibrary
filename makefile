
JC = javac
.SUFFIXES: .java .class
.java.class:
		$(JC)  $*.java
CLASSES = GatorLibrary.java \
default: classes
classes: $(CLASSES:.java=.class)

