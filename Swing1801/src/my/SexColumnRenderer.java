package my;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;

public class SexColumnRenderer extends JLabel implements TableCellRenderer
{
	public SexColumnRenderer()
	{
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setFont(this.getFont().deriveFont(Font.PLAIN));
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column)
	{
		Boolean sex = (Boolean)value;
		if(sex != null && sex == true)
			this.setText("男");
		else
			this.setText("女");
		
		// 背景设置
		this.setOpaque(true);
		
	    if (isSelected) {
	    	this.setBackground(table.getSelectionBackground());
	    	this.setForeground(table.getSelectionForeground());
	    	
        } else {
        	this.setBackground(table.getBackground());
        	this.setForeground(table.getForeground());
        }
	
		return this;
	}
	
}
