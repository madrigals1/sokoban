package res.Windows;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import res.Scenar.Main;
import res.Static.Values;

public class SettingsMenu extends Application {
    Stage primaryStageSaved;
    AnchorPane root;
    Scene scene;

    AnchorPane mainPane = new AnchorPane();

    Text label, widthText, heightText, arrowText;
    Button back, save;
    TextField widthTF, heightTF;
    CheckBox arrowCB;

    double mainWidth, mainHeight;

    public void start(Stage primaryStage) {
        try {
            primaryStageSaved = primaryStage;
            root = new AnchorPane();
            scene = new Scene(root, Values.sceneWidth, Values.sceneHeight);

            createElements();

            primaryStageSaved.setTitle(Values.title);
            primaryStageSaved.setScene(scene);
            primaryStageSaved.show();

        } catch(Exception e) {
            e.printStackTrace();
            Platform.exit();
        }
    }

    void createElements() {
        scene.getStylesheets().add("/res/CSS/main.css");

        mainWidth = 400;
        mainHeight = 400;

        mainPane.setLayoutX((Values.sceneWidth - mainWidth) / 2);
        mainPane.setLayoutY((Values.sceneHeight - mainHeight) / 2);
        mainPane.setPrefWidth(mainWidth);
        mainPane.setPrefHeight(mainHeight);

        label = new Text(0, (Values.sceneHeight - mainHeight) / 2 - 50, "Settings");
        label.setWrappingWidth(Values.sceneWidth);
        label.setId("label");
        label.setFill(new Color(0.188, 0.247, 0.624, 1));
        label.setTextAlignment(TextAlignment.CENTER);

        widthText = new Text(0, (mainHeight / 4) * 0.5, "Screen width :");
        widthText.setId("label");
        widthText.setFill(new Color(0.188, 0.247, 0.624, 1));
        widthText.setStyle("-fx-font-size : 35px");

        heightText = new Text(0, (mainHeight / 4) * 1.5, "Screen height :");
        heightText.setId("label");
        heightText.setFill(new Color(0.188, 0.247, 0.624, 1));
        heightText.setStyle("-fx-font-size : 35px");

        arrowText = new Text(0, (mainHeight / 4) * 2.5, "Arrow enabled :");
        arrowText.setId("label");
        arrowText.setFill(new Color(0.188, 0.247, 0.624, 1));
        arrowText.setStyle("-fx-font-size : 35px");

        widthTF = new TextField("" + Values.sceneWidth);
        widthTF.setLayoutX(mainWidth * 0.7);
        widthTF.setLayoutY(mainHeight * 0.25 * 0);
        widthTF.setPrefSize(mainWidth * 0.3, mainHeight * 0.25 - 20);
        widthTF.setId("label");
        widthTF.setStyle("-fx-font-size: 20px; -fx-effect: null");
        widthTF.setAlignment(Pos.CENTER);

        heightTF = new TextField("" + Values.sceneHeight);
        heightTF.setLayoutX(mainWidth * 0.7);
        heightTF.setLayoutY(mainHeight * 0.25 * 1);
        heightTF.setPrefSize(mainWidth * 0.3, mainHeight * 0.2);
        heightTF.setId("label");
        heightTF.setStyle("-fx-font-size: 20px; -fx-effect: null");
        heightTF.setAlignment(Pos.CENTER);

        arrowCB = new CheckBox();
        arrowCB.setLayoutX(mainWidth * 0.82);
        arrowCB.setLayoutY(mainHeight * 0.25 * 2);
        arrowCB.setPrefHeight(mainHeight * 0.2);
        arrowCB.setAlignment(Pos.CENTER);
        arrowCB.setSelected(Values.arrowEnabled);

        back = new Button("Back");
        back.setLayoutX(mainWidth * 0.1);
        back.setLayoutY(mainHeight * 0.25 * 3);
        back.setPrefSize(mainWidth * 0.35, mainHeight * 0.25);
        back.setStyle("-fx-font-size: 30px");

        save = new Button("Save");
        save.setLayoutX(mainWidth * 0.55);
        save.setLayoutY(mainHeight * 0.25 * 3);
        save.setPrefSize(mainWidth * 0.35, mainHeight * 0.25);
        save.setStyle("-fx-font-size: 30px");

        mainPane.getChildren().add(widthText);
        mainPane.getChildren().add(heightText);
        mainPane.getChildren().add(arrowText);
        mainPane.getChildren().add(widthTF);
        mainPane.getChildren().add(heightTF);
        mainPane.getChildren().add(arrowCB);
        mainPane.getChildren().add(back);
        mainPane.getChildren().add(save);

        setListeners();

        root.getChildren().add(mainPane);
        root.getChildren().add(label);
    }

    void setListeners(){
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                Values.sceneWidth = Integer.parseInt(widthTF.getText());
                Values.sceneHeight = Integer.parseInt(heightTF.getText());

                if(Values.sceneWidth < 600) Values.sceneWidth = 600;
                if(Values.sceneHeight < 600) Values.sceneHeight = 600;

                Values.arrowEnabled = arrowCB.isSelected();
                MainMenu menu = new MainMenu();
                menu.start(primaryStageSaved);
            }
        });
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                MainMenu menu = new MainMenu();
                menu.start(primaryStageSaved);
            }
        });
        scene.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                if (ke.getCode() == KeyCode.ESCAPE) {
                    MainMenu main = new MainMenu();
                    main.start(primaryStageSaved);
                    ke.consume();
                }
            }
        });
    }
}
