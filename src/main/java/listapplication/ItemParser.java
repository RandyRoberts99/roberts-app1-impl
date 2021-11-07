/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

// Creates a single parsed item string for the list of parsed strings to be displayed in the program.
public class ItemParser
{
    public String createParsedItem(ListItem unparsedItem)
    {
        String parsedItem;
        parsedItem = "Description: ";
        if (unparsedItem.description.length() > 60)
        {
            parsedItem += unparsedItem.description.substring(0, 60) + "... \n";
        }
        else
        {
            parsedItem += unparsedItem.description + " \n";
        }
        if (!unparsedItem.date.equalsIgnoreCase("//"))
        {
            parsedItem += "Date: " + unparsedItem.date + "\n";
        }
        else
        {
            parsedItem += "Date: Empty\n";
        }
        parsedItem += "Completed: ";
        if (unparsedItem.isCompleted)
        {
            parsedItem += "Yes";
        }
        else
        {
            parsedItem += "No";
        }
        return parsedItem;
    }
}