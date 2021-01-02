package my;


import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import af.swing.layout.AfYLayout;


public class MyFrame extends JFrame
{

	public MyFrame(String title)
	{
		super(title);
		
		// 创建一个Box，并设置为顶层容器 （取代原有的顶层容器)
		JPanel root = new JPanel();
		this.setContentPane(root);
		root.setLayout(new AfYLayout(4));
		
		 //也可以给  JPanel 设置一个边框
		Border padding=BorderFactory.createEmptyBorder(8,8,8,8);
		Border border=BorderFactory.createLineBorder(Color.BLACK);
		
		border=BorderFactory.createCompoundBorder(border,padding);
		root.setBorder(border);
		
		// 简单线条边框
		JLabel a1 = new ColorfulLabel("1", new Color(0xfcfcfc));
		root.add(a1, "60px");
		a1.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.RAISED) );	
		
		// 特种边框 (4条边分别可设置)
		JLabel a2 = new ColorfulLabel("2", new Color(0xfcfcfc));
		root.add(a2, "60px");
		a2.setBorder( BorderFactory.createMatteBorder(1, 5, 1, 1, Color.red) );	
		
		// 带标题的边框
		JLabel a3 = new ColorfulLabel("3", new Color(0xfcfcfc));
		root.add(a3, "60px");
		a3.setBorder( BorderFactory.createTitledBorder("title") );	
	
		// 复合边框
		JLabel a4 = new ColorfulLabel("4", new Color(0xfcfcfc));
		root.add(a4, "60px");
		Border outer = BorderFactory.createLineBorder(Color.RED, 4);
		Border inner = BorderFactory.createLineBorder(Color.BLUE, 4);
		Border compound = BorderFactory.createCompoundBorder(outer, inner);
		a4.setBorder(compound);
	}
	
	private static class ColorfulLabel extends JLabel
	{
		public ColorfulLabel(String text, Color bgColor)
		{
			super(text);
			
			setOpaque(true);
			setBackground(bgColor);
			//setPreferredSize(new Dimension(60,30));
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(new Font("宋体", Font.PLAIN, 16));
		}
	}	
	
	
}
