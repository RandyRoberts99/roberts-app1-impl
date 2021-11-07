/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ListParser
{
    // Creates a parsed text file at the specified file path
    public void parseAndSaveFile(List<ListItem> listToParse, String filePath)
    {
        String parsedData = "WARNING: DO NOT ALTER THIS FILE OR IT WILL NOT LOAD PROPERLY IN THE APPLICATION!\nNumber of Items: " + listToParse.size() + "\n\n";
        for (int i = 0; i < listToParse.size(); i++)
        {
            ListItem tempItem = listToParse.get(i);
            parsedData += "Date: " + tempItem.date + "\n";
            parsedData += "Description: " + tempItem.description + "\n";
            parsedData += "Completed: " + tempItem.isCompleted + "\n\n";
        }
        try {
            File listFile = new File(filePath);
            FileWriter f2 = new FileWriter(listFile, false);
            f2.write(parsedData);
            f2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}