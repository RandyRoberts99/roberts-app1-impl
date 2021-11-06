/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

public class ListItem
{
    /*
    This class is meant to hold the current state of a list item,
    and will be referenced when attempting to display an item in a list and its current properties
    */

    String description;
    boolean isCompleted;
    String date;

    public ListItem(String description, boolean isCompleted, String date)
    {
        this.description = description;
        this.isCompleted = isCompleted;
        this.date = date;
    }
}