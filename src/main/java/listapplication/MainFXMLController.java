/*
 *  UCF COP3330 Fall 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;


public class MainFXMLController extends AddFXMLController
{
    @FXML
    private Button addButton;

    @FXML
    private MenuItem closeButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button editButton;

    @FXML
    private Button filterButton;

    @FXML
    private MenuItem loadButton;

    @FXML
    private ListView<String> mainList;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem newListButton;

    @FXML
    private MenuItem saveButton;

    @FXML
    private Alert notLoadedAlert;

    @FXML
    private Alert selectionAlert;

    @FXML
    private Alert filterAlert;

    @FXML Alert fullAlert;

    private ItemParser itemParser;
    private ListItem emptyItem;

    private ListItem editedItem;
    private int editIndex;

    private boolean isSaved;
    private boolean isLoaded;
    private boolean isCompleted;
    private boolean isIncompleted;

    private List<ListItem> toDoList = new ArrayList<>();
    private ObservableList<String> toDoListString = FXCollections.observableArrayList();
    private ObservableList<String> completeList = FXCollections.observableArrayList();
    private ObservableList<String> incompleteList = FXCollections.observableArrayList();

    private static final int MAX_NUM_LIST_ITEMS = 15;
    private int currentNumberOfItems = 0;

    @FXML @Override
    void initialize()
    {
        itemParser = new ItemParser();
        emptyItem = new ListItem("Empty", false, "Empty");

        fillSceneData();

        if (isLoaded)
        {
            mainList.setItems(toDoListString);
            toDoList.set(editIndex, editedItem);
            toDoListString.set(editIndex, itemParser.createParsedItem(editedItem));
            currentNumberOfItems = toDoList.size();
        }

        notLoadedAlert = new Alert(Alert.AlertType.ERROR);
        notLoadedAlert.setResizable(false);
        notLoadedAlert.setTitle("List Error");
        notLoadedAlert.setContentText("Please create a list before attempting to change items.");

        selectionAlert = new Alert(Alert.AlertType.ERROR);
        selectionAlert.setResizable(false);
        selectionAlert.setTitle("Selection Error");
        selectionAlert.setContentText("You must select an item before changing it.");

        filterAlert = new Alert(Alert.AlertType.ERROR);
        filterAlert.setTitle("Filter Error");
        filterAlert.setContentText("Please un-filter your data and select the item you would like to change.");
        filterAlert.setResizable(false);

        fullAlert = new Alert(Alert.AlertType.ERROR);
        fullAlert.setTitle("Size Error");
        fullAlert.setResizable(false);
        fullAlert.setContentText("Your list is full! Delete an item before adding in another item.");
    }

    @FXML
    private void addListItem(ActionEvent event)
    {
        /*
        This will first check to see if the current list is full and if not,
        this will create a popup window allowing the user to enter in their data into
        fields that will be appended into the array
        */
        if (!isLoaded)
        {
            notLoadedAlert.showAndWait();
        }
        else if (currentNumberOfItems == MAX_NUM_LIST_ITEMS)
        {
            fullAlert.showAndWait();
        }
        else if (isCompleted || isIncompleted)
        {
            filterAlert.showAndWait();
        }
        else
        {
            toDoList.add(emptyItem);
            String emptyString = itemParser.createParsedItem(emptyItem);
            toDoListString.add(emptyString);
            incompleteList.add(emptyString);
            currentNumberOfItems++;
        }
    }

    @FXML
    private void attemptCloseApp(ActionEvent event)
    {
        Platform.exit();
    }

    @FXML
    private void createNewList(ActionEvent event)
    {
        initializeItems();
        isLoaded = true;
    }

    @FXML
    private void deleteListItem(ActionEvent event)
    {
        /*
        This method will delete an item in the list at the associated index.
        */
        if (!isLoaded)
        {
            notLoadedAlert.showAndWait();
        }
        else
        {
            int deleteIndex = mainList.getSelectionModel().getSelectedIndex();

            if (deleteIndex != -1)
            {
                if (isCompleted || isIncompleted)
                {
                    filterAlert.showAndWait();
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
                selectionAlert.showAndWait();
            }
        }
    }
    @FXML
    private void clearList()
    {
        if (!isLoaded)
        {
            notLoadedAlert.showAndWait();
        }
        else if (isCompleted || isIncompleted)
        {
            filterAlert.showAndWait();
        }
        else
        {
            initializeItems();
            toDoList.remove(0);
            toDoListString.remove(0);
        }
    }

    @FXML
    private void editListItem(ActionEvent event)
    {
        /*
        This method will alter the properties of a single list object and return the new object to the list at the same
        index. (There will be conditionals to check for proper input)
        */
        if (!isLoaded)
        {
            notLoadedAlert.showAndWait();
        }
        else if (isCompleted || isIncompleted)
        {
            filterAlert.showAndWait();
        }
        else
        {
            editIndex = mainList.getSelectionModel().getSelectedIndex();

            if (editIndex == -1)
            {
                selectionAlert.showAndWait();
            }
            else
            {
                saveSceneData();
                switchToEditScreen();
            }
        }
    }

    @FXML
    private void loadList(ActionEvent event)
    {
        /*
        This method will create an object using a parsed .txt file, and will print the result to the list.
        */
    }

    @FXML
    private void saveCurrentList(ActionEvent event)
    {
        /*
        This will check to see if the current instance of the application is already associated with a file,
        and if it is not it will prompt the user for a name and location for the file. If it does already
        have an association, it will save the new set of objects as a parsed .txt file and will ask for a file location.
        */
    }
    private void switchToEditScreen()
    {
        SceneSwitcher.switchTo(ListScenes.EDIT);
    }

    private void fillSceneData()
    {
        isSaved = SceneData.getSaveStatus();
        isLoaded = SceneData.getLoadedStatus();
        editIndex = SceneData.getEditIndex();
        editedItem = SceneData.getItemToEdit();
        toDoList = SceneData.getItemList();
        toDoListString = (ObservableList<String>) SceneData.getItemListString();
    }
    private void saveSceneData()
    {
        SceneData.setSaveStatus(isSaved);
        SceneData.setLoadedStatus(isLoaded);
        SceneData.setEditIndex(mainList.getSelectionModel().getSelectedIndex());
        SceneData.setItemToEdit(toDoList.get(SceneData.getEditIndex()));
        SceneData.setItemList(toDoList);
        SceneData.setItemListString(toDoListString);
    }
    @FXML
    private void filterList()
    {
        if (!isLoaded)
        {
            notLoadedAlert.showAndWait();
            return;
        }
        if (isCompleted)
        {
            setIncompleteList();
            isCompleted = false;
            isIncompleted = true;
        }
        else if (isIncompleted)
        {
            setMainList();
            isIncompleted = false;
        }
        else
        {
            setCompleteList();
            isCompleted = true;
        }
    }
    private void setCompleteList()
    {
        completeList = FXCollections.observableArrayList();
        for (int i = 0; i < toDoList.size(); i++)
        {
            if (toDoList.get(i).isCompleted)
            {
                completeList.add(itemParser.createParsedItem(toDoList.get(i)));
            }
        }
        mainList.setItems(completeList);
    }
    private void setIncompleteList()
    {
        incompleteList = FXCollections.observableArrayList();
        for (int i = 0; i < toDoList.size(); i++)
        {
            if (!toDoList.get(i).isCompleted)
            {
                incompleteList.add(itemParser.createParsedItem(toDoList.get(i)));
            }
        }
        mainList.setItems(incompleteList);
    }
    private void setMainList()
    {
        mainList.setItems(toDoListString);
    }

    private void initializeItems()
    {
        // resetting all values to default state, adding in new empty item
        toDoList = new ArrayList<>();
        completeList = FXCollections.observableArrayList();
        incompleteList = FXCollections.observableArrayList();
        toDoListString = FXCollections.observableArrayList();

        isCompleted = false;
        isIncompleted = false;

        mainList.setItems(toDoListString);
        toDoList.add(emptyItem);
        String emptyString = itemParser.createParsedItem(emptyItem);
        toDoListString.add(emptyString);

        currentNumberOfItems = 0;
        currentNumberOfItems++;
    }
}
