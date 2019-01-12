package io.github.blaney83.todo;

import io.github.blaney83.todo.datamodel.TDData;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainapp.fxml"));
        primaryStage.setTitle("JTD");
        primaryStage.setScene(new Scene(root, 900, 500));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        try{
            TDData.getInstance().loadTDItems();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        try{
            TDData.getInstance().saveTDItems();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
