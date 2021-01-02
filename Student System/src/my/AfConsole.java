package my;

import java.io.BufferedReader;
import java.io.InputStreamReader;
public class AfConsole
{	
	BufferedReader reader;
	
	public AfConsole() {
		InputStreamReader m=new InputStreamReader(System.in);
		reader=new BufferedReader(m);
	}
	
	//输出显示一个字符串
	public void print(String s) {
		System.out.print(s);
	}
	//输出显示一个字符串(并换行)
	public void println(String s) {
		System.out.println(s);
	}
	
	//从控制台读取用户输入:读取一个字符串
	//		如果用户直接按了回车,则返回默认值
	public String readString(String defValue)
	{
		try {
			return reader.readLine();
		}catch(Exception e)
		{
			return defValue;
		}
	}
	
	//控制台读取用户输入:读取一个整数
	//		如果用户直接按了回车,则返回默认值
	public int readInt(int defValue)
	{
		try {
			String s=readString(null);
			return Integer.valueOf(s);
			}catch(Exception e)
		{
			return defValue;
		}
	}
}
