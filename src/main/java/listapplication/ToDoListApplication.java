/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ToDoListApplication extends Application
{
    // Starts up the main scene, nothing else (Besides initializing SceneData)
    @Override
    public void start(Stage stage) throws IOException
    {
        // Initializing SceneData class to null/default values
        SceneData.initializeSceneData();

        Scene mainScene = new Scene(FXMLLoader.load(getClass().getResource("MainBox.fxml")));
        SceneSwitcher.setScene(mainScene);

        stage.setResizable(false);
        stage.setTitle("To Do List Application");
        stage.setScene(mainScene);
        stage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
}
