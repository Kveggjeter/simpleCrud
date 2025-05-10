package com.note.visma.viewmodel.common;

import javafx.scene.control.Alert;

public class AlertBox {

    public static void alertBox(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
