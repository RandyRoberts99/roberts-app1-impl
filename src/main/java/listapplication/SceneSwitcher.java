/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class SceneSwitcher
{
    // private Constructor for utility class, to ensure no access
    private SceneSwitcher() throws IllegalAccessException
    {
        throw new IllegalAccessException("Utility Class");
    }

    private static Scene scene;

    // Set the scene for switching
    public static void setScene(Scene scene)
    {
        SceneSwitcher.scene = scene;
    }

    // Switch to the add scene and vice versa
    public static void switchTo(ListScenes nextScene)
    {
        if (scene == null)
        {
            return;
        }
        try
        {
            Parent root = FXMLLoader.load(SceneSwitcher.class.getResource(nextScene.getFileName()));
            scene.setRoot(root);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
