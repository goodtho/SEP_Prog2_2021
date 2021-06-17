import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class Handlers {

    /**
     * Button press listener
     */
    public void buttonHandler() {
        Button button = new Button("send");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //do something
            }
        });
    }

    /**
     * TextField change listener
     */
    public void textFieldListener() {
        TextField txtField = new TextField();

        txtField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //do something when value changes
            }
        });
    }
}
