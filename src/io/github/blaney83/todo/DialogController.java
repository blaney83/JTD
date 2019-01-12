package io.github.blaney83.todo;

import io.github.blaney83.todo.datamodel.TDData;
import io.github.blaney83.todo.datamodel.TDItem;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.time.LocalDate;

public class DialogController {
    @FXML
    private TextField shortDescriptionField;
    @FXML
    private TextArea detailsArea;
    @FXML
    private DatePicker deadlinePicker;

    public TDItem handleNewItem(){
        String shortDescription = shortDescriptionField.getText().trim();
        String details = detailsArea.getText().trim();
        LocalDate date = deadlinePicker.getValue();
        TDItem newItem = new TDItem(shortDescription, details, date);
        TDData.getInstance().addTDItem(newItem);
        return newItem;
    }

}
