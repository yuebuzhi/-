package my;

import java.util.ArrayList;

public class Start
{
	public void start() {
		AfConsole cons=new AfConsole();
		StudentDB db=new StudentDB();
		cons.println("**学生管理系统**");
		
		while(true) {
			//命令行提示符
			cons.print("\n>");
			String cmd=cons.readString("");
			cmd=cmd.trim();
			if(cmd.equals("exit")) {
				System.out.println("退出...");
				break;
				}
			else if(cmd.equals("add")) {
				Student stu=getStudent(cons);
				System.out.println("\n添加了:"+stu);
				
				db.add(stu);
				System.out.println("共"+db.list.size()+"条记录");
			}
			else if(cmd.equals("find")) {
				cons.print("请输入要查找的对象姓名:");
				String name =cons.readString("");
				name =name.trim();
				
				ArrayList result=db.find(name);
				System.out.println("匹配到"+result.size()+"条记录");
				for(int i=0;i<db.list.size();i++) {
					Student stu=(Student) result.get(i);
					System.out.println(stu);
						
					}
				}
			else if(cmd.equals("IDfind")) {
				cons.print("请输入要查找的对象学号:");
				int id =cons.readInt(0);
				ArrayList result=db.find(id);
				System.out.println("匹配到"+result.size()+"条记录");
				for(int i=0;i<db.list.size();i++) {
					Student stu=(Student) result.get(i);
					System.out.println(stu);
						
					}
				}
				
			
			else if(cmd.equals("remove")) {
				cons.print("输入删除的学生学号:");
				int id=cons.readInt(0);
				if(id>0) {
					db.remove(id);
					System.out.println("已删除学生:ID="+id);
				}
					
				
			}
			else if(cmd.equals("show"))
			{
				System.out.println("共"+db.list.size()+"条记录");
				for(int i=0;i<db.list.size();i++) {
					Student stu=(Student)db.list.get(i);
					System.out.println(stu);
				}
			}
			else {
				System.out.println("错误的命令!");
			}
			
		}
	}
	//输入一个学生的信息,返回学生对象
	public Student getStudent(AfConsole cons)
	{
		Student stu=new Student();
		cons.print("学号:");
		stu.id=cons.readInt(0);
		
		cons.print("姓名:");
		stu.name=cons.readString("");
		
		cons.print("性别(1/0):");
		int nValue=cons.readInt(1);
		stu.sex=nValue>0;
		
		cons.print("手机号:");
		stu.cellphone=cons.readString("");
		
		return stu;
	}
	
	public static void main(String[] args) {
		Start world =new Start();
		AfConsole cons=new AfConsole();
		world.start();
		
		System.out.println("Exit");
	}
}
