package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.net.URISyntaxException;

/**
 * Created by Robert on 13.03.2017.
 */
public class AddGenreFilmController {
    @FXML
    private TextField nameGenre;
    @FXML
    private Label positivLabel;
    @FXML
    private Label negativLabel;
    @FXML
    private CheckBox useMorf;

    private File directoryPositiv;
    private File directoryNegativ;

    private Stage dialogStage;
    private Main main;
    private static final Logger log = LogManager.getLogger(ConnectionBase.class);



    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    public void setMain(Main main){
        this.main = main;
    }

    @FXML
    public void handlePositivFolder(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбор каталога для положительных отзывов");
        File file = new File(".");
        directoryChooser.setInitialDirectory(file);
        directoryPositiv = directoryChooser.showDialog(dialogStage);
        if(directoryPositiv != null){
            positivLabel.setText(directoryPositiv.getPath());
        }
    }
    @FXML
    public void handleNegativFolder(){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбор каталога для отрицательных отзывов");
        File file = new File(".");
        directoryChooser.setInitialDirectory(file);
        directoryNegativ = directoryChooser.showDialog(dialogStage);
        if(directoryNegativ != null){
            negativLabel.setText(directoryNegativ.getPath());
        }
    }
    @FXML
    public void handleAdd(){
        if(nameGenre.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка!");
            alert.setHeaderText("Пустое поле с названием обучающей выборки");
            alert.setContentText("Введите название обучающей выборки");
            alert.showAndWait();
        }else{
            if(directoryPositiv == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText("Не выбрана папка положительной обучающей выборкой");
                alert.setContentText("Выберите папку с положительной обучающей выборкой");
                alert.showAndWait();
            }else{
                if(directoryNegativ == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText("Не выбрана папка отрицательной обучающей выборкой");
                    alert.setContentText("Выберите папку с отрицательной обучающей выборкой");
                    alert.showAndWait();
                }else{
                    main.showCreateTemproraryFile("Создание временных файлов", directoryPositiv,directoryNegativ,true);
                    File directoryPositiv = new File("TEMP\\positiv");
                    File directoryNegativ = new File("TEMP\\negativ");
                    main.showProgressBarDownload(directoryPositiv,directoryNegativ,nameGenre.getText(),useMorf.isSelected());

                    dialogStage.close();

                }
            }
        }
    }



}
