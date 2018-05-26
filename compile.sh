if ! [ -e swingbox-bin.jar ]
then
    wget https://sourceforge.net/projects/cssbox/files/SwingBox/1.1/swingbox-1.1-bin.jar/download -O swingbox-bin.jar
fi
javac -cp src:swingbox-bin.jar src/Browser.java
