package net.summerrains.plugins.gui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;

import net.summerrains.generator.AutoGenerator;
import net.summerrains.generator.AutoGenerator.GeneratorListener;
import net.summerrains.generator.ConfigGenerator;
import net.summerrains.plugins.DefaultConfigGenerator;
import net.summerrains.plugins.utils.Utils;

public class ConfigDialog extends BaseDialog {
	
	private Text host;
	private Text port;
	private Text database;
	private Text users;
	private Text password;
	private Text saveDir;
	private Text packageName;
	private Combo tablePrefix;
	private Combo fieldPrefix;
	private Combo columnPrefix;
	
	private DefaultConfigGenerator configGenerator;
	
	public ConfigDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/**
	 * 移除按钮
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButton(parent, IDialogConstants.NO_ID, "Test Connect", false);
		super.createButton(parent, IDialogConstants.NEXT_ID, "Next", true);
		super.createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
	}
	
	private DefaultConfigGenerator testConnection() {
		DefaultConfigGenerator cg = getDefaultConfigGenerator();
		if(cg != null) {
			cg.setGeneratorListener(new GeneratorListener() {
				@Override
				public void onSuccess() { }
				@Override
				public void onProcess(String arg0, String arg1) { }
				@Override
				public void onError(String message, Throwable throwable) {
					MessageDialog.openError(getShell(),TITLE,message);
				}
			});
			AutoGenerator generator = new AutoGenerator(cg);
			if(generator.testConnection()) {
				return cg;
			}
		}
		return null;
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if(IDialogConstants.NO_ID == buttonId) {
			if(testConnection() != null) {
				MessageDialog.openInformation(
						getShell(),TITLE,
						"Test Connection Success");
			}
		} else if(IDialogConstants.NEXT_ID == buttonId) {
			configGenerator = testConnection();
			if(configGenerator != null) {
				Utils.writeDefaultConfigGenerator(configGenerator);
				getShell().dispose();
				new TableDialog(getShell(),configGenerator).open();
			}
		}
		super.buttonPressed(buttonId);
	}
	
	public ConfigGenerator getConfigGenerator() {
		if(configGenerator != null) {
			configGenerator.setGeneratorListener(null);
		}
		return configGenerator;
	}
	
	private DefaultConfigGenerator getDefaultConfigGenerator() {
		DefaultConfigGenerator cg = new DefaultConfigGenerator();
		cg.setDbDriverName("com.mysql.jdbc.Driver");
		String hostValue = host.getText();
		String portValue = port.getText();
		String dbValue = database.getText();
		String userValue = users.getText();
		String pwdValue = password.getText();
		String saveDirValue = saveDir.getText();
		String pkgValue = packageName.getText();
		if(!isCheckPass(hostValue, false, false, "database host can not null")) {
			return null;
		}
		cg.setHost(hostValue.trim());
		if(!isCheckPass(portValue, true, false, "database port can not null and must be number")) {
			return null;
		}
		cg.setPort(portValue.trim());
		if(!isCheckPass(dbValue, false, false, "database name can not null")) {
			return null;
		}
		cg.setDatabase(dbValue.trim());
		cg.setDbUrl("jdbc:mysql://"+hostValue.trim()+":"+portValue.trim()+"/"+dbValue.trim()+"?characterEncoding=utf8");
		if(!isCheckPass(userValue, false, false, "database user can not null")) {
			return null;
		}
		cg.setDbUser(userValue.trim());
		cg.setDbPassword(pwdValue == null ? "" : pwdValue.trim());
		if(!isCheckPass(saveDirValue, false, false, "save directory can not null")) {
			return null;
		}
		cg.setSaveDir(saveDirValue.trim());
		if(!isCheckPass(pkgValue, false, false, "package name can not null")) {
			return null;
		}
		cg.setPackageName(pkgValue.trim());
		cg.setTablePrefix(tablePrefix.getSelectionIndex() == 0 ? true : false);
		cg.setFieldPrefix(fieldPrefix.getSelectionIndex() == 0 ? true : false);
		cg.setColumnPrefix(columnPrefix.getSelectionIndex() == 0 ? true : false);
		return cg;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		
		DefaultConfigGenerator defConfig = Utils.readDefaultConfigGenerator();
		
		parent.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		getShell().setText(TITLE);
		
		 Composite container = (Composite) super.createDialogArea(parent);
		 
		 this.setTitle(TITLE);
		 this.setMessage("configure mybatis-plus generator information");
		 
		 Composite composite = new Composite(container, SWT.NONE);
		 GridData gd_composite = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		 gd_composite.widthHint = 526;
		 composite.setLayoutData(gd_composite);
		 GridLayout gl_composite = new GridLayout(2, false);
		 gl_composite.verticalSpacing = 10;
		 gl_composite.marginBottom = 10;
		 gl_composite.marginTop = 10;
		 gl_composite.marginRight = 10;
		 gl_composite.marginLeft = 10;
		 composite.setLayout(gl_composite);
		 
		 Label lblNewLabel = new Label(composite, SWT.NONE);
		 lblNewLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblNewLabel.setText("Database Host");
		 
		 host = new Text(composite, SWT.BORDER);
		 host.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 host.setText(defConfig.getHost());
		 
		 Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		 lblNewLabel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblNewLabel_1.setText("Database Port");
		 
		 port = new Text(composite, SWT.BORDER);
		 port.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 port.setText(defConfig.getPort());
		 
		 Label lblNewLabel_2 = new Label(composite, SWT.NONE);
		 lblNewLabel_2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblNewLabel_2.setText("Database Name");
		 
		 database = new Text(composite, SWT.BORDER);
		 database.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 database.setText(defConfig.getDatabase());
		 
		 Label lblUser = new Label(composite, SWT.NONE);
		 lblUser.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblUser.setText("Database User");
		 
		 users = new Text(composite, SWT.BORDER);
		 users.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 users.setText(defConfig.getDbUser());
		 
		 Label lblPassword = new Label(composite, SWT.NONE);
		 lblPassword.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblPassword.setText("Database Password");
		 
		 password = new Text(composite, SWT.BORDER);
		 password.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 if(defConfig.getDbPassword() != null) {
			 password.setText(defConfig.getDbPassword());
		 }
		 
		 Label lblSaveDirectory = new Label(composite, SWT.NONE);
		 lblSaveDirectory.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblSaveDirectory.setText("Save Directory");
		 
		 saveDir = new Text(composite, SWT.BORDER);
		 saveDir.setToolTipText("the directory of save generator file");
		 saveDir.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 saveDir.setText(defConfig.getSaveDir());
		 
		 Label lblTablePrefix = new Label(composite, SWT.NONE);
		 lblTablePrefix.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblTablePrefix.setText("Remove Table Prefix");
		 
		 tablePrefix = new Combo(composite, SWT.READ_ONLY);
		 tablePrefix.setToolTipText("choose true if your table name have prefix");
		 tablePrefix.setItems(new String[] {"true", "false"});
		 tablePrefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 tablePrefix.select(defConfig.isTablePrefix() ? 0 : 1);
		 
		 Label lblFieldPrefix = new Label(composite, SWT.NONE);
		 lblFieldPrefix.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblFieldPrefix.setText("Remove Field Prefix");
		 
		 fieldPrefix = new Combo(composite, SWT.READ_ONLY);
		 fieldPrefix.setToolTipText("choose true if your column name have prefix");
		 fieldPrefix.setItems(new String[] {"true", "false"});
		 fieldPrefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 fieldPrefix.select(defConfig.isFieldPrefix() ? 0 : 1);
		 
		 Label lblColumnPrefix = new Label(composite, SWT.NONE);
		 lblColumnPrefix.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblColumnPrefix.setText("Add Base Column Prefix");
		 
		 columnPrefix = new Combo(composite, SWT.READ_ONLY);
		 columnPrefix.setToolTipText("add table name as prefix each column in mapper.xml");
		 columnPrefix.setItems(new String[] {"true", "false"});
		 columnPrefix.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 columnPrefix.select(defConfig.isColumnPrefix() ? 0 : 1);
		 
		 Label lblPackageName = new Label(composite, SWT.NONE);
		 lblPackageName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		 lblPackageName.setText("Package Name");
		 
		 packageName = new Text(composite, SWT.BORDER);
		 packageName.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		 packageName.setText(defConfig.getPackageName());
		
	     return container;
	}
	
}
