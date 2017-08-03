package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;


/**
 * Created by Robert on 09.01.2017.
 */
public class OpinionController {
    @FXML
    private TextArea textAreaUser;
    @FXML
    private Label labelOpinion;
    @FXML
    private MenuButton menuButton;
    @FXML
    private CheckBox morfSDK;
    @FXML
    private ObservableList<MenuItem> listMenuItem = FXCollections.observableArrayList();

    private Stage dialogStage;
    private int idMenuItem = 0;
    private Main main;

    @FXML
    private void initialize() {
        ConnectionBase con = ConnectionBase.getInstance();
        ArrayList<String> listGenreWithOutMorf = con.getListNameGenreDBWithOutMorf();
        for (int indexList = 0; indexList < listGenreWithOutMorf.size(); indexList++) {
            String name = listGenreWithOutMorf.get(indexList);
            MenuItem menuItem = new MenuItem();
            menuItem.setText(name + "(Морфология:Нет)");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menuButton.setText(menuItem.getText());
                    main.setNameGenre(menuItem.getText());
                }
            });
            listMenuItem.add(menuItem);
        }
        ArrayList<String> listGenreWithMorf = con.getListNameGenreDBWithMorf();
        for (int indexList = 0; indexList < listGenreWithMorf.size(); indexList++) {
            String name = listGenreWithMorf.get(indexList);
            MenuItem menuItem = new MenuItem();
            menuItem.setText(name + "(Морфология:Да)");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menuButton.setText(menuItem.getText());
                    main.setNameGenre(menuItem.getText());
                }
            });
            listMenuItem.add(menuItem);
        }
        menuButton.getItems().addAll(listMenuItem);
    }

    public void setMain(Main main){
        this.main = main;
    }
    @FXML
    private void handleTest() throws IOException {
        int count = 1;
        File result = new File("result.txt");
        result.canExecute();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Выбор каталога для отрицательных отзывов");
        File file = new File(".");
        directoryChooser.setInitialDirectory(file);
        File folder = directoryChooser.showDialog(dialogStage);
        File[] listFolder = folder.listFiles();
        FileWriter writer = new FileWriter(result);
        StringBuffer sbResult = new StringBuffer();
        sbResult.append("Результаты тестирования\r\n");
        for(int idTraining = 0; idTraining < listMenuItem.size(); idTraining++ ) {
            main.setNameGenre(listMenuItem.get(idTraining).getText());
            for(int morf = 0; morf < 2; morf++) {
                if(morf == 0){
                    morfSDK.setSelected(false);
                    sbResult.append("Без морфологии\r\n");
                }else{
                    morfSDK.setSelected(true);
                    sbResult.append("С морфологией\r\n");
                }
                for (int indexFolder = 0; indexFolder < listFolder.length; indexFolder++) {
                    File folderPositiv = new File(listFolder[indexFolder].getAbsolutePath() + "\\позитив");
                    File[] listFilePositiv = folderPositiv.listFiles();
                    int countResultPositiv = 0;
                    for (int indexFile = 0; indexFile < listFilePositiv.length; indexFile++) {
                        StringBuffer sb = new StringBuffer();
                        BufferedReader reader = new BufferedReader(new FileReader(listFilePositiv[indexFile]));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + " ");
                        }
                        textAreaUser.setText(sb.toString());
                        System.out.println(count++ + " из 480");
                        handleSend();
                        if (labelOpinion.getText() == "Положительный") {
                            countResultPositiv++;
                        }
                    }
                    sbResult.append("Обучающая выборка " + listMenuItem.get(idTraining).getText() + "\r\n");
                    sbResult.append(listFolder[indexFolder].getName() + "(позитив) " + countResultPositiv + " из " + listFilePositiv.length + "\r\n");

                    File folderNegativ = new File(listFolder[indexFolder].getAbsolutePath() + "\\негатив");
                    File[] listFileNegativ = folderNegativ.listFiles();
                    int countResultNegativ = 0;
                    for (int indexFile = 0; indexFile < listFilePositiv.length; indexFile++) {
                        StringBuffer sb = new StringBuffer();
                        BufferedReader reader = new BufferedReader(new FileReader(listFileNegativ[indexFile]));
                        String line;
                        while ((line = reader.readLine()) != null) {
                            sb.append(line + " ");
                        }
                        textAreaUser.setText(sb.toString());
                        System.out.println(count++ + " из 480");
                        handleSend();
                        if (labelOpinion.getText() == "Отрицательный") {
                            countResultNegativ++;
                        }
                    }
                    sbResult.append("Обучающая выборка " + listMenuItem.get(idTraining).getText() + "\r\n");
                    sbResult.append(listFolder[indexFolder].getName() + "(негатив) " + countResultNegativ + " из " + listFileNegativ.length + "\r\n");
                }
            }
        }
        writer.write(sbResult.toString());
        writer.flush();
        writer.close();
    }
    @FXML
    private void handleSend() {
        labelOpinion.setText("-");
        labelOpinion.setStyle("-fx-background-color: #000000");
        Classifier classifier = new Classifier();
       if(main.getNameGenre() != null && textAreaUser.getText().length() != 0){
           double opinion = 0;
           if(morfSDK.isSelected()) {
               File folderWithMorfSDK = new File("treatmentUser");
               File userFile = new File(folderWithMorfSDK.getAbsolutePath() + "\\tempUser.txt");
               File tempFile = new File(folderWithMorfSDK.getAbsolutePath() + "\\tempTreatmentUser.txt");

               ProcessBuilder processBuilder = new ProcessBuilder(folderWithMorfSDK.getAbsolutePath() + "\\runner.bat",
                       folderWithMorfSDK.getAbsolutePath() + "\\treatmentUser.jar", userFile.getAbsolutePath(), tempFile.getAbsolutePath());
               processBuilder.redirectErrorStream(true);
               Process process = null;
               int indexFile = 0;

               try {
                   userFile.createNewFile();
                   tempFile.createNewFile();
                   FileOutputStream fileOutputStream = new FileOutputStream(userFile);
                   BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));

                   String text = textAreaUser.getText().toLowerCase();
                   text = text.replaceAll("[^а-яА-Я]", " ");
                   text = text.replaceAll(" +", " ");

                   bw.write(text);
                   bw.flush();
                   fileOutputStream.flush();
                   fileOutputStream.close();

                   process = processBuilder.start();
                   BufferedReader reader1 = new BufferedReader(new InputStreamReader(process.getInputStream()));
                   String line1 = null;
                   int i = 0;
                   while ((line1 = reader1.readLine()) != null){
                        i++;
                   }
                   int exitVal = process.waitFor();

                   BufferedReader reader = new BufferedReader(new FileReader(tempFile));
                   StringBuffer sb = new StringBuffer();
                   String line;
                   while ((line = reader.readLine()) != null) {
                       sb.append(line + " ");
                   }
                   reader.close();
                   tempFile.delete();
                   userFile.delete();
                   opinion = classifier.getOpinion(sb.toString(), main.getNameGenre());
               } catch (InterruptedException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }else{
               opinion = classifier.getOpinion(textAreaUser.getText(),main.getNameGenre());
           }
           if(opinion > 0){
               labelOpinion.setText("Положительный");
               labelOpinion.setStyle("-fx-background-color: #00FF00");
           }else{
               labelOpinion.setText("Отрицательный");
               labelOpinion.setStyle("-fx-background-color: #FF0000");
           }
       }else{
           if(main.getNameGenre() == null){
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Ошибка!");
               alert.setHeaderText("Не выбран жанр обучающей выборки");
               alert.setContentText("Для выбора жанра фильма нажмите\"Жанр\"");
               alert.showAndWait();
           }else{
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Ошибка!");
               alert.setHeaderText("Отзыв пуст!");
               alert.setContentText("Напишите свой отзыв пожалуйста");
               alert.showAndWait();
           }

       }
    }
    @FXML
    public void updateMenuButton(){
        listMenuItem.remove(0,listMenuItem.size());
        ConnectionBase con = ConnectionBase.getInstance();
        ArrayList<String> listGenreWithOutMorf = con.getListNameGenreDBWithOutMorf();
        for(int indexList = 0; indexList < listGenreWithOutMorf.size(); indexList++){
            String name = listGenreWithOutMorf.get(indexList);
            MenuItem menuItem = new MenuItem();
            menuItem.setText(name + "(Морфология:Нет)");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menuButton.setText(menuItem.getText());
                    main.setNameGenre(menuItem.getText());
                }
            });
            listMenuItem.add(menuItem);
        }
        ArrayList<String> listGenreWithMorf = con.getListNameGenreDBWithMorf();
        for(int indexList = 0; indexList < listGenreWithMorf.size(); indexList++){
            String name = listGenreWithMorf.get(indexList);
            MenuItem menuItem = new MenuItem();
            menuItem.setText(name + "(Морфология:Да)");
            menuItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    menuButton.setText(menuItem.getText());
                    main.setNameGenre(menuItem.getText());
                }
            });
            listMenuItem.add(menuItem);
        }
        if (listMenuItem.size() == 0){
            menuButton.setText("Жанр");
        }
        menuButton.getItems().remove(0,menuButton.getItems().size());
        menuButton.getItems().addAll(listMenuItem);
    }

}
