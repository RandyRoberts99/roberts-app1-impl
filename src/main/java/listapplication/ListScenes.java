/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

public enum ListScenes
{
    MAIN("MainBox.fxml"),
    EDIT("EditBox.fxml");

    private String fileName;

    ListScenes(String fileName)
    {
        this.fileName = fileName;
    }
    public String getFileName()
    {
        return fileName;
    }
}
