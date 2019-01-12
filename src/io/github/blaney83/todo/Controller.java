package io.github.blaney83.todo;

import io.github.blaney83.todo.datamodel.TDData;
import io.github.blaney83.todo.datamodel.TDItem;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private List<TDItem> todoItems;
    @FXML
    private ListView<TDItem> tdListView;
    @FXML
    private TextArea centerTextArea;
    @FXML
    private Label dateLabel;


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
}


//    TDItem item1 = new TDItem("Welcome to JTD","Have a look around and explore the application. If you need help, check out the repository: ", LocalDate.now());
//    TDItem item2 = new TDItem("Check out blaney83.github.io","Be sure to visit my website for more information about me! https://blaney83.github.io", LocalDate.now());
//    TDItem item3 = new TDItem("Add me on LinkedIn","If you haven't already be sure to connect with me on LinkedIn", LocalDate.now());
//    TDItem item4 = new TDItem("Write that novel.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. In nisi libero, facilisis eu risus vitae, iaculis fermentum orci. Sed dapibus purus nec sapien egestas gravida. In ac leo eu leo congue gravida facilisis sit amet ligula. Aenean eget dapibus nisi. Aenean ac ligula odio. Etiam feugiat pharetra nunc, a vehicula turpis pharetra eu. Donec ac fringilla leo. In ut nulla auctor, interdum purus ac, commodo velit. Suspendisse potenti. Aenean interdum, nibh sollicitudin placerat scelerisque, justo velit feugiat tortor, et dignissim eros tellus non ligula. In vitae velit tristique, porttitor tellus ac, commodo ante. Ut pretium urna dui, eget blandit neque dictum nec. Integer porta iaculis orci, at tempor lectus elementum et.\n" +
//            "\n" +
//            "Maecenas eu egestas nisi, quis molestie dui. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi ante ex, elementum nec sem vitae, elementum feugiat arcu. Fusce pulvinar feugiat metus, non convallis metus euismod et. Morbi ac viverra est, gravida laoreet leo. Curabitur et dui consequat, aliquam erat id, mattis metus. Sed condimentum non lorem et sagittis. In vehicula tincidunt nulla, at rhoncus risus condimentum id. Cras porttitor id nisi condimentum viverra. Aenean a risus porttitor dui semper porta. Nunc id tempor nunc. Curabitur convallis tellus nec lectus fermentum pretium. Aenean ut arcu iaculis, interdum libero at, ultricies sem. Nunc sit amet felis nibh. Suspendisse sagittis lacinia nulla, id ultricies magna. Mauris purus urna, tincidunt a consectetur a, vehicula ac nisl.\n" +
//            "\n" +
//            "Praesent non feugiat enim. Quisque sagittis tincidunt finibus. Proin ultricies ante eget enim maximus scelerisque. Vestibulum convallis justo nisi, vel varius quam molestie nec. Nulla purus ipsum, facilisis id auctor eget, bibendum quis orci. Nulla laoreet, mi in interdum suscipit, ligula massa consectetur nisl, a luctus tellus diam quis mi. Phasellus sodales orci quis fermentum fringilla. Interdum et malesuada fames ac ante ipsum primis in faucibus. Nullam rutrum pulvinar felis non porta. Nam lectus nulla, pellentesque nec leo non, auctor fringilla ex. Morbi sed elementum felis, sed dignissim lacus. Sed tincidunt tortor in nisi vulputate, non tincidunt odio feugiat. Nulla commodo nibh arcu, a fringilla massa mattis at. Vivamus dictum lorem vitae ante venenatis interdum. Nulla porta est vel felis interdum, ut efficitur quam scelerisque. Phasellus auctor sapien vel nisl condimentum commodo.\n" +
//            "\n" +
//            "Donec lorem diam, fringilla sed ante non, lacinia congue risus. Sed placerat mollis leo, et varius risus lobortis ac. Cras nunc turpis, pretium vel ligula eget, volutpat ornare lectus. Aliquam eu magna lacinia, dignissim orci at, tincidunt metus. Etiam purus ex, fringilla quis facilisis non, scelerisque a tortor. Sed pulvinar hendrerit orci a blandit. Ut tempor risus sit amet lobortis feugiat. Nulla ac bibendum neque. Aliquam at eros sit amet erat lacinia vestibulum.\n" +
//            "\n" +
//            "Pellentesque aliquet, felis id placerat posuere, dui lectus accumsan lectus, efficitur facilisis enim metus eget justo. Donec commodo mi in nisi pellentesque, quis vehicula ante congue. Integer est leo, interdum vitae nisl non, iaculis dapibus odio. Donec ultrices urna et nisl ultrices vestibulum. Fusce a lorem sit amet diam hendrerit pulvinar. Phasellus vitae eros quis est ullamcorper tincidunt vitae vitae dui. Donec in urna felis. Donec aliquam ligula quam, vel faucibus diam finibus vitae. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Vivamus sed neque a eros iaculis placerat sed quis risus. Quisque laoreet pellentesque justo. Ut consequat purus purus. Nam molestie aliquet tortor at molestie. In convallis elit non quam lacinia imperdiet. Interdum et malesuada fames ac ante ipsum primis in faucibus.", LocalDate.now());
//
//        todoItems = new ArrayList<>();
//        todoItems.add(item1);
//        todoItems.add(item2);
//        todoItems.add(item3);
//        todoItems.add(item4);

