# nael-quotes-flashcards
A dumb little command-line flashcard-like game for memorizing Nael's various quotes in UCoB. Each of her fifteen quotes
are represented, prefixed by when they occur in the fight (since this is also usable information). The user inputs the
corresponding mechanics separated by spaces.

## Downloading
Clone the git repository:

```shell
git clone https://github.com/wrthomps/nael-quotes-flashcards.git
```

## Running
This program is written in Java, compiled by JDK11, built using Maven. Why all this trouble for something so simple?
Because it means my programming-illiterate static can participate by just typing a couple highlighted commands. Like
this one, which runs the program directly:

```shell
mvn exec:java
```

## Building
I'm a little confused why you need to read this section, but sure. Build a JAR via the standard Maven command:

```shell
mvn package
```

## Contributing
I wrote this in like half an hour and don't plan on making it Production-Ready(tm). Please don't submit a PR, just fork
your own and do that instead. There's a reason I'm releasing this under the Unlicense.