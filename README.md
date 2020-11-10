# Sokoban

Sokoban game clone made in JavaFX

# Prerequisites

Make sure you have installed these:

- [Java](https://www.java.com/en/download/) - is a class-based, object-oriented programming language that is designed to have as few implementation dependencies as possible.
You should install both **JDK** and **JRE**.
- [JavaFX](https://gluonhq.com/products/javafx/) - OpenJFX is an open source, next generation client application platform for desktop, mobile and embedded systems built on Java.

## Gameplay

### Main Menu

![Main Menu](https://i.imgur.com/dFR4HUW.png "Main Menu")

### Game

![Game](https://i.imgur.com/7HpYzxA.png "Game")

### Game has 15 pre-installed levels

![Levels](https://i.imgur.com/fheiIIR.png "Levels")

### You can always add your own level

![Add Level](https://i.imgur.com/4UJyvbh.png "Add Level")


## Controls

- **W, A, S, D** - movement
- **Escape** - go back to Main Menu
- **R** - restart

## Installation

Following instruction only works for **IntelliJ IDEA**

- Add **JavaFX** library by the instruction here
https://www.jetbrains.com/help/idea/javafx.html#add-javafx-lib

- Navigate to `src/res/windows`

- Run `MainMenu.java`. Don't worry if it throws,
we used this part to create configuration.

- Go to `Run -> Edit Configurations`

- Open configuration we just created.

- In the VM options field, specify:

```shell script
--module-path %PATH_TO_FX% --add-modules javafx.controls,javafx.fxml
```

> Instead of %PATH_TO_FX%, specify the path to the JavaFX SDK lib directory

- Apply configuration

- Run




