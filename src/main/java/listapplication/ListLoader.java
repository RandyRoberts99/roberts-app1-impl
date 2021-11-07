/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListLoader {

    private Scanner reader;

    // Loads a list object from a parsed text file (Reverse of List Parser)
    public List<ListItem> loadNewList(File listFile)
    {
        List<ListItem> newList = new ArrayList<>();
        try
        {
            reader = new Scanner(Paths.get(listFile.getAbsolutePath()));
            reader.nextLine();
            String intStr = reader.nextLine();
            intStr = intStr.substring(17);
            reader.nextLine();
            try
            {
                int numItems = Integer.parseInt(intStr);
                for (int i = 0; i < numItems; i++)
                {
                    ListItem newItem = new ListItem("", false, "");
                    newItem.date = reader.nextLine();
                    String[] dateParts = newItem.date.split(" ", 2);
                    newItem.date = dateParts[1];

                    newItem.description = reader.nextLine();
                    String[] descParts = newItem.description.split(" ", 2);
                    newItem.description = descParts[1];

                    String newItemBool = reader.nextLine();
                    String[] boolParts = newItemBool.split(" ", 2);
                    newItem.isCompleted = Boolean.valueOf(boolParts[1]);

                    newList.add(newItem);
                    reader.nextLine();
                }
            }
            catch (NumberFormatException e)
            {
                System.out.print("Number error!");
            }
        }
        catch (IOException e)
        {
            System.out.print("File Error!");
            e.printStackTrace();
        }
        return newList;
    }
}