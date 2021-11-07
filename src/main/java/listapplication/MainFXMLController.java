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
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
    private MenuItem helpButton;

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

    @FXML
    private Alert fullAlert;

    @FXML
    private Alert nullFileAlert;

    // All data that will be used to change/save state in the scene

    private ItemParser itemParser;
    private ListParser listParser;
    private ListLoader listLoader;

    private ListItem emptyItem;
    private String emptyItemString;

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

    private static final int MAX_NUM_LIST_ITEMS = 100;
    private int currentNumberOfItems = 0;

    // Instantiate classes/variables and checks to see if any data needs to be replaced.
    @FXML @Override
    void initialize()
    {
        listParser = new ListParser();
        itemParser = new ItemParser();
        listLoader = new ListLoader();
        emptyItem = new ListItem("Empty", false, "Empty");
        emptyItemString = itemParser.createParsedItem(emptyItem);

        fillSceneData();

        if (isLoaded)
        {
            mainList.setItems(toDoListString);
            toDoList.set(editIndex, editedItem);
            toDoListString.set(editIndex, itemParser.createParsedItem(editedItem));
            currentNumberOfItems = toDoList.size();
        }

        notLoadedAlert = new Alert(Alert.AlertType.ERROR);
        notLoadedAlert.setTitle("List Error");
        notLoadedAlert.setResizable(false);
        notLoadedAlert.setContentText("Please create a list before attempting to change items.");

        selectionAlert = new Alert(Alert.AlertType.ERROR);
        selectionAlert.setTitle("Selection Error");
        selectionAlert.setResizable(false);
        selectionAlert.setContentText("You must select an item before changing it.");

        filterAlert = new Alert(Alert.AlertType.ERROR);
        filterAlert.setTitle("Filter Error");
        filterAlert.setResizable(false);
        filterAlert.setContentText("Please un-filter your data and select the item you would like to change.");

        fullAlert = new Alert(Alert.AlertType.ERROR);
        fullAlert.setTitle("Size Error");
        fullAlert.setResizable(false);
        fullAlert.setContentText("Your list is full! Delete an item before adding in another item.");

        nullFileAlert = new Alert(Alert.AlertType.ERROR);
        nullFileAlert.setTitle("Invalid File Error");
        nullFileAlert.setResizable(false);
        nullFileAlert.setContentText("Please use a valid file name and file path");
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
            toDoListString.add(emptyItemString);
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
        // This method checks to see if the list is loaded, and if so it will delete the current list.
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
            currentNumberOfItems = 0;
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
        // This method will take a parsed text file and attempt to create list objects, and from there will
        // try to replace the current list with the new list

        FileChooser listOpener = new FileChooser();
        listOpener.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        listOpener.getExtensionFilters().add(extFilter);

        Stage fileStage = new Stage();
        File listLoadFile = listOpener.showOpenDialog(fileStage);

        if (listLoadFile != null)
        {
            List<ListItem> loadedList = listLoader.loadNewList(listLoadFile);
            initializeItems();
            toDoList = loadedList;
            toDoListString = FXCollections.observableArrayList();
            currentNumberOfItems = toDoList.size();
            mainList.setItems(toDoListString);
            for (int i = 0; i < loadedList.size(); i++)
            {
                toDoListString.add(itemParser.createParsedItem(toDoList.get(i)));
            }
            isLoaded = true;
        }
        else
        {
            nullFileAlert.showAndWait();
        }
    }
    @FXML
    private void saveCurrentList(ActionEvent event)
    {
        /*
        This will save the new set of objects as a parsed .txt file and will ask for a file location and name.
        */
        FileChooser listSaver = new FileChooser();
        listSaver.setInitialDirectory(new File(System.getProperty("user.dir")));
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        listSaver.getExtensionFilters().add(extFilter);

        Stage fileStage = new Stage();
        File listSaveFile = listSaver.showSaveDialog(fileStage);

        if (listSaveFile != null)
        {
            listParser.parseAndSaveFile(toDoList, listSaveFile.getAbsolutePath());
        }
        else
        {
            nullFileAlert.showAndWait();
        }
    }
    @FXML
    private void filterList()
    {
        // This method will filter the given list by dates, and use temporary lists as the filtered lists
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
    @FXML
    private void openGithubLink() throws IOException {
        String websiteLink = "https://github.com/RandyRoberts99/roberts-app1-impl";
        try {
            Desktop.getDesktop().browse(new URI(websiteLink));
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
    }

    private void switchToEditScreen()
    {
        SceneSwitcher.switchTo(ListScenes.EDIT);
    }
    // Fill/Save scene data is basically getting/setting the scene data for scene switching.
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
    // SetList methods will all set the listview to their respective filters
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
        toDoListString.add(emptyItemString);

        currentNumberOfItems = 0;
        currentNumberOfItems++;
    }
}
