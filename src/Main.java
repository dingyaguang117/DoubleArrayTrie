import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.xml.crypto.Data;


public class Main {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		//String[] words = new String[]{"bachelor","jar","badge","baby"};
		//String[] words = new String[]{"badge","baby","bachelor","jar"};
		//String[] words = new String[]{"a","b","c","d","ab"};
		//String[] words = new String[]{"abc","a","a","ab"};
		//String[] words = new String[]{"a","a","a"};
		ArrayList<String> words = new ArrayList<String>();
		//BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:/Users/ding/Downloads/result.txt")));
		BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("E:/兔子的试验学习中心[课内]/ACM大赛/ACM第四届校赛/E命令提示/words3.dic")));
		String s;
		int num = 0;
		while((s=reader.readLine()) != null)
		{
			words.add(s);
			num ++;
		}
		DoubleArrayTrie dat = new DoubleArrayTrie();
		
		for(String word: words)
		{
			dat.Insert(word);
			
			//System.out.println(word);
			//dat.Debug(26);
		}
		
		System.out.println(dat.Base.length);
		System.out.println(dat.Tail.length);
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext())
		{
			String word = sc.next();
			//System.out.println(word.length());
			
			//int r = dat.Find(word);
			//System.out.printf("index=%d\n",r);
			System.out.println(dat.Exists(word));
			System.out.println(dat.FindAllWords(word));
			//System.out.printf("base[%d]=%d\n",r,dat.Base[r]);
			//System.out.printf("check[%d]=%d\n",r,dat.Check[r]);
		}
		
	}

}
