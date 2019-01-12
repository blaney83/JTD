package io.github.blaney83.todo;
import io.github.blaney83.todo.datamodel.TDData;
import io.github.blaney83.todo.datamodel.TDItem;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Controller {
    private List<TDItem> todoItems;
    private FilteredList<TDItem> filteredList;
    private Predicate<TDItem> showAll;
    private Predicate<TDItem> showToday;
    @FXML
    private ListView<TDItem> tdListView;
    @FXML
    private TextArea centerTextArea;
    @FXML
    private Label dateLabel;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private ContextMenu listContextMenu;
    @FXML
    private ToggleButton filterToggleButton;


    public void initialize(){

        listContextMenu = new ContextMenu();
        MenuItem deleteMenuItem = new MenuItem("Delete");
        deleteMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                TDItem item = tdListView.getSelectionModel().getSelectedItem();
                deleteItem(item);
            }
        });
        showAll = new Predicate<TDItem>() {
            @Override
            public boolean test(TDItem tdItem) {
                return true;
            }
        };
        showToday = new Predicate<TDItem>() {
            @Override
            public boolean test(TDItem tdItem) {
                return tdItem.getDeadline().equals(LocalDate.now());
            }
        };
        filteredList = new FilteredList<TDItem>(TDData.getInstance().getTdItems(), showAll);

        SortedList<TDItem> sortedList = new SortedList<TDItem>(filteredList, new Comparator<TDItem>() {
            @Override
            public int compare(TDItem o1, TDItem o2) {
                if(o1 != null && o2 != null) {
                    return o1.getDeadline().compareTo(o2.getDeadline());
                }
                return -1;
            }
        });

        listContextMenu.getItems().addAll(deleteMenuItem);
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
        tdListView.setItems(sortedList);
        tdListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tdListView.getSelectionModel().selectFirst();

        tdListView.setCellFactory(new Callback<ListView<TDItem>, ListCell<TDItem>>() {
            @Override
            public ListCell<TDItem> call(ListView<TDItem> tdItemListView) {
                ListCell<TDItem> cell = new ListCell<>(){
                    @Override
                    protected void updateItem(TDItem tdItem, boolean b) { //b is boolean empty
                        super.updateItem(tdItem, b);
                        if(b){
                            setText(null);
                            setStyle("-fx-control-inner-background:  rgb(255, 255, 255)");
                            System.out.println("hello");
                        }else{
                            if(tdItem != null) {
                                System.out.println(tdItem.getShortDescription());
                                setText(tdItem.getShortDescription());
                                if (tdItem.getDeadline().equals(LocalDate.now())) {
                                    setStyle("-fx-control-inner-background:  rgb(255, 204, 204)");
//                                setTextFill(Color.rgb(255, 153, 153));
                                } else if (tdItem.getDeadline().compareTo(LocalDate.now()) < 0) {
                                    setStyle("-fx-control-inner-background:  rgb(255, 102, 102)");
//                                setTextFill(Color.rgb(255, 255, 128));
                                }  else if (tdItem.getDeadline().compareTo(LocalDate.now().plusDays(3)) <= 0) {
                                    setStyle("-fx-control-inner-background:  rgb(255, 255, 179)");
//                                setTextFill(Color.rgb(255, 255, 128));
                                }else {
                                    setStyle("-fx-control-inner-background:  rgb(193, 240, 193)");
                                }
                            }
                        }
                    }
                };
                cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty)->{
                    if(isNowEmpty){
                        cell.setContextMenu(null);
                    }else{
                        cell.setContextMenu(listContextMenu);
                    }
                });
                return cell;
            }
        });

    }
    @FXML
    public void handleKeyDown(KeyEvent keyEvent){
        TDItem item = tdListView.getSelectionModel().getSelectedItem();
        if(item != null){
            if(keyEvent.getCode().equals(KeyCode.DELETE)){
                deleteItem(item);
            }
        }
    }
    @FXML
    public void deleteItem(TDItem item){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete To-Do Item");
        alert.setHeaderText("Delete " + item.getShortDescription());
        alert.setContentText("Are you sure? Press OK to confirm, or CANCEL to go back.");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            TDData.getInstance().deleteItem(item);
        }
    }

    @FXML
    public void showNewItemDialog(){
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initOwner(mainBorderPane.getScene().getWindow());
        dialog.setTitle("Add new To-Do");
        dialog.setHeaderText("Enter all the fields below to create your new item.");
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
            tdListView.getSelectionModel().select(newItem);
        }
    }

    @FXML
    public void filterList(){
        TDItem selectedItem = tdListView.getSelectionModel().getSelectedItem();
        if(filterToggleButton.isSelected()){
            filteredList.setPredicate(showToday);
            if(filteredList.isEmpty()){
                centerTextArea.clear();
                dateLabel.setText("");
            }else if(filteredList.contains(selectedItem)){
                tdListView.getSelectionModel().select(selectedItem);
            }else{
                tdListView.getSelectionModel().selectFirst();
            }
        }else{
            filteredList.setPredicate(showAll);
            tdListView.getSelectionModel().select(selectedItem);
        }
    }

    @FXML
    public void exitProgram(){
        Platform.exit();
    }
}




