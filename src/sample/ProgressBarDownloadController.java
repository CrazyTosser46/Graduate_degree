package sample;


import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.*;

/**
 * Created by Robert on 15.03.2017.
 */
public class ProgressBarDownloadController {

    @FXML
    private ProgressBar pbCommonDownload;
    @FXML
    private ProgressIndicator piPositiv;
    @FXML
    private ProgressIndicator piNegativ;
    @FXML
    private Label labelFilePositivTop;
    @FXML
    private Label labelFilePositivBot;
    @FXML
    private Label labelFileNegativTop;
    @FXML
    private Label labelFileNegativBot;


    private Stage dialogStage;
    private Main main;
    private File directoryPositiv;
    private File directoryNegativ;
    private String nameGenre;
    private ConnectionBase con = ConnectionBase.getInstance();
    private int indexPositiv = 0;
    private int indexNegativ = 0;
    private int indexCommon;
    private int commonSize;
    private boolean useMorf = false;
    private int useMorfForDB = 0;
    private ArrayList<String> arrayPrepos = new ArrayList<>();
    private static final Logger log = LogManager.getLogger("fileForLogin");

    private Thread threadPositiv;
    private Thread threadNegativ;
    private Thread threadCommon;

    private Task taskPositiv = new Task() {
        @Override
        protected Object call() throws Exception {
            HashMap<String, Integer> listWordWithCount = new HashMap<>();
            File[] listPositivFile = directoryPositiv.listFiles();
            con.writeGenreDB(nameGenre, 1, useMorfForDB, listPositivFile.length);
            int id = con.getId(nameGenre, 1, useMorfForDB);
            for (int indexFile = 0; indexFile < listPositivFile.length; indexFile++) {
                updateProgress(indexFile, listPositivFile.length);
                updateMessage("Чтение из " + listPositivFile[indexFile].getName());

                HashSet<String> listWordUnigrammInFile = new HashSet<>();
                HashSet<String> listWordBigrammInFile = new HashSet<>();
                StringBuffer sb = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(listPositivFile[indexFile]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp + " ");
                }
                String text = sb.toString();
                text = text.toLowerCase();
                text = text.replaceAll("[^а-яА-Я]"," ");
                text = text.replaceAll(" +"," ");

                String[] words = text.split("\\s+");
                text = "";

                for(int i = 0; i < words.length; i++){
                    if(comparePreposInArray(words[i])){
                        if(i == 0){
                            text = text + words[i];
                        }else{
                            text = text + " " + words[i];
                        }
                    }
                }
                words = null;
                words = text.split("\\s+");
                for (int indexWord = 0; indexWord < words.length; indexWord++) {
                    String word = words[indexWord].toLowerCase();
                    if (!listWordUnigrammInFile.contains(word)){
                        if(listWordWithCount.containsKey(word)){
                            listWordWithCount.put(word,listWordWithCount.get(word) + 1);
                        }else{
                            listWordWithCount.put(word,1);
                        }
                        listWordUnigrammInFile.add(word);
                    }
                    if (indexWord < words.length - 1) {
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(words[indexWord])
                                .append(" ")
                                .append(words[indexWord + 1]);

                        String bigramm = stringBuffer.toString().toLowerCase();
                        if (!listWordBigrammInFile.contains(bigramm)) {
                            if(listWordWithCount.containsKey(bigramm)){
                                listWordWithCount.put(bigramm,listWordWithCount.get(bigramm) + 1);
                            }else{
                                listWordWithCount.put(bigramm,1);
                            }
                            listWordBigrammInFile.add(bigramm);
                        }
                    }
                }
                updateProgress(indexFile + 1, listPositivFile.length);
                indexPositiv = indexFile;
                updateIndex();
            }
            con.writeWordDB(listWordWithCount,id);

            Thread.interrupted();
            return null;
        }
    };

    private Task taskNegativ = new Task() {
        @Override
        protected Object call() throws Exception {
            HashMap<String, Integer> listWordWithCount = new HashMap<>();
            File[] listNegativFile = directoryNegativ.listFiles();
            con.writeGenreDB(nameGenre, 0, useMorfForDB, listNegativFile.length);
            int id = con.getId(nameGenre, 0, useMorfForDB);
            int count = 0;
            for (int indexFile = 0; indexFile < listNegativFile.length; indexFile++) {
                updateProgress(indexFile, listNegativFile.length);
                updateMessage("Чтение из " + listNegativFile[indexFile].getName());

                HashSet<String> listWordUnigrammInFile = new HashSet<>();
                HashSet<String> listWordBigrammInFile = new HashSet<>();
                StringBuffer sb = new StringBuffer();
                FileInputStream fileInputStream = new FileInputStream(listNegativFile[indexFile]);
                BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));
                String temp;
                while ((temp = reader.readLine()) != null) {
                    sb.append(temp + " ");
                }
                String text = sb.toString();
                text = text.toLowerCase();
                text = text.replaceAll("[^а-я^А-Я^0-9]"," ");
                text = text.replaceAll(" +"," ");

                String[] words = text.split("\\s+");
                text = "";

                for(int i = 0; i < words.length; i++){
                    if(comparePreposInArray(words[i])){
                        if(i == 0){
                            text = text + words[i];
                        }else{
                            text = text + " " + words[i];
                        }
                    }
                }
                words = null;
                words = text.split(" +");
                for (int indexWord = 0; indexWord < words.length; indexWord++) {
                    String word = words[indexWord].toLowerCase();
                    if(!listWordUnigrammInFile.contains(word)){
                        if(listWordWithCount.containsKey(word)){
                            listWordWithCount.put(word,listWordWithCount.get(word) + 1);
                        }else{
                            listWordWithCount.put(word,1);
                        }
                        listWordUnigrammInFile.add(word);
                    }
                    if(indexWord < words.length - 1){
                        StringBuffer stringBuffer = new StringBuffer();
                        stringBuffer.append(words[indexWord])
                                    .append(" ")
                                    .append(words[indexWord + 1]);
                        String bigramm = stringBuffer.toString().toLowerCase();
                        if(!listWordBigrammInFile.contains(bigramm)){
                            if(listWordWithCount.containsKey(bigramm)){
                                listWordWithCount.put(bigramm,listWordWithCount.get(bigramm) + 1);
                            }else{
                                listWordWithCount.put(bigramm,1);
                            }
                            listWordBigrammInFile.add(bigramm);
                        }
                    }
                }
                updateProgress(indexFile+1, listNegativFile.length);
                indexNegativ = indexFile;
                updateIndex();
            }
            con.writeWordDB(listWordWithCount,id);
            Thread.interrupted();
            return null;
        }


    };
    private Task taskCommon = new Task() {
        @Override
        protected Object call() throws Exception {
            while(indexCommon < commonSize){
                updateProgress(indexCommon,commonSize);
            }
            updateProgress(commonSize,commonSize);
            endDownload();
            Thread.interrupted();
            return null;
        }
    };

    private Task taskPositivMorf = new Task() {
        @Override
        protected Object call() throws Exception {
            HashMap<String, Integer> listWordWithCount = new HashMap<>();
            File[] listFile = directoryPositiv.listFiles();
            con.writeGenreDB(nameGenre, 1, useMorfForDB, listFile.length);
            int id = con.getId(nameGenre, 1, useMorfForDB);

            updateLabelPositivTop("Обработка данных");
            try {
                for (int indexFile = 0; indexFile < listFile.length; indexFile++) {

                    updateProgress(indexFile, listFile.length);
                    indexPositiv++;
                            updateIndex();
                    updateMessage("Чтение из " + listFile[indexFile].getName());

                    StringBuffer sb = new StringBuffer();
                    FileInputStream fileInputStream = new FileInputStream(listFile[indexFile]);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    String text = sb.toString().toLowerCase();
                    text = text.replaceAll("[^а-яА-Я]", " ");
                    text = text.replaceAll(" +", " ");

                    FileOutputStream fileOutputStream = new FileOutputStream(listFile[indexFile]);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
                    bw.write(text);
                    bw.flush();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

            } catch (UnsupportedEncodingException ex) {
                log.log(Level.ERROR,"Ошибка в taskPositivMorf", ex);
            } catch (FileNotFoundException ex) {
                log.log(Level.ERROR,"Файл был не найден",ex);
            } catch (IOException ex) {
                log.log(Level.ERROR,"Ошибка в taskPositivMorf",ex);
            }
            updateMessage("Запуск библиотеки");
            updateProgress(0,listFile.length);
            updateLabelPositivTop("Морфологическая обработка");

            File folderWithMorfSDK = new File("threadPositiv");
            File tempFile = new File("tempPositiv.txt");

            ProcessBuilder processBuilder = new ProcessBuilder(folderWithMorfSDK.getAbsolutePath()+"\\runner.bat",
                    folderWithMorfSDK.getAbsolutePath()+"\\untitled.jar", directoryPositiv.getAbsolutePath(), tempFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            Process process = null;
            Thread.sleep(1000);
            int indexFile = 0;
            try {
                tempFile.createNewFile();
                process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                int i = 0;
                while ((line = reader.readLine()) != null) {
                    System.out.println(i++);
                    if (line.compareToIgnoreCase("!!!") == 0) {
                        updateProgress(indexFile+1, listFile.length);
                        updateMessage("Обработка файла " + listFile[indexFile++].getName());
                        indexPositiv++;
                        updateIndex();
                    }
                }
                int exitVal = process.waitFor();

                updateMessage("Загрузка данных...");
                updateLabelPositivTop("Подсчёт униграмм и биграмм");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile));
                StringBuffer sb = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null){
                    sb.append(line + " ");
                }
                String[] opinion = sb.toString().split("!!!");
                for(int indexOpinion = 1; indexOpinion < opinion.length; indexOpinion++) {
                    HashSet<String> listWordUnigrammInFile = new HashSet<>();
                    HashSet<String> listWordBigrammInFile = new HashSet<>();

                    updateProgress(indexOpinion+1,opinion.length);
                    indexPositiv++;
                    updateIndex();
                    updateMessage("Подсчёт из файла " + listFile[indexOpinion-1].getName());
                    String[] words = opinion[indexOpinion].split("\\s+");
                    for (int indexWord = 0; indexWord < words.length; indexWord++) {
                        String word = words[indexWord].toLowerCase();
                        if (!listWordUnigrammInFile.contains(word)) {
                            if(listWordWithCount.containsKey(word)) {
                                listWordWithCount.put(word, listWordWithCount.get(word) + 1);
                            } else {
                                listWordWithCount.put(word, 1);
                            }
                            listWordUnigrammInFile.add(word);
                        }
                        if (indexWord < words.length - 1) {
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(words[indexWord])
                                    .append(" ")
                                    .append(words[indexWord + 1]);
                            String bigramm = stringBuffer.toString().toLowerCase();
                            if (!listWordBigrammInFile.contains(bigramm)) {
                                if (listWordWithCount.containsKey(bigramm)) {
                                    listWordWithCount.put(bigramm, listWordWithCount.get(bigramm) + 1);
                                } else {
                                    listWordWithCount.put(bigramm, 1);
                                }
                                listWordBigrammInFile.add(bigramm);
                            }
                        }
                    }
                }
            } catch (InterruptedException ex) {
                log.log(Level.ERROR,"Ошибка при запуске нового процесса для MorfSDK (taskPositivMorf)",ex);
            } catch (IOException ex) {
                log.log(Level.ERROR,"Ошибка при запуске нового процесса для MorfSDK (taskPositivMorf)",ex);
            }
            tempFile.delete();
            updateLabelPositivTop("Запись данных в базу данных");
            updateMessage("Запись даннных...");
            con.writeWordDB(listWordWithCount,id);
            updateMessage("Запись данных завершена");
            Thread.interrupted();
            return null;
        }
    };
    private Task taskNegativMorf = new Task() {
        @Override
        protected Object call() throws Exception {
            HashMap<String, Integer> listWordWithCount = new HashMap<>();
            File[] listFile = directoryNegativ.listFiles();
            con.writeGenreDB(nameGenre, 0, useMorfForDB, listFile.length);
            int id = con.getId(nameGenre, 0, useMorfForDB);

            updateLabelNegativTop("Обработка данных");
            try {
                for (int indexFile = 0; indexFile < listFile.length; indexFile++) {

                    updateProgress(indexFile, listFile.length);
                    indexNegativ ++;
                    updateIndex();
                    updateMessage("Чтение из " + listFile[indexFile].getName());

                    StringBuffer sb = new StringBuffer();
                    FileInputStream fileInputStream = new FileInputStream(listFile[indexFile]);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream, "utf-8"));

                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    String text = sb.toString().toLowerCase();
                    text = text.replaceAll("[^а-яА-Я]", " ");
                    text = text.replaceAll(" +", " ");

                    FileOutputStream fileOutputStream = new FileOutputStream(listFile[indexFile]);
                    BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fileOutputStream, "UTF-8"));
                    bw.write(text);
                    bw.flush();
                    fileOutputStream.flush();
                    fileOutputStream.close();
                }

            } catch (UnsupportedEncodingException ex) {
                log.log(Level.ERROR,"Ошибка в taskNegativMorf");
            } catch (FileNotFoundException ex) {
                log.log(Level.ERROR,"Файл был не найден");
            } catch (IOException ex) {
                log.log(Level.ERROR,"Ошибка в taskNegativMorf");
            }
            updateMessage("Запуск библиотека");
            updateProgress(0,listFile.length);
            updateLabelNegativTop("Морфологическая обработка");

            File folderWithMorfSDK = new File("threadNegativ");
            File tempFile = new File("tempNegativ.txt");

            ProcessBuilder processBuilder = new ProcessBuilder(folderWithMorfSDK.getAbsolutePath()+"\\runner.bat",
                    folderWithMorfSDK.getAbsolutePath()+"\\untitled.jar", directoryNegativ.getAbsolutePath(), tempFile.getAbsolutePath());
            processBuilder.redirectErrorStream(true);
            Process process = null;
            int indexFile = 0;
            try {
                tempFile.createNewFile();
                process = processBuilder.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    if (line.compareToIgnoreCase("!!!") == 0) {
                        updateProgress(indexFile, listFile.length);
                        updateMessage("Обработка файла " + listFile[indexFile++].getName());
                        indexNegativ++;
                        updateIndex();
                    }
                }
                int exitVal = process.waitFor();

                updateMessage("");
                updateLabelNegativTop("Подсчёт униграмм и биграмм");
                BufferedReader bufferedReader = new BufferedReader(new FileReader(tempFile));
                StringBuffer sb = new StringBuffer();
                while ((line = bufferedReader.readLine()) != null){
                    sb.append(line + " ");
                }
                String[] opinion = sb.toString().split("!!!");
                for(int indexOpinion = 1; indexOpinion < opinion.length; indexOpinion++) {
                    HashSet<String> listWordUnigrammInFile = new HashSet<>();
                    HashSet<String> listWordBigrammInFile = new HashSet<>();

                    updateProgress(indexOpinion+1,opinion.length);
                    indexNegativ++;
                    updateIndex();
                    updateMessage("Подсчёт из файла " + listFile[indexOpinion-1].getName());

                    String[] words = opinion[indexOpinion].split("\\s+");
                    for (int indexWord = 0; indexWord < words.length; indexWord++) {
                        String word = words[indexWord].toLowerCase();
                        if (!listWordUnigrammInFile.contains(word)) {
                            if (listWordWithCount.containsKey(word)) {
                                listWordWithCount.put(word, listWordWithCount.get(word) + 1);
                            } else {
                                listWordWithCount.put(word, 1);
                            }
                            listWordUnigrammInFile.add(word);
                        }
                        if (indexWord < words.length - 1) {
                            StringBuffer stringBuffer = new StringBuffer();
                            String bigramm = stringBuffer.toString().toLowerCase();
                            if (!listWordBigrammInFile.contains(bigramm)) {
                                if (listWordWithCount.containsKey(bigramm)) {
                                    listWordWithCount.put(bigramm, listWordWithCount.get(bigramm) + 1);
                                } else {
                                    listWordWithCount.put(bigramm, 1);
                                }
                                listWordBigrammInFile.add(bigramm);
                            }
                        }
                    }
                }
            } catch (InterruptedException ex) {
                log.log(Level.ERROR, "Ошибка при запуске нового процесса для MorfSDK (taskNegativMorf)", ex);
            } catch (IOException ex) {
                log.log(Level.ERROR, "Ошибка при запуске нового процесса для MorfSDK (taskNegativMorf)", ex);
            }
            tempFile.delete();
            updateLabelNegativTop("Запись данных в базу данных:");
            updateMessage("Запись даннных...");
            con.writeWordDB(listWordWithCount,id);
            updateMessage("Запись данных завершена");
            Thread.interrupted();
            return null;
        }
    };
    public ProgressBarDownloadController() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public void setData(File directoryPositiv, File directoryNegativ, String name, boolean useMorf) {

        this.directoryPositiv = directoryPositiv;
        this.directoryNegativ = directoryNegativ;
        this.nameGenre = name;
        this.useMorf = useMorf;
        if(useMorf){
            useMorfForDB = 1;
        }
        startTask();
    }
    public void startTask() {
        arrayPrepos.add("а");
        arrayPrepos.add("о");
        arrayPrepos.add("и");
        arrayPrepos.add("но");
        arrayPrepos.add("на");
        arrayPrepos.add("под");
        arrayPrepos.add("за");
        arrayPrepos.add("к");
        arrayPrepos.add("из");
        arrayPrepos.add("по");
        arrayPrepos.add("об");
        arrayPrepos.add("от");
        arrayPrepos.add("в");
        arrayPrepos.add("у");
        arrayPrepos.add("с");
        arrayPrepos.add("над");
        arrayPrepos.add("около");
        arrayPrepos.add("при");
        arrayPrepos.add("перед");

        if(useMorf){

            commonSize = (directoryPositiv.list().length * 3) + (directoryNegativ.list().length * 3) + 2;

            threadPositiv = new Thread(taskPositivMorf);
            threadPositiv.setDaemon(true);
            threadPositiv.start();
            piPositiv.progressProperty().bind(taskPositivMorf.progressProperty());
            labelFilePositivBot.textProperty().bind(taskPositivMorf.messageProperty());


            threadNegativ = new Thread(taskNegativMorf);
            threadNegativ.setDaemon(true);
            threadNegativ.start();
            piNegativ.progressProperty().bind(taskNegativMorf.progressProperty());
            labelFileNegativBot.textProperty().bind(taskNegativMorf.messageProperty());

        }else{

            commonSize = directoryPositiv.list().length + directoryNegativ.list().length;

            threadPositiv = new Thread(taskPositiv);
            threadPositiv.setDaemon(true);
            threadPositiv.start();
            piPositiv.progressProperty().bind(taskPositiv.progressProperty());
            labelFilePositivBot.textProperty().bind(taskPositiv.messageProperty());

            threadNegativ = new Thread(taskNegativ);
            threadNegativ.setDaemon(true);
            threadNegativ.start();
            piNegativ.progressProperty().bind(taskNegativ.progressProperty());
            labelFileNegativBot.textProperty().bind(taskNegativ.messageProperty());
        }

        threadCommon = new Thread(taskCommon);
        threadCommon.setDaemon(true);
        threadCommon.start();
        pbCommonDownload.progressProperty().bind(taskCommon.progressProperty());
    }


    private void updateIndex(){
        indexCommon = indexPositiv + indexNegativ + 2;
    }

    private void updateLabelPositivTop(String text){
        Platform.runLater(() -> {
            labelFilePositivTop.setText(text);
        });
    }
    private void updateLabelNegativTop(String text){
        Platform.runLater(() -> {
            labelFileNegativTop.setText(text);
        });
    }


    private void endDownload() {
        Platform.runLater(() -> {
            main.showDeleteTemproraryFile("Удаление временных файлов" ,false);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Информационное");
            alert.setHeaderText("Загрузка в базу данных успешна завершена!");
            alert.showAndWait();
            dialogStage.close();
        });
    }
    private synchronized boolean comparePreposInArray(String value){

        boolean control = true;

        for(int indexPrepos = 0; indexPrepos < arrayPrepos.size(); indexPrepos++ ){
            if(arrayPrepos.get(indexPrepos).compareToIgnoreCase(value) == 0){
                control = false;
                break;
            }
        }
        return control;
    }

}
