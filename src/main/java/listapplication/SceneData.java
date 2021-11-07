/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import java.util.List;

public class SceneData
{
    // private constructor for utility class
    private SceneData() throws IllegalAccessException {
        throw new IllegalAccessException("Utility Class");
    }

    // All data, getters and setters are compiled nicely in a line to access when needed

    private static boolean isSaved;
    private static boolean isLoaded;

    private static int editIndex;
    private static ListItem itemToEdit;

    private static List<ListItem> itemList;
    private static List<String> itemListString;

    public static void setSaveStatus(boolean isSaved)
    {
        SceneData.isSaved = isSaved;
    }
    public static void setLoadedStatus(boolean hasLoadedList)
    {
        SceneData.isLoaded = hasLoadedList;
    }
    public static void setEditIndex(int currentIndex)
    {
        SceneData.editIndex = currentIndex;
    }
    public static void setItemToEdit(ListItem itemToEdit)
    {
        SceneData.itemToEdit = itemToEdit;
    }
    public static void setItemList(List<ListItem> itemList)
    {
        SceneData.itemList = itemList;
    }
    public static void setItemListString(List<String> itemListString)
    {
        SceneData.itemListString = itemListString;
    }

    public static boolean getSaveStatus()
    {
        return isSaved;
    }
    public static boolean getLoadedStatus()
    {
        return isLoaded;
    }
    public static int getEditIndex()
    {
        return editIndex;
    }
    public static ListItem getItemToEdit()
    {
        return itemToEdit;
    }
    public static List<ListItem> getItemList()
    {
        return itemList;
    }
    public static List<String> getItemListString()
    {
        return itemListString;
    }

    public static void initializeSceneData()
    {
        SceneData.setSaveStatus(false);
        SceneData.setLoadedStatus(false);
        SceneData.setEditIndex(-1);
        SceneData.setItemToEdit(null);
        SceneData.setItemList(null);
        SceneData.setItemListString(null);
    }
}