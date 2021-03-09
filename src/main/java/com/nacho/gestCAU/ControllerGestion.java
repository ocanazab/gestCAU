package com.nacho.gestCAU;

import com.nacho.gestCAU.util.Incidenciasmysql;
import com.nacho.gestCAU.util.Mensajeria;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ControllerGestion {
    @FXML
    private TextArea txtDescripcionIncidencia;
    @FXML
    private ChoiceBox cbTipoIncidencia;
    @FXML
    private ChoiceBox cbEstado;
    @FXML
    private DatePicker dpFechaCreacion;
    @FXML
    private DatePicker dpFechaSolucion;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtEmail;
    @FXML
    private Button btnModificar;
    @FXML
    private Button btnSalir;
    @FXML
    private TableView tblGestionIncidencias;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private TableColumn colnumIncidencia;
    @FXML
    private TableColumn colFecha;
    @FXML
    private TableColumn colEstado;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableColumn colApellidos;
    @FXML
    private TableColumn colEmail;

    private String usu;
    private String bd;

    public void setData(String usuario, String baseDatos){
        usu=usuario;
        bd=baseDatos;
    }

    @FXML
    private void initialize() {
        //Inicializo el DatePicker
        dpFechaCreacion.setEditable(false);

        //Preparo el TableView
        TableView.TableViewSelectionModel<Incidenciasmysql> selectionModel = tblGestionIncidencias.getSelectionModel();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);

        String resultado="";

        //Consulto a Postgre las incidencias pendientes de traspaso y las inserto en Mysql
        Model modelo = new Model();
        modelo.conectarBD("postgre");
        modelo.conectarBD(bd);
        resultado= modelo.traspasarIncidencias();

        if (resultado.isEmpty()){
            //Desconecto de Postgre
            modelo.desconectarBD("postgre");
            //Actualizo el TableView
            resultado=refrescaTabla();
        }else{
            //Muestro un mensaje de error para ver que ha pasado en el traspaso y cierro la app
            Mensajeria.mostrarError("Error en el traspaso de datos",resultado);
            Platform.exit();
        }
    }

    private String refrescaTabla(){
        String resultado="";

        //Muestro las incidencias
        ObservableList<Incidenciasmysql> lista = FXCollections.observableArrayList();

        Model modelo = new Model();
        //modelo.conectarBD(bd);
        modelo.conectarBD(bd);

        lista = modelo.listaGestionIncidencias();

        if(lista!=null){
            //Relleno el TableView
            for (int i = 0; i < lista.size(); i++) {
                colnumIncidencia.setCellValueFactory(new PropertyValueFactory("codIncidencia"));
                colDescripcion.setCellValueFactory(new PropertyValueFactory("descripcion"));
                colFecha.setCellValueFactory(new PropertyValueFactory("fechaCreacion"));
                colEstado.setCellValueFactory(new PropertyValueFactory("estado"));
                colNombre.setCellValueFactory(new PropertyValueFactory("nombre"));
                colApellidos.setCellValueFactory(new PropertyValueFactory("apellidos"));
                colEmail.setCellValueFactory(new PropertyValueFactory("email"));
            }
            tblGestionIncidencias.setItems(null);
            tblGestionIncidencias.setItems(lista);

        }else{
            resultado="Error";
        }
        //modelo.desconectarBD(bd);
        modelo.desconectarBD(bd);
        return resultado;
    }


}
