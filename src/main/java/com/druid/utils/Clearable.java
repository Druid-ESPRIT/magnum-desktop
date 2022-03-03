package com.druid.utils;

import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * This interface implements a method that has the ability to
 * clear a <code>TextField</code> through a handy shortcut.
 */
public class Clearable {
    /**
     * When <code>Ctrl+U</code> is pressed, the provided <code>TextField</code> will clear itself.
     * <br>
     * <br>
     * <h2>Usage</h2>
     * <pre>{@code
     * public class MyController implements Initializable, Clearable {
     *   @FXML
     *   private TextField email;
     *
     *   @Override
     *   public void initialize(URL url, ResourceBundle resourceBundle) {
     *     email.setOnKeyPressed(Clearable.clear(email));
     *   }
     * }
     * }
     * </pre>
     *
     * @param field The field that should be cleared.
     * @return An <code>EventHandler</code> that must be consumed by the provided <code>TextField</code>.
     */
    public static EventHandler<KeyEvent> clear(TextField field) {
        return event -> {
            if (event.isControlDown() && event.getCode() == KeyCode.U) {
                field.clear();
            }
        };
    }
}
