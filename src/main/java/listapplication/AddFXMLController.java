/*
 *  UCF COP3330 Summer 2021 Application Assignment 1 Solution
 *  Copyright 2021 Randall Roberts
 */

package listapplication;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class AddFXMLController {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button confirmButton;

    @FXML
    private TextField dayField;

    @FXML
    private TextArea descriptionField;

    @FXML
    private TextField monthField;

    @FXML
    private VBox vBox;

    @FXML
    private TextField yearField;

    @FXML
    private CheckBox isCompleted;

    private int itemIndex;

    private ListItem itemToEdit;

    // Initializes and classes/variables and fills data appropriately with edit item
    @FXML
    void initialize()
    {
        itemIndex = SceneData.getEditIndex();
        itemToEdit = SceneData.getItemToEdit();

        descriptionField.setText(itemToEdit.description);
        isCompleted.setSelected(itemToEdit.isCompleted);

        if (!itemToEdit.date.equalsIgnoreCase("//") && !itemToEdit.date.equalsIgnoreCase("Empty"))
        {
            yearField.setText(itemToEdit.date.substring(0,4));
            monthField.setText(itemToEdit.date.substring(5,7));
            dayField.setText(itemToEdit.date.substring(8,10));
        }
    }

    // Checks to see if the users input is valid and if so, will create an item to go into the list
    @FXML
    void checkValidListItem(ActionEvent event)
    {
        boolean isValidDate = false;

        if ((isNumber(yearField.getText()) && isNumber(monthField.getText()) && isNumber(dayField.getText())) || (isEmpty(yearField.getText(), monthField.getText(), dayField.getText())))
        {
            if (checkValidDate(yearField.getText(), monthField.getText(), dayField.getText()))
            {
                isValidDate = true;
            }
            else if (isEmpty(yearField.getText(), monthField.getText(), dayField.getText()))
            {
                isValidDate = true;
            }
            else
            {
                Alert dateFormatAlert = new Alert(Alert.AlertType.ERROR);
                dateFormatAlert.setResizable(false);
                dateFormatAlert.setTitle("Invalid Date Error");
                dateFormatAlert.setContentText("Please enter a proper date from the Gregorian Calendar, OR an empty date.");
                dateFormatAlert.showAndWait();
            }
        }
        else
        {
            Alert numberAlert = new Alert(Alert.AlertType.ERROR);
            numberAlert.setResizable(false);
            numberAlert.setTitle("Invalid Date Error");
            numberAlert.setContentText("Please enter a valid numerical date.");
            numberAlert.showAndWait();
        }
        boolean isValidDescription = checkValidDescription(descriptionField.getText());

        if (isValidDescription && isValidDate)
        {
            editListItem(yearField, monthField, dayField, descriptionField);
        }
    }

    private boolean checkValidDate(String year, String month, String day)
    {
        boolean isValidDate;
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
        return isValidDate;
    }
    private boolean isEmpty(String str1, String str2, String str3)
    {
        return str1.equalsIgnoreCase("") && str2.equalsIgnoreCase("") && str3.equalsIgnoreCase("");
    }
    private boolean checkValidDescription(String description)
    {
        if (description.length() > 0 && description.length() < 257)
        {
            return true;
        }
        else
        {
            Alert overflowAlert = new Alert(Alert.AlertType.ERROR);
            overflowAlert.setTitle("Description Error");
            overflowAlert.setResizable(false);
            overflowAlert.setContentText("Please enter in a valid description between 1-256 characters.");
            overflowAlert.showAndWait();
        }
        return false;
    }
    private boolean isNumber(String partialDate)
    {
        boolean canParse;
        try
        {
            Integer.parseInt(partialDate);
            canParse = true;
        }
        catch (NumberFormatException e)
        {
            canParse = false;
        }
        return canParse;
    }
    public void editListItem(TextField yearField, TextField monthField, TextField dayField, TextArea descriptionField)
    {
        ListItem newItem = new ListItem(descriptionField.getText(), isCompleted.isSelected(), yearField.getText() + "/" + monthField.getText() + "/" + dayField.getText());
        SceneData.setEditIndex(itemIndex);
        SceneData.setItemToEdit(newItem);
        switchToMainScreen();
    }
    public void switchToMainScreen()
    {
        SceneSwitcher.switchTo(ListScenes.MAIN);
    }
}