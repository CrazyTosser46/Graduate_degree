package sample.POJO;

/**
 * Created by Robert on 15.03.2017.
 */
public class DataTable {
    private int id;
    private String name;
    private int sizePositiv;
    private int sizeNegativ;
    private String useMorf;

    public DataTable(int id, String name, String useMorf, int sizePositiv, int sizeNegativ){
        this.id = id;
        this.name = name;
        this.useMorf = useMorf;
        this.sizePositiv = sizePositiv;
        this.sizeNegativ = sizeNegativ;
    }
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public int getSizePositiv(){
        return sizePositiv;
    }
    public void setSizePositiv(int sizePositiv){
        this.sizePositiv = sizePositiv;
    }
    public int getSizeNegativ(){
        return sizeNegativ;
    }
    public void setSizeNegativ(int sizeNegativ){
        this.sizeNegativ = sizeNegativ;
    }
    public String getUseMorf(){
        return useMorf;
    }
    public void setUseMorf(String useMorf){
        this.useMorf = useMorf;
    }
}
