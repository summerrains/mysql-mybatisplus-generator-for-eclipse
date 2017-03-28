package net.summerrains.plugins.gui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.wb.swt.SWTResourceManager;

import net.summerrains.plugins.DefaultConfigGenerator;
import net.summerrains.plugins.utils.Utils;

public class TempleteDialog extends BaseDialog {
	
	private DefaultConfigGenerator configGenerator;
	
	private StyledText entityStyledText;
	private StyledText daoStyledText;
	private StyledText mapperStyledText;
	private StyledText serviceStyledText;
	private StyledText serviceImplStyledText;
	private StyledText baseEntityStyledText;
	private StyledText repositoryStyledText;

	public TempleteDialog(Shell parentShell,DefaultConfigGenerator configGenerator) {
		super(parentShell);
		setShellStyle(SWT.MAX);
		this.configGenerator = configGenerator;
	}
	
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		super.createButton(parent, IDialogConstants.RETRY_ID, "Reset", false);
		super.createButton(parent, IDialogConstants.BACK_ID, "Back", false);
		super.createButton(parent, IDialogConstants.NEXT_ID, "Start", true);
		super.createButton(parent, IDialogConstants.CANCEL_ID, "Cancel", false);
	}
	
	@Override
	protected void buttonPressed(int buttonId) {
		if(IDialogConstants.RETRY_ID == buttonId) {
			entityStyledText.setText(configGenerator.getEntityTemplete());
			daoStyledText.setText(configGenerator.getDaoTemplete());
			mapperStyledText.setText(configGenerator.getMapperTemplete());
			serviceStyledText.setText(configGenerator.getServiceTemplete());
			serviceImplStyledText.setText(configGenerator.getServiceImplTemplete());
			baseEntityStyledText.setText(configGenerator.getBaseEntityTemplete());
			repositoryStyledText.setText(configGenerator.getRepositoryTemplete());
			MessageDialog.openInformation(
					getShell(),TITLE,
					"Reset Success");
		} else if(IDialogConstants.BACK_ID == buttonId) {
			getShell().dispose();
			new TableDialog(getShell(),configGenerator).open();
		} else if(IDialogConstants.NEXT_ID == buttonId) {
			if(getDefaultTemplate()) {
				Utils.writeDefaultTemplete(configGenerator);
				getShell().dispose();
				new GeneratorProgressMonitorDialog(getShell(),configGenerator).open();
			}
		}
		super.buttonPressed(buttonId);
	}
	private boolean getDefaultTemplate() {
		String entityText = entityStyledText.getText();
		String daoText = daoStyledText.getText();
		String mapperText = mapperStyledText.getText();
		String serviceText = serviceStyledText.getText();
		String serviceImplText = serviceImplStyledText.getText();
		String baseEntityText = baseEntityStyledText.getText();
		String repositoryText = repositoryStyledText.getText();
		if(!isCheckPass(entityText, false, false, "Entity templete can not null")) {
			return false;
		}
		if(!isCheckPass(daoText, false, false, "Dao templete can not null")) {
			return false;
		}
		if(!isCheckPass(mapperText, false, false, "Mapper templete can not null")) {
			return false;
		}
		if(!isCheckPass(serviceText, false, false, "Service templete can not null")) {
			return false;
		}
		if(!isCheckPass(serviceImplText, false, false, "ServiceImpl templete can not null")) {
			return false;
		}
		if(!isCheckPass(baseEntityText, false, false, "BaseEntity templete can not null")) {
			return false;
		}
		if(!isCheckPass(repositoryText, false, false, "Repository templete can not null")) {
			return false;
		}
		configGenerator.setEntityTemplete(entityText);
		configGenerator.setDaoTemplete(daoText);
		configGenerator.setMapperTemplete(mapperText);
		configGenerator.setServiceTemplete(serviceText);
		configGenerator.setServiceImplTemplete(serviceImplText);
		configGenerator.setBaseEntityTemplete(baseEntityText);
		configGenerator.setRepositoryTemplete(repositoryText);
		return true;
	}
	
	@Override
	protected Control createDialogArea(Composite parent) {
		parent.setFont(SWTResourceManager.getFont("微软雅黑", 9, SWT.NORMAL));
		getShell().setText(TITLE);
		
		this.setTitle(TITLE);
		this.setMessage("you can edit the templete");
		
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new BorderLayout());
		Composite composite = new Composite(container, SWT.NONE);
		composite.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));
		composite.setLayout(new BorderLayout());
		
		TabFolder tabFolder = new TabFolder(composite, SWT.NONE);
		tabFolder.setLayoutData(new BorderLayout.BorderData(BorderLayout.CENTER));
		
		configGenerator = Utils.readDefaultTemplete(configGenerator);
		
		TabItem entityItem = new TabItem(tabFolder, SWT.NONE);
		entityItem.setText("Entity");
		entityStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		entityStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		entityStyledText.setText(configGenerator.getEntityTemplete());
		entityItem.setControl(entityStyledText);
		
		TabItem daoItem = new TabItem(tabFolder, SWT.NONE);
		daoItem.setText("Dao");
		daoStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		daoStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		daoStyledText.setText(configGenerator.getDaoTemplete());
		daoItem.setControl(daoStyledText);
		
		TabItem mapperItem = new TabItem(tabFolder, SWT.NONE);
		mapperItem.setText("Mapper");
		mapperStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		mapperStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		mapperStyledText.setText(configGenerator.getMapperTemplete());
		mapperItem.setControl(mapperStyledText);
		
		TabItem serviceItem = new TabItem(tabFolder, SWT.NONE);
		serviceItem.setText("Service");
		serviceStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		serviceStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		serviceStyledText.setText(configGenerator.getServiceTemplete());
		serviceItem.setControl(serviceStyledText);
		
		TabItem serviceImplItem = new TabItem(tabFolder, SWT.NONE);
		serviceImplItem.setText("ServiceImpl");
		serviceImplStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		serviceImplStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		serviceImplStyledText.setText(configGenerator.getServiceImplTemplete());
		serviceImplItem.setControl(serviceImplStyledText);
		
		TabItem baseEntityItem = new TabItem(tabFolder, SWT.NONE);
		baseEntityItem.setText("BaseEntity");
		baseEntityStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		baseEntityStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		baseEntityStyledText.setText(configGenerator.getBaseEntityTemplete());
		baseEntityItem.setControl(baseEntityStyledText);
		
		TabItem repositoryItem = new TabItem(tabFolder, SWT.NONE);
		repositoryItem.setText("Repository");
		repositoryStyledText = new StyledText(tabFolder, SWT.H_SCROLL | SWT.V_SCROLL);
		repositoryStyledText.setFont(SWTResourceManager.getFont("微软雅黑", 12, SWT.NORMAL));
		repositoryStyledText.setText(configGenerator.getRepositoryTemplete());
		repositoryItem.setControl(repositoryStyledText);
		 
		return container;
	}
	

}
