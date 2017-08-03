package MorfSdkJ;


import java.io.*;

public class MorfSdk  {
	private native void Start(String msg1,String msg2,String msg3);
	private native int GrafAnalys(String textStrForAnalisys);
	private native int GrafAnalysFile(String Filename);
	private native int NormolWordByNom (int i,int i1);
	private native int NormolWord (String word);
	
	private native String DivWord (String word);
	private native int GetColSents();
	
	private native String GetWord(int i);
	private native int GetID(int i );
	private native String GetMainForm(int i);
	private native int GetType(int i);
	private native String GetSentText(int i);

	private native int GetRod(int i);
	private native int GetChislo(int i);
	private native int GetPadezh(int i);
	private native int GetOdush(int i);
	private native int GetPril(int i);
	private native int GetVrem(int i);
	private native int GetGlag(int i);
	private native int GetLico(int i);
	private native int GetDeist(int i);
	private native int GetVid(int i);
	private native int GetPereh(int i);
	private native int GetProch(int i);
	private native int GetProch2(int i);
	private native int Uninitialize();
	private native int sum(int i,int i2);
	
	static {
            System.loadLibrary("MorfSdkToJava");
	}
  
	public void StartJ(String msg1,String msg2,String msg3) {
		Start(msg1,msg2, msg3);
	}
 	public int GrafAnalysJ(String textStrForAnalisys) {
		return GrafAnalys(textStrForAnalisys);
	}
 	public int GrafAnalysFileJ(String Filename) {
		return GrafAnalysFile(Filename);
	}
 	public int NormolWordByNomJ(int i,int i1) {
		return NormolWordByNom(i,i1);
	}
 	public int NormolWordJ(String word) {
            int k = NormolWord(word); 
            return k;
	}
	
 	public String DivWordJ(String word) {
		return DivWord(word);
	}
 	public int GetColSentsJ() {
		return GetColSents();
	}
	
 	public String GetWordJ(int i) {
		return GetWord(i);
	}
 	public int GetIDJ(int i) {
		return GetID (i) ;
	}
 	public String GetMainFormJ(int i) {
		return GetMainForm(i);
	}
 	public int GetTypeJ(int i) {
		return GetType(i);
	}
 	public String GetSentTextJ(int i) {
		return GetSentText(i);
	}

 	public int GetRodJ(int i) {
		return GetRod(i);
	}
 	public int GetChisloJ(int i) {
		return GetChislo(i);
	}
 	public int GetPadezhJ(int i) {
		return GetPadezh(i);
	}
 	public int GetOdushJ(int i) {
		return GetOdush(i);
	}
 	public int GetPrilJ(int i) {
		return GetPril(i);
	}
 	public int GetVremJ(int i) {
		return GetVrem(i);
	}
 	public int GetGlagJ(int i) {
		return GetGlag(i);
	}
 	public int GetLicoJ(int i) {
		return GetLico(i);
	}
 	public int GetDeistJ(int i) {
		return GetDeist(i);
	}
 	public int GetVidJ(int i) {
		return GetVid(i);
	}
 	public int GetPerehJ(int i) {
		return GetPereh(i);
	}
 	public int GetProchJ(int i) {
		return GetProch(i);
	}
 	public int GetProch2J(int i) {
		return GetProch2(i);
	}

 	public int UninitializeJ() {
		return Uninitialize();
	}

 	public static String decode(byte[] input){
		char[] output=new char[input.length/2];
		for (int i=1;i<input.length;i+=2)
			output[i/2]= (char) ('а'+input[i]+96);
		String retVal = String.valueOf(output);
		return retVal;
	}
}