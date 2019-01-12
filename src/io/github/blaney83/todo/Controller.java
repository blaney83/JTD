package io.github.blaney83.todo;

import io.github.blaney83.todo.datamodel.TDData;
import io.github.blaney83.todo.datamodel.TDItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class Controller {
    private List<TDItem> todoItems;
    @FXML
    private ListView<TDItem> tdListView;
    @FXML
    private TextArea centerTextArea;
    @FXML
    private Label dateLabel;
    @FXML
    private BorderPane mainBorderPane;



    public void initialize(){


        tdListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TDItem>() {
            @Override
            public void changed(ObservableValue<? extends TDItem> observableValue, TDItem tdItem, TDItem t1) {
                if(t1 != null){
                    DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("E, MMM dd, yyyy");
                    TDItem item = tdListView.getSelectionModel().getSelectedItem();
                    dateLabel.setText(item.getDeadline().format(FORMATTER));
                    centerTextArea.setText(item.getDetails());
                }
            }
        });

        tdListView.getItems().setAll(TDData.getInstance().getTdItems());
        tdListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tdListView.getSelectionModel().selectFirst();

    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new To-Do");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("tdItemDialog.fxml"));
        try{
            dialog.getDialogPane().setContent(fxmlLoader.load());
        }catch (IOException e){
            System.out.println(e);
            e.printStackTrace();
            return;
        }
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            DialogController dialogController = fxmlLoader.getController();
            TDItem newItem = dialogController.handleNewItem();
            tdListView.getItems().setAll(TDData.getInstance().getTdItems());
            tdListView.getSelectionModel().select(newItem);

        }else{
            System.out.println("Cancelled");
        }
    }
}




