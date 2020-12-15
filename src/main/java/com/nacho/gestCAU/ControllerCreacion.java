package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Traspaso;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ControllerCreacion {

    @FXML
    private TextArea txtDescripcion;

    public void setData(String data){
        txtDescripcion.setText(data);

    }

}
