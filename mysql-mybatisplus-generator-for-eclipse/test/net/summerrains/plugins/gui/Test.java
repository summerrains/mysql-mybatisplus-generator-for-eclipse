package net.summerrains.plugins.gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.summerrains.plugins.utils.Utils;

public class Test {

	public static void main(String[] args) {
		Shell shell = new Shell();
		TableDialog td = new TableDialog(shell,Utils.readDefaultConfigGenerator());
        td.setBlockOnOpen(true);
        td.open();
        Display.getCurrent().dispose();
	}
	
}
