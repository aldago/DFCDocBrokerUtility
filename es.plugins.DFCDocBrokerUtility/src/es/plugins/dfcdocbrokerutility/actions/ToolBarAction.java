package es.plugins.dfcdocbrokerutility.actions;

import java.util.Iterator;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;
import org.eclipse.jface.dialogs.MessageDialog;

import es.plugins.dfcdocbrokerutility.popup.actions.ChangeDocBrokerAction;
import es.plugins.dfcdocbrokerutility.utils.Constants;
import es.plugins.dfcdocbrokerutility.utils.DFCDocBrokerUtils;

/**
 * Our sample action implements workbench action delegate.
 * The action proxy will be created by the workbench and
 * shown in the UI. When the user tries to use the action,
 * this delegate will be created and execution will be 
 * delegated to it.
 * @see IWorkbenchWindowActionDelegate
 */
public class ToolBarAction implements IWorkbenchWindowPulldownDelegate {
	private IWorkbenchWindow window;
	/**
	 * The constructor.
	 */
	public ToolBarAction() {
	}

	/**
	 * The action has been activated. The argument of the
	 * method represents the 'real' action sitting
	 * in the workbench UI.
	 * @see IWorkbenchWindowActionDelegate#run
	 */
	public void run(IAction action) {

	}

	/**
	 * Selection in the workbench has been changed. We 
	 * can change the state of the 'real' action here
	 * if we want, but this can only happen after 
	 * the delegate has been created.
	 * @see IWorkbenchWindowActionDelegate#selectionChanged
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

	/**
	 * We can use this method to dispose of any system
	 * resources we previously allocated.
	 * @see IWorkbenchWindowActionDelegate#dispose
	 */
	public void dispose() {
	}

	/**
	 * We will cache window object in order to
	 * be able to provide parent shell for the message dialog.
	 * @see IWorkbenchWindowActionDelegate#init
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	private MenuManager menuManager=null;
	
	private boolean checkMenu(){		
		if (menuManager==null) return false;
		
		if (DFCDocBrokerUtils.readDFCprops().contains(menuManager.getMenu().getItem(0).getText())){
			String vals=DFCDocBrokerUtils.getProps();
			
			if (vals!=null){
				if (vals.split(Constants.docbrokerListToken).length==menuManager.getSize())
					return true;
			}
		}
		return false;
	}
	
	public Menu getMenu(Control control) {
	      Menu fMenu = null;
	      String[] lblParam=null;
	      
	      if (!checkMenu()) {
	         menuManager = new MenuManager();

	         fMenu = menuManager.createContextMenu(control);
	         menuManager.removeAll();

	         String selectedHost=DFCDocBrokerUtils.readDFCprops();
	         final String paramHost=selectedHost;
	         lblParam=paramHost.split(Constants.docbrokerToken);
	         final String lblName=lblParam[0]+Constants.docbrokerToken+lblParam[1];
	         
	         menuManager.add(new Action(lblName){	        	 
	        	 public void run(){
	        		 DFCDocBrokerUtils.writeDFCprops(paramHost);
	        		 DFCDocBrokerUtils.writeStatusBarMsg(Constants.statusBarHostPrefix+lblName);
	        	 }
	         });
	         menuManager.add(new Separator());
	         
	         String vals=DFCDocBrokerUtils.getProps();
	         String[] items=vals.split(Constants.docbrokerListToken);
	         
	         for (int i=0;i<items.length;i++){
	            final String name = items[i];
	            lblParam=name.split(Constants.docbrokerToken);
	            final String lblNamePrefs=lblParam[0]+Constants.docbrokerToken+lblParam[1];
	            
	            menuManager.add(new Action(lblNamePrefs){
		        	 public void run(){
		        		 DFCDocBrokerUtils.writeDFCprops(name);
		        		 DFCDocBrokerUtils.writeStatusBarMsg(Constants.statusBarHostPrefix+lblNamePrefs);
		        	 }
	            });
	         }
	      }
	      else
	      {
	         fMenu = menuManager.getMenu();
	      }
	      return fMenu; 
	}
	
}