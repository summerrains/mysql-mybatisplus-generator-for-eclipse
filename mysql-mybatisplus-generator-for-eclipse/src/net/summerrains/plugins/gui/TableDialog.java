package net.summerrains.plugins.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.wb.swt.SWTResourceManager;

import net.summerrains.generator.AutoGenerator;
import net.summerrains.generator.AutoGenerator.GeneratorListener;
import net.summerrains.plugins.DefaultConfigGenerator;

public class TableDialog extends BaseDialog {
	
	
	private DefaultConfigGenerator configGenerator;
	private Table table;
	private Map<String,Button> checkButtonMap;
	
	public TableDialog(Shell parentShell,DefaultConfigGenerator configGenerator) {
		super(parentShell);
		this.configGenerator = configGenerator;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButton(parent, IDialogConstants.SELECT_ALL_ID, "Select All", false);
		super.createButton(parent, IDialogConstants.DESELECT_ALL_ID, "Unselect All", false);
		super.createButton(parent, IDialogConstants.BACK_ID, "Back", false);
		super.createButton(parent, IDialogConstants.NEXT_ID, "Next", true);
		super.createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if(IDialogConstants.DESELECT_ALL_ID == buttonId) {
			if(checkButtonMap != null) {
				for (Map.Entry<String,Button> entry : checkButtonMap.entrySet()) {
					if(entry.getValue().getSelection()) {
						entry.getValue().setSelection(false);
					}
				}
			}
		} else if(IDialogConstants.SELECT_ALL_ID == buttonId) {
			if(checkButtonMap != null) {
				for (Map.Entry<String,Button> entry : checkButtonMap.entrySet()) {
					if(!entry.getValue().getSelection()) {
						entry.getValue().setSelection(true);
					}
				}
			}
		} else if(IDialogConstants.BACK_ID == buttonId) {
			getShell().dispose();
			new ConfigDialog(getShell()).open();
		} else if(IDialogConstants.NEXT_ID == buttonId) {
			String[] tables = getSelectedTables();
			if(tables == null || tables.length <= 0) {
				MessageDialog.openError(getShell(),TITLE,"must be select one table to generator");
			} else {
				configGenerator.setTables(tables);
				getShell().dispose();
				new TempleteDialog(getShell(),configGenerator).open();
			}
		}
		super.buttonPressed(buttonId);
	}
	
	private String[] getSelectedTables() {
		if(checkButtonMap != null) {
			List<String> selectTableList = new ArrayList<String>();
			for (Map.Entry<String,Button> entry : checkButtonMap.entrySet()) {
				if(entry.getValue().getSelection()) {
					selectTableList.add(entry.getKey());
				}
			}
			if(!selectTableList.isEmpty()) {
				return selectTableList.toArray(new String[selectTableList.size()]);
			}
		}
		return null;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		getShell().setText(TITLE);
		
		Composite container = (Composite) super.createDialogArea(parent);
		 
		this.setTitle(TITLE);
		this.setMessage("select the tables you want to generate");
		
		Composite composite = new Composite(container, SWT.NONE);
		 
		RowLayout rl_container = new RowLayout();
		rl_container.marginTop = 10;
		rl_container.marginRight = 10;
		rl_container.marginLeft = 10;
		rl_container.marginHeight = 10;
		rl_container.marginBottom = 10;
		composite.setLayout(rl_container);
		
		
		table = new Table(composite, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new RowData(520, 324));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tblclmnNewColumn = new TableColumn(table, SWT.CENTER);
		tblclmnNewColumn.setResizable(false);
		tblclmnNewColumn.setWidth(22);
		
		TableColumn tblclmnNewColumn_2 = new TableColumn(table, SWT.LEFT);
		tblclmnNewColumn_2.setWidth(131);
		tblclmnNewColumn_2.setText("Table Name");
		
		TableColumn tblclmnNewColumn_1 = new TableColumn(table, SWT.LEFT);
		tblclmnNewColumn_1.setWidth(352);
		tblclmnNewColumn_1.setText("Table Comments");
		
		configGenerator.setGeneratorListener(new GeneratorListener() {
			@Override
			public void onSuccess() {
			}
			@Override
			public void onProcess(String arg0, String arg1) {
			}
			@Override
			public void onError(String message, Throwable throwable) {
				MessageDialog.openError(getShell(),TITLE,message);
			}
		});
		
		AutoGenerator generator = new AutoGenerator(configGenerator);
		if(generator.testConnection()) {
			List<String> tableList = generator.getTables();
			Map<String,String> tableCommentMap = generator.getTablesComment();
			if(tableList != null && tableList.size() > 0) {
				checkButtonMap = new HashMap<String,Button>();
				for (String tableName : tableList) {
					TableItem tableItem = new TableItem(table, SWT.NONE);
					Button checkButton = new Button(table, SWT.CHECK);
					checkButton.setSelection(false);
					TableEditor editor = new TableEditor(table);
					editor.grabHorizontal = true; 
					editor.setEditor(checkButton, tableItem, 0);
					tableItem.setText(1, tableName);
					tableItem.setText(2, tableCommentMap.get(tableName));
					checkButtonMap.put(tableName, checkButton);
				}
			}
		}
		
		return container;
	}
	
}
