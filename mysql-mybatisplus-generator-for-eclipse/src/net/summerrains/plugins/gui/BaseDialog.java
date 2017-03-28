package net.summerrains.plugins.gui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BaseDialog extends TitleAreaDialog {
	
	protected static final String TITLE = "MySQL MybatisPlus Generator for Eclipse";

	public BaseDialog(Shell parentShell) {
		super(parentShell);
	}
	
	@Override
	protected Point getInitialLocation(Point initialSize) {
		Rectangle displayBounds = Display.getCurrent().getPrimaryMonitor() 
                .getBounds(); 
        int x = displayBounds.x + (displayBounds.width - initialSize.x) >> 1; 
        int y = displayBounds.y + (displayBounds.height - initialSize.y) >> 1; 
        return new Point(x, y);  
	}
	
	protected boolean isCheckPass(String value,boolean number,boolean isNull,String message) {
		if(isNull) {
			return true;
		} else {
			if(value == null || value.length() == 0) {
				MessageDialog.openError(getShell(),TITLE,message);
				return false;
			}
			if(number) {
				try {
					Integer.valueOf(value);
				} catch(NumberFormatException e) {
					MessageDialog.openError(getShell(),TITLE,message);
					return false;
				}
			}
			return true;
		}
	}

}
