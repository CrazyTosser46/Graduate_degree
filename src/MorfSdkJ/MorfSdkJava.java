package MorfSdkJ;


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MorfSdkJava {
	
    static int counter = 0;
    private CustomClassLoader cl;
    Class ca;
    Object o;
    Method m;
	
    private void restartSdk() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        m = null;
        ca = null;
        o = null;
        cl = null;
        System.gc();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cl = new CustomClassLoader();
        ca = cl.findClass("MorfSdkJ.MorfSdk");
        o = ca.newInstance();
        counter = 0;
    }
    private void startSdk() {
        try {
            cl = new CustomClassLoader();
            ca = cl.findClass("MorfSdkJ.MorfSdk");
            o = ca.newInstance();
            counter = 0;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void incrementUsageCounter()  {
        if (++counter > 1400) {
            try {
                restartSdk();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public MorfSdkJava() {
        MorfSdkJava.counter = 0;
        startSdk();
    }
    @Override
    protected void finalize() throws Throwable{
        m = null;
        ca = null;
        o = null;
        cl = null;
    }
    
    public void StartJ(String msg1,String msg2,String msg3) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("StartJ", String.class, String.class, String.class);
            m.invoke(o, msg1, msg2, msg3); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public int GrafAnalysJ(String textStrForAnalisys) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GrafAnalysJ", String.class);
            return (Integer)m.invoke(o, textStrForAnalisys); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public int GrafAnalysFileJ(String filename) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GrafAnalysFileJ", String.class);
            return (Integer)m.invoke(o, filename); 
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
        
    public int NormolWordByNomJ(int wordNumber,int numberOfForm) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("NormolWordByNomJ",int.class,int.class);
            int k = (Integer)m.invoke(o, wordNumber, numberOfForm); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
      
    public int NormolWordJ(String word) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("NormolWordJ",String.class);
            int k = (Integer)m.invoke(o,word); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public String DivWordJ(String word) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("DivWordJ",String.class);
            return (String)m.invoke(o,word);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetColSentsJ() {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetColSentsJ");
            int k = (Integer)m.invoke(o); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public String GetWordJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetWordJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetIDJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetIDJ", int.class);
            int k = (Integer)m.invoke(o, i); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public String GetMainFormJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetMainFormJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    public int GetTypeJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetTypeJ", int.class);
            int k = (Integer)m.invoke(o, i); 
            return k;
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    public String GetSentTextJ(int i) {
        try {
            incrementUsageCounter();
            m = ca.getMethod("GetSentTextJ",int.class);
            return (String)m.invoke(o,i);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    private int runIntToInt (String funcName, int val) {
        try {
            incrementUsageCounter();
            m = ca.getMethod(funcName,int.class);
            return (int)m.invoke(o,val);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(MorfSdkJava.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;    
    }
            

    public int GetRodJ(int i) {
        return runIntToInt ("GetRodJ", i);
    }
    public int GetChisloJ(int i) {
        return runIntToInt ("GetChisloJ", i);
    }
    public int GetPadezhJ(int i) {
        return runIntToInt ("GetPadezhJ", i);
    }
    public int GetOdushJ(int i) {
        return runIntToInt ("GetOdushJ", i);
    }
    public int GetPrilJ(int i) {
        return runIntToInt ("GetPrilJ", i);
    }
    public int GetVremJ(int i) {
        return runIntToInt ("GetVremJ", i);
    }
    public int GetGlagJ(int i) {
        return runIntToInt ("GetGlagJ", i);
    }
    public int GetLicoJ(int i) {
        return runIntToInt ("GetLicoJ", i);
    }
    public int GetDeistJ(int i) {
        return runIntToInt ("GetDeistJ", i);
    }
    public int GetVidJ(int i) {
        return runIntToInt ("GetVidJ", i);
    }
    public int GetPerehJ(int i) {
        return runIntToInt ("GetPerehJ", i);
    }
    public int GetProchJ(int i) {
        return runIntToInt ("GetProchJ", i);
    }
    public int GetProch2J(int i) {
        return runIntToInt ("GetProch2J", i);
    }

}