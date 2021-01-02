package my;

import java.util.ArrayList;

public class StudentDB
{
	public ArrayList list=new ArrayList();
	
	//添加一条记录
	public void add(Student s) {
		list.add(s);
	}
	public void delete(Student s) {
		list.remove(s);
	}
	//按学号删除一条记录
	public void remove(int id) {
		for(int i=0;i<list.size();i++) {
			Student s=(Student)list.get(i);
			if(s.id==id) {
				list.remove(i);
				break;
			}
		}
	}
	public ArrayList find(String name) {
		ArrayList result=new ArrayList();
		
		for(int i=0;i<list.size();i++) {
			Student s=(Student)list.get(i);
			if(s.name.indexOf(name)>=0) {//indexOf()用来判断是否有这个子串
				result.add(s);
			}
		}
		return result;
	}
	public ArrayList find(int id) {
		ArrayList result=new ArrayList();
		
		for(int i=0;i<list.size();i++) {
			Student s=(Student)list.get(i);
			if(s.id==id) {
				result.add(s);
			}
		}
		return result;
	}
}
