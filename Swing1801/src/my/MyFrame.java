package my;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.json.JSONArray;
import org.json.JSONObject;

import af.common.json.AfJSON;

public class MyFrame extends JFrame
{
	JPanel root = new JPanel();
	JTable table = null;
	
	// dataList: 维护所有记录  , tableModel: 要显示出来的记录
	List<Student> dataList = new ArrayList<>(); 
	DefaultTableModel tableModel = new DefaultTableModel();
	
	
	JButton addButton,deleteButton,editButton;
	JTextField searchField = new JTextField();
	
	public MyFrame(String title)
	{
		super(title);

		// Content Pane		
		this.setContentPane(root);
		root.setLayout(new BorderLayout());
		
		// 表格初始化
		initTable();
		
		// 初始化工具栏
		initToolBar();
		
		// 加载文件
		loadData();
		
		
	}
	
	private void initTable()
	{
		// 创建 JTable，直接重写 isCellEditable()，设为不可编辑
		table = new JTable(tableModel){
			@Override
			public boolean isCellEditable(int row, int column)
			{
				return false;
			}			
		};
		JScrollPane scrollPane = new JScrollPane(table);
		root.add(scrollPane, BorderLayout.CENTER);
		
		// 添加到主界面		
		table.setFillsViewportHeight(true);		
		table.setRowSelectionAllowed(true); // 整行选择
		table.setRowHeight(30);	
		
		// 列设置：添加5列
		tableModel.addColumn ("学号");
		tableModel.addColumn ("姓名");
		tableModel.addColumn ("性别");
		tableModel.addColumn ("出生日期");
		tableModel.addColumn ("手机号");
		
		// 列设置：自定义绘制
		table.getColumnModel().getColumn(2).setCellRenderer(new SexColumnRenderer());
		table.getColumnModel().getColumn(0).setCellRenderer(new IDColumnRender());
		table.getColumnModel().getColumn(0).setPreferredWidth(110); // 该列的宽度		
	}
	
	private void initToolBar()
	{
		JToolBar toolBar = new JToolBar();
		root.add(toolBar, BorderLayout.PAGE_START);
		toolBar.setFloatable(false);
		
		// 按钮
		addButton = createToolButton("添加", "ic_add.png" );
		toolBar.add(addButton);
		addButton.addActionListener( (e)->{
			onAdd();
		});
		
		// 按钮
		deleteButton = createToolButton("删除", "ic_delete.png" );
		toolBar.add(deleteButton);
		deleteButton.addActionListener( (e)->{
			onDelete();
		});
		
		// 按钮
		editButton = createToolButton("编辑", "ic_edit.png" );
		toolBar.add(editButton);
		editButton.addActionListener( (e)->{
			onEdit();
		});		
		
		// 查询
		toolBar.addSeparator(new Dimension(40,10));
		toolBar.add( new JLabel("查询") );
		toolBar.add( searchField );
		searchField.setMaximumSize(new Dimension(120,30));
		searchField.addActionListener( (e)->{
			// 按回车时触发事件
			onSearch();
		});
	}
	
	protected JButton createToolButton(String text, String icon)
	{
		// 图标
		String imagePath = "/icons/" + icon;
		URL imageURL = getClass().getResource(imagePath);

		// 创建按钮
		JButton button = new JButton(text);
		//button.setActionCommand(action);
		button.setToolTipText(text);
		button.setIcon(new ImageIcon(imageURL));
		button.setFocusPainted(false);
		return button;
	}
	
	private void addTableRow(Student item)
	{
		// java.util.Vector 是个范型 ，表示数组
		Vector<Object> rowData = new Vector<>();
		rowData.add(item.id);
		rowData.add(item.name);
		rowData.add(item.sex);
		rowData.add(item.birthday);
		rowData.add(item.cellphone);		
		tableModel.addRow( rowData ); // 添加一行		
	}
	
	// 获取 表格控件中的一条记录的值
	private Student getTableRow(int row)
	{
		Student s = new Student();
		s.id = (String) tableModel.getValueAt(row, 0);
		s.name = (String) tableModel.getValueAt(row, 1);
		s.sex = (Boolean) tableModel.getValueAt(row, 2);
		s.birthday = (String) tableModel.getValueAt(row, 3);
		s.cellphone = (String) tableModel.getValueAt(row, 4);		
		return s;
	}
	// 设置 表格控件中的一条记录的值
	private void setTableRow(Student v, int row)
	{
		tableModel.setValueAt(v.id, row, 0);
		tableModel.setValueAt(v.name, row, 1);
		tableModel.setValueAt(v.sex, row, 2);
		tableModel.setValueAt(v.birthday, row, 3);		
		tableModel.setValueAt(v.cellphone, row, 4);
	}
	
	// 向dataList添加一条记录
	private void addToDataList(Student s)
	{
		dataList.add(s);
	}
	// 修改一条记录
	private void updateToDataList(String id, Student s)
	{
		for(int i=0;i<dataList.size();i++)
		{
			Student item = dataList.get(i);
			if(item.id.equals(id))
			{
				dataList.set(i, s);
			}			
		}
	}	
	// 从dataList中删除一条记录
	private void removeFromDataList(String id)
	{
		Iterator<Student> iter = dataList.iterator();
		while(iter.hasNext())
		{
			Student s = iter.next();
			if(s.id.equals( id))
			{
				iter.remove();
				break;
			}
		}
	}
	
	// 点'添加' 按钮
	private void onAdd()
	{
		EditStudentDialog dlg = new EditStudentDialog(this);
		if( dlg.exec() )
		{
			Student stu = dlg.getValue();
			
			addToDataList (stu); // 添加到 dataList
			addTableRow( stu);	// 添加到 tableModel		
			saveData(); // 保存到文件
		}
	}
	
	// 点 '删除' 按钮
	private void onDelete()
	{
		// 获取选中的行的索引
		int[] rows = table.getSelectedRows();
		if(rows.length == 0)
		{
			System.out.println("没有选中，所以不作删除!");
			return;
		}
				
		// 弹出对话框确认
		int select = JOptionPane.showConfirmDialog(this, "是否确认删除?", "确认", JOptionPane.YES_NO_OPTION);
		if(select != 0) return; // 0号按钮是'确定'按钮

		// 技巧：从后往前删除
		for(int i= rows.length-1; i>=0; i--)
		{
			int row = rows[i];
			
			// 按学号，从dataList中删除该条记录
			String id = (String)tableModel.getValueAt(row, 0);
			removeFromDataList(id);
			
			// 从tableModel中删除该条记录
			tableModel.removeRow( row);
			

		}
		
		saveData(); // 保存到文件
	}
	
	// 点 '编辑' 按钮
	private void onEdit()
	{
		// 获取选中的行的索引
		int[] rows = table.getSelectedRows();
		if(rows.length == 0)return;
		
		// 取得选中的行
		int row = rows[0]; // 只编辑选中的第一行
		Student s = getTableRow( row );
		
		// 弹出编辑对话框
		EditStudentDialog dlg = new EditStudentDialog(this);
		// 设置初始值
		dlg.setValue( s );
		if( dlg.exec() )
		{
			Student stu = dlg.getValue();
			
			// 更新到 Model
			setTableRow (stu, row );
			// 更新到dataList
			updateToDataList(stu.id, stu);
			
			saveData(); // 保存到文件
		}		
	}
	
	private void onSearch()
	{
		// 获取用户输入的过滤条件
		String filter = searchField.getText().trim();
		
		if(filter.length() == 0) // 过滤条件为空
		{
			// 恢复原始数据
			tableModel.setRowCount(0);// 清空
			for(Student s : dataList)
			{
				addTableRow(s);
			}		
			this.addButton.setEnabled(true);		
			return;
		}
			
		// 把符合条件的记录显示在表格里
		tableModel.setRowCount(0);//清空
		for(Student s : dataList)
		{
			if(s.name.indexOf(filter)>=0)
			{
				addTableRow(s);
			}
		}
		
		// 把其他操作按钮禁用
		this.addButton.setEnabled(false);
		
		
	}
	
	// 保存数据
	private void saveData()
	{
		// 构造一个 JSON 数组
		JSONArray array = new JSONArray();
		for(int i=0; i<dataList.size(); i++)
		{
			Student s = dataList.get(i);
			JSONObject j1 = new JSONObject();
			j1.put("id", s.id);
			j1.put("name", s.name);
			j1.put("sex", s.sex);
			j1.put("birthday", s.birthday);
			j1.put("cellphone", s.cellphone);
			
			array.put( j1 );
		}
		
		// 将JSON对象保存到文件
		File file = new File("students.json");
		try
		{
			AfJSON.toFile(array, file, "UTF-8");
			System.out.println("保存数据至: " + file.getAbsolutePath());
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void loadData()
	{
		// 加载数据
		File file = new File("students.json");
		if(!file.exists())
		{
			System.out.println("数据文件尚不存在 :" + file.getAbsolutePath());
			return;
		}
		
		JSONArray array = null;
		try
		{
			array = (JSONArray) AfJSON.fromFile(file, "UTF-8");
		} catch (Exception e)
		{
			JOptionPane.showMessageDialog(this, e.getMessage());
			e.printStackTrace();
			return;
		}
		// 显示到表格
		dataList.clear();
		tableModel.setRowCount(0); // 清空
		for(int i=0; i<array.length(); i++)
		{
			JSONObject j1 = array.getJSONObject(i);
			Student s = new Student();
			s.id = j1.getString("id");
			s.name = j1.getString("name");
			s.sex = j1.getBoolean("sex");
			s.cellphone = j1.getString("cellphone");
			s.birthday = j1.getString("birthday");
			
			addToDataList( s);
			addTableRow( s);			
		}
	}
	
	
}
