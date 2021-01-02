package my;

public class Student
{
	int id;			//学号
	String name;	//姓名
	boolean sex;	//性别
	String cellphone; //手机号
	@Override
	public String toString()
	{	
		String s= "学号: "+id+" 姓名: "+name+" 性别: ";
		if(sex==true) {
		s+="男";
	}
		else s+="女";
		s+=" 手机号:"+cellphone;
		return s;
	}
	
}	
