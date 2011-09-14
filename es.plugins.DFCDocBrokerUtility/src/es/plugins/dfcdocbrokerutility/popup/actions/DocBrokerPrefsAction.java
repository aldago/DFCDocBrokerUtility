package es.plugins.dfcdocbrokerutility.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.PreferencesUtil;

import es.plugins.dfcdocbrokerutility.utils.Constants;

public class DocBrokerPrefsAction implements IObjectActionDelegate {

	@SuppressWarnings("unused")
	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public DocBrokerPrefsAction() {
		super();
	}

	/**
	 * @see IObjectActionDelegate#setActivePart(IAction, IWorkbenchPart)
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		shell = targetPart.getSite().getShell();
	}

	/**
	 * @see IActionDelegate#run(IAction)
	 */
	public void run(IAction action) {
		PreferenceDialog prefd=PreferencesUtil.createPreferenceDialogOn(null, Constants.preferencesPage, null, null);
		prefd.open();
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
