package net.summerrains.plugins.gui;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import net.summerrains.generator.AutoGenerator;
import net.summerrains.generator.ConfigGenerator;
import net.summerrains.plugins.utils.Utils;

public class GeneratorProgressMonitorDialog {
	
	private Shell shell;
	private ConfigGenerator configGenerator;
	private int totalTask;
	
	private static final String TITLE = "MySQL MybatisPlus Generator for Eclipse";

	public GeneratorProgressMonitorDialog(Shell parent,ConfigGenerator configGenerator) {
		this.shell = parent;
		this.configGenerator = configGenerator;
		if(this.configGenerator.getTables() == null) {
			this.totalTask = 10000;
		} else {
			this.totalTask = this.configGenerator.getTables().length * 5 + 2;
		}
	}
	
	public void open() {
		IRunnableWithProgress runnable = new IRunnableWithProgress() {
			@Override
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				configGenerator.setGeneratorListener(new AutoGenerator.GeneratorListener() {
					@Override
					public void onSuccess() {
						monitor.done();
						MessageDialog.openInformation(
								shell,TITLE,
								"Generator Success");
						shell.dispose();
					}
					@Override
					public void onProcess(String entity, String filePath) {
						monitor.setTaskName("generator "+entity+" to "+filePath);
						monitor.worked(1);
					}
					@Override
					public void onError(String message, Throwable throwable) {
						monitor.setCanceled(true);
						Utils.showMessageBox(shell, message + "\r\n" +Utils.throwableToString(throwable), SWT.ICON_ERROR);
						shell.dispose();
					}
				});
				monitor.beginTask("start generator", totalTask);
				monitor.worked(0);
				AutoGenerator.run(configGenerator);
			}
		};
		try {
			new ProgressMonitorDialog(shell).run(false, false, runnable);
		} catch (InvocationTargetException e) {
			MessageDialog.openError(shell,TITLE,e.getMessage());
		} catch (InterruptedException e) {
			MessageDialog.openError(shell,TITLE,e.getMessage());
		}
	}

}
