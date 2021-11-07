/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

import listapplication.ItemParser;
import listapplication.ListItem;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTester
{
    private ItemParser itemParser = new ItemParser();

    /*
    * The purpose of this class is to test methods that do not solely use FXML. I have copied and pasted each method
    * that uses programming logic and commented out and FXML elements that they use. I have also instantiated objects
    * for each method to use and verify that each one of them works correctly.
    */

    @Test
    void testAddItem()
    {
        boolean isLoaded = true;
        boolean isCompleted = false;
        boolean isIncompleted = false;

        List<ListItem> toDoList = new ArrayList<>();
        List<String> toDoListString = new ArrayList<>();

        int currentNumberOfItems = 0;
        int MAX_NUM_LIST_ITEMS = 100;

        ListItem emptyItem = new ListItem("Empty", false, "Empty");
        String emptyItemString = itemParser.createParsedItem(emptyItem);

        if (!isLoaded)
        {
            //notLoadedAlert.showAndWait();
        }
        else if (currentNumberOfItems == MAX_NUM_LIST_ITEMS)
        {
            //fullAlert.showAndWait();
        }
        else if (isCompleted || isIncompleted)
        {
            //filterAlert.showAndWait();
        }
        else
        {
            toDoList.add(emptyItem);
            toDoListString.add(emptyItemString);
            currentNumberOfItems++;
        }
        assertEquals(1,currentNumberOfItems);
    }
    @Test
    void testDescriptionVerifier()
    {
        boolean isValid = true;

        String description = "";
        for (int i = 0; i < 257; i++)
        {
            description += "a";
        }
        if (description.length() > 0 && description.length() < 257)
        {
            isValid = true;
        }
        else
        {
            //Alert overflowAlert = new Alert(Alert.AlertType.ERROR);
            //overflowAlert.setTitle("Description Error");
            //overflowAlert.setResizable(false);
            //overflowAlert.setContentText("Please enter in a valid description between 1-256 characters.");
            //overflowAlert.showAndWait();
            isValid = false;
        }
        assertEquals(false, isValid);
    }
    @Test
    void testRemoval()
    {
        boolean isLoaded = true;
        boolean isCompleted = false;
        boolean isIncompleted = false;

        List<ListItem> toDoList = new ArrayList<>();
        List<String> toDoListString = new ArrayList<>();

        ListItem emptyItem = new ListItem("Empty", false, "Empty");
        String emptyItemString = itemParser.createParsedItem(emptyItem);

        toDoList.add(emptyItem);
        toDoListString.add(emptyItemString);

        int currentNumberOfItems = 1;

        if (!isLoaded)
        {
            //notLoadedAlert.showAndWait();
        }
        else
        {
            int deleteIndex = 0;  // mainList.getSelectionModel().getSelectedIndex();

            if (deleteIndex != -1)
            {
                if (isCompleted || isIncompleted)
                {
                    // filterAlert.showAndWait();
                }
                else
                {
                    toDoList.remove(deleteIndex);
                    toDoListString.remove(deleteIndex);
                    currentNumberOfItems--;
                }
            }
            else
            {
                //selectionAlert.showAndWait();
            }
        }
        assertEquals(true, (toDoListString.size() == 0 && currentNumberOfItems == 0));
    }
    @Test
    void testDueDate()
    {
        boolean isValidDate;
        String year = "1999";
        String day = "15";
        String month = "02";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        try
        {
            LocalDate.parse(year + "/" + month + "/" + day, formatter);
            isValidDate = true;
        }
        catch (DateTimeParseException e)
        {
            isValidDate = false;
        }
        assertEquals(true, isValidDate);
    }
    @Test
    void testItemClear()
    {
        boolean isLoaded = true;
        boolean isCompleted = false;
        boolean isIncompleted = false;

        List<ListItem> toDoList = new ArrayList<>();
        List<String> toDoListString = new ArrayList<>();

        ListItem emptyItem = new ListItem("Empty", false, "Empty");
        String emptyItemString = itemParser.createParsedItem(emptyItem);

        toDoList.add(emptyItem);
        toDoListString.add(emptyItemString);

        int currentNumberOfItems = 1;

        if (!isLoaded)
        {
            //notLoadedAlert.showAndWait();
        }
        else if (isCompleted || isIncompleted)
        {
            //filterAlert.showAndWait();
        }
        else
        {
            //initializeItems();
            toDoList.remove(0);
            toDoListString.remove(0);
            currentNumberOfItems = 0;
        }
        assertEquals(0, currentNumberOfItems);
    }
}