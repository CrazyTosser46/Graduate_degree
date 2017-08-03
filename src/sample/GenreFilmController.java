package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.POJO.DataTable;
import java.util.ArrayList;

/**
 * Created by Robert on 13.03.2017.
 */
public class GenreFilmController {

    private ObservableList<DataTable> dataTable = FXCollections.observableArrayList();

    @FXML
    private TableView<DataTable> genreTable;
    @FXML
    private TableColumn<DataTable,Integer> idColumn;
    @FXML
    private TableColumn<DataTable,String> genreColumn;
    @FXML
    private TableColumn<DataTable, String> useMorf;
    @FXML
    private TableColumn<DataTable,Integer> sizePositivColumn;
    @FXML
    private TableColumn<DataTable,Integer> sizeNegativColumn;

    private int sizeInitList;
    private String nameGenre;
    private Stage dialogStage;
    private Main main;

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    public void setMain(Main main){
        this.main = main;
    }
    @FXML
    private void initialize() {

        ConnectionBase con = ConnectionBase.getInstance();
        ArrayList<String> listGenreWithOutMorf = con.getListNameGenreDBWithOutMorf();
        int indexListTable = 1;
        for(int indexList = 0; indexList < listGenreWithOutMorf.size(); indexList++){
            String name = listGenreWithOutMorf.get(indexList);
            int type = 0;
            int sizePositiv = con.getGenreSize(name,1);
            int sizeNegativ = con.getGenreSize(name,0);
            dataTable.add(new DataTable(indexListTable++,name,"Нет",sizePositiv,sizeNegativ));
        }
        ArrayList<String> listGenreWithMorf = con.getListNameGenreDBWithMorf();
        for(int indexList = 0; indexList < listGenreWithMorf.size(); indexList++){
            String name = listGenreWithMorf.get(indexList);
            int type = 0;
            int sizePositiv = con.getGenreSize(name,1);
            int sizeNegativ = con.getGenreSize(name,0);
            dataTable.add(new DataTable(indexListTable++,name,"Да",sizePositiv,sizeNegativ));
        }

        sizeInitList = listGenreWithMorf.size() + listGenreWithOutMorf.size();

        idColumn.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("id"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<DataTable, String>("name"));
        useMorf.setCellValueFactory(new PropertyValueFactory<DataTable, String>("useMorf"));
        sizePositivColumn.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("sizePositiv"));
        sizeNegativColumn.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("sizeNegativ"));

        genreTable.setItems(dataTable);

    }
    @FXML
    public void handleAddGenre() {
        main.showAddGenreFilm();
        updateTable();
    }
    @FXML
    public void deleteGenre() {
        int row = genreTable.getSelectionModel().getSelectedIndex();
        DataTable temp = genreTable.getItems().get(row);
        ConnectionBase con = ConnectionBase.getInstance();
        int useMorf = 0;
        if(temp.getUseMorf().compareToIgnoreCase("Да") == 0){
            useMorf = 1;
        }
        if(con.deleteGenre(temp.getName(),useMorf)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Удаление данных");
            alert.setHeaderText("Удаление данных успешно завершенно!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Удаление данных");
            alert.setHeaderText("Произошла ошибка при удалении данных из БД");
            alert.setContentText("Посмотрите log файл");
            alert.showAndWait();
        }
        updateTable();
    }
    @FXML
    public void handleClose(){
        dialogStage.close();
    }

    private void updateTable() {
        Platform.runLater(() -> {
            int id = 1;
            dataTable.remove(0,dataTable.size());
            genreTable.getItems().removeAll();
            genreTable.refresh();
            ConnectionBase con = ConnectionBase.getInstance();
            ArrayList<String> listGenreWithOutMorf = con.getListNameGenreDBWithOutMorf();
            for(int indexList = 0; indexList < listGenreWithOutMorf.size(); indexList++){
                String name = listGenreWithOutMorf.get(indexList);
                int type = 0;
                int sizePositiv = con.getGenreSize(name,1);
                int sizeNegativ = con.getGenreSize(name,0);
                dataTable.add(new DataTable(id++,name,"Нет",sizePositiv,sizeNegativ));
            }
            ArrayList<String> listGenreWithMorf = con.getListNameGenreDBWithMorf();
            for(int indexList = 0; indexList < listGenreWithMorf.size(); indexList++){
                String name = listGenreWithMorf.get(indexList);
                int type = 0;
                int sizePositiv = con.getGenreSize(name,1);
                int sizeNegativ = con.getGenreSize(name,0);
                dataTable.add(new DataTable(id++,name,"Да",sizePositiv,sizeNegativ));
            }
            genreTable.setItems(dataTable);
            genreTable.refresh();
        });
    }
}
