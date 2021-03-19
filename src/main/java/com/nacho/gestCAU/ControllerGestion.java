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

import java.sql.Date;
import java.time.LocalDate;

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
    private int numeroinci;

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

        ObservableList<String> listaEstados = FXCollections.observableArrayList("En curso","Solucionada");
        cbEstado.setItems(listaEstados);

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
    @FXML
    private void rellenarDatos(){
        ObservableList<Incidenciasmysql> filaseleccionada = FXCollections.observableArrayList();
        filaseleccionada=tblGestionIncidencias.getSelectionModel().getSelectedItems();


        txtDescripcionIncidencia.setText(filaseleccionada.get(0).getDescripcion());
        txtNombre.setText(filaseleccionada.get(0).getNombre());
        txtApellidos.setText(filaseleccionada.get(0).getApellidos());
        txtEmail.setText(filaseleccionada.get(0).getEmail());
        dpFechaCreacion.setValue(filaseleccionada.get(0).getFechaCreacion().toLocalDate());
        cbEstado.setValue(filaseleccionada.get(0).getEstado());
        numeroinci=filaseleccionada.get(0).getCodIncidencia();


    }

    @FXML
    private void modifIncidencia(){
        String resultado="";
        String checkFecha="";
        //LocalDate fechaCreacion=null;

        if (txtDescripcionIncidencia.getText().isEmpty()){
            resultado="Error";
            Mensajeria.mostrarError("Error al validar","Es necesario introducir una descripción de incidencia.");
        }

        //Compruebo si el combo de fecha de creación tiene valor o no
        if (dpFechaSolucion.getValue()==null){ checkFecha="null"; }

        if (cbEstado.getValue().toString()=="Solucionada" && checkFecha=="null"){
            resultado="Error";
            Mensajeria.mostrarError("Error al validar","Si la incidencia está solucionada, es necesario introducir una fecha de solución");
        }

        if (resultado.isEmpty()){
            //Conectamos con la base de datos
            Model modelo = new Model();
            resultado=modelo.conectarBD(bd);

            if (resultado.isEmpty()){
                if (checkFecha=="null"){
                    Mensajeria.mostrarInfo("Antes de modelo.updateIncidencia","Fecha Nula" + txtDescripcionIncidencia.getText() +Date.valueOf(dpFechaSolucion.getValue()) + numeroinci + cbEstado.getValue()+ txtNombre.getText()+txtApellidos.getText()+txtEmail.getText());
                    resultado=modelo.updateIncidencia(txtDescripcionIncidencia.getText(), null,numeroinci,usu,bd,cbEstado.getValue().toString(),txtNombre.getText(),txtApellidos.getText(),txtEmail.getText());
                }else{
                    resultado=modelo.updateIncidencia(txtDescripcionIncidencia.getText(),dpFechaSolucion.getValue(),numeroinci,usu,bd,cbEstado.getValue().toString(),txtNombre.getText(),txtApellidos.getText(),txtEmail.getText());
                }

                if (resultado.isEmpty()){
                    //Si no hay error, muestro el mensaje y refresco la tabla.
                    Mensajeria.mostrarInfo("Actualizar incidencia","Incidencia modificada con éxito.");
                    txtDescripcionIncidencia.setText("");
                    dpFechaSolucion.setValue(null);
                    txtNombre.setText("");
                    txtApellidos.setText("");
                    txtEmail.setText("");
                }else{
                    Mensajeria.mostrarError("Actualizar Incidencia","Error al actualizar la incidencia."+"\n"+resultado);
                }
            }else{
                Mensajeria.mostrarError("Actualizar Incidencia","Error al conectar a la base de datos."+"\n"+resultado);
            }
            refrescaTabla();
            modelo.desconectarBD(bd);
        }
    }

    @FXML
    private void salir(){
        System.exit(0);
    }

}
