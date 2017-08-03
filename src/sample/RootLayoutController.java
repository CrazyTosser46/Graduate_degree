package sample;


import javafx.fxml.FXML;

public class RootLayoutController {

    private Main main;

    public void setMain(Main main){
        this.main = main;
    }
    @FXML
    public void handleClose(){
        System.exit(0);
    }
    @FXML
    public void handleGenreFilmShow(){
        main.showGenreFilm();
    }

}
