package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;

import java.io.*;

/**
 * Created by Robert on 16.05.2017.
 */
public class CreateTemporaryFileController {
    @FXML
    Label label;
    @FXML
    ProgressIndicator progressIndicator;

    private Stage dialogStage;
    private Main main;
    private boolean control;
    private File directoryPositiv = null;
    private File directoryNegativ = null;

    private Thread thread;

    private Task taskCreate = new Task() {
        @Override
        protected Object call() throws Exception {
            File[] listFileInPositiv = directoryPositiv.listFiles();
            File[] listFileInNegativ = directoryNegativ.listFiles();

            for(int indexFile = 0; indexFile < listFileInPositiv.length; indexFile++){
                File newFile = new File("TEMP\\positiv\\" + listFileInPositiv[indexFile].getName());
                try {
                    newFile.createNewFile();
                    copyFile(listFileInPositiv[indexFile],newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            for(int indexFile = 0; indexFile < listFileInNegativ.length; indexFile++){
                File newFile = new File("TEMP\\negativ\\" + listFileInNegativ[indexFile].getName());
                try {
                    newFile.createNewFile();
                    copyFile(listFileInNegativ[indexFile],newFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Platform.runLater(() -> {
                dialogStage.close();
            });
            Thread.interrupted();
            return null;
        }
    };

    private Task taskDelete = new Task() {
        @Override
        protected Object call() throws Exception {
            File folderTemp = new File("TEMP");
            File folderPositiv = new File("TEMP\\positiv");
            File folderNegativ = new File("TEMP\\negativ");

            File[] listFileInPositiv = folderPositiv.listFiles();
            File[] listFileInNegativ = folderNegativ.listFiles();

            for (int indexFile = 0; indexFile < listFileInPositiv.length; indexFile++){
                listFileInPositiv[indexFile].delete();
            }
            for(int indexFile = 0; indexFile < listFileInNegativ.length; indexFile++){
                listFileInNegativ[indexFile].delete();
            }
            folderPositiv.delete();
            folderNegativ.delete();
            folderTemp.delete();

            Platform.runLater(() -> {
                dialogStage.close();
            });

            Thread.interrupted();
            return null;
        }
    };

    public void setDialogStage(Stage dialogStage){
        this.dialogStage = dialogStage;
    }
    public void setMain(Main main){
        this.main = main;
    }
    public void setFileDirectory(File directoryPositiv, File directoryNegativ){
        this.directoryPositiv = directoryPositiv;
        this.directoryNegativ = directoryNegativ;
    }
    public void setData(String text, boolean conrol){
        this.control = conrol;
        progressIndicator.progressProperty().bind(taskCreate.progressProperty());
        label.setText(text);
        if(conrol){
            File folderPositiv = new File("TEMP\\positiv");
            File folderNegativ = new File("TEMP\\negativ");
            folderPositiv.mkdirs();
            folderNegativ.mkdirs();

            thread = new Thread(taskCreate);
            thread.setDaemon(true);
            thread.start();
        }else{
            thread = new Thread(taskDelete);
            thread.setDaemon(true);
            thread.start();
        }

    }
    private void copyFile(File original, File duplicate){
        try {
            InputStream inputStream = new FileInputStream(original);
            OutputStream outputStream = new FileOutputStream(duplicate);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0){
                outputStream.write(buffer,0,length);
            }
            inputStream.close();
            outputStream.close();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}