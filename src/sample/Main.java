package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Main extends Application {

    private String nameGenre = null;

    private Stage primaryStage;
    private BorderPane rootLayout;

    @Override
    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Анализ отзыва");
        primaryStage.show();

        initRootLayout();

        showOpinion();
    }

    @Override
    public void stop(){
            ConnectionBase.getInstance().closeConnection();
    }

    public void initRootLayout(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            RootLayoutController rootLayoutController = loader.getController();
            rootLayoutController.setMain(this);

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void showOpinion(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/OpinionShow.fxml"));
            AnchorPane opinionOverview = (AnchorPane) loader.load();

            rootLayout.setCenter(opinionOverview);

            OpinionController opinionController = loader.getController();
            opinionController.setMain(this);

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showGenreFilm(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/GenreFilmShow.fxml"));
            AnchorPane genreOverview = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Выбор жанра");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(genreOverview);
            dialogStage.setScene(scene);

            GenreFilmController genreFilmController = loader.getController();
            genreFilmController.setDialogStage(dialogStage);
            genreFilmController.setMain(this);

            dialogStage.showAndWait();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showAddGenreFilm(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/AddGenreFilm.fxml"));
            AnchorPane addGenre = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Добавить жанр");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(addGenre);
            dialogStage.setScene(scene);

            AddGenreFilmController addGenreFilmController = loader.getController();
            addGenreFilmController.setDialogStage(dialogStage);
            addGenreFilmController.setMain(this);

            dialogStage.showAndWait();

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void showProgressBarDownload(File directoryPositiv, File directoryNegativ, String name, boolean useMorf){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/ProgressBarDownload.fxml"));
            AnchorPane progressBarUnigramm = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Загрузка обучающей выборки");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(progressBarUnigramm);
            dialogStage.setScene(scene);

            ProgressBarDownloadController progressBarDownloadController = loader.getController();
            progressBarDownloadController.setDialogStage(dialogStage);
            progressBarDownloadController.setMain(this);
            progressBarDownloadController.setData(directoryPositiv,directoryNegativ,name,useMorf);

            dialogStage.showAndWait();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void showCreateTemproraryFile(String text,File directoryPositiv, File directoryNegativ, boolean control){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CreateTemporaryFile.fxml"));
            AnchorPane progressBarUnigramm = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создание временных файлов");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(progressBarUnigramm);
            dialogStage.setScene(scene);

            CreateTemporaryFileController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setFileDirectory(directoryPositiv,directoryNegativ);
            controller.setData(text,control);


            dialogStage.showAndWait();
        }catch (Exception ex){
        ex.printStackTrace();
        }
    }

    public void showDeleteTemproraryFile(String text, boolean control){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("view/CreateTemporaryFile.fxml"));
            AnchorPane progressBarUnigramm = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Создание временных файлов");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(progressBarUnigramm);
            dialogStage.setScene(scene);

            CreateTemporaryFileController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setData(text,control);


            dialogStage.showAndWait();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    public void setNameGenre(String nameGenre){
        this.nameGenre = nameGenre;
    }

    public String getNameGenre(){
        return nameGenre;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
