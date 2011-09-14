package es.plugins.dfcdocbrokerutility.popup.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;

import es.plugins.dfcdocbrokerutility.utils.DFCDocBrokerUtils;
import es.plugins.dfcdocbrokerutility.utils.Constants;

public class ChangeDocBrokerAction implements IObjectActionDelegate {

	private Shell shell;
	
	/**
	 * Constructor for Action1.
	 */
	public ChangeDocBrokerAction() {
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
		String vals=DFCDocBrokerUtils.getProps();
		
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, new LabelProvider());
		dialog.setTitle(Constants.elementListTitle);
		dialog.setMessage(Constants.elementListMsg);
		dialog.setElements(vals.split(Constants.docbrokerListToken));
		int closed=dialog.open();
		
		if (closed==0){
			DFCDocBrokerUtils.writeDFCprops((String)dialog.getResult()[0]);
		}
	}

	/**
	 * @see IActionDelegate#selectionChanged(IAction, ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
