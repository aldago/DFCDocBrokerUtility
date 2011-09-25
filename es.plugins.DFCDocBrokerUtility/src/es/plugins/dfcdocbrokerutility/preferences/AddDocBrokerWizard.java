package es.plugins.dfcdocbrokerutility.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import es.plugins.dfcdocbrokerutility.Activator;
import es.plugins.dfcdocbrokerutility.utils.Constants;


public class AddDocBrokerWizard extends Wizard implements INewWizard{
	AddDocBrokerEntryPage addPage;
	
	private String listItems="";
	
	protected IStructuredSelection selection;
	protected IWorkbench workbench;

	public AddDocBrokerWizard(){
		super();
	}
	
	public void addPages(){
		addPage = new AddDocBrokerEntryPage(workbench, selection);
		addPage(addPage);
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.workbench = workbench;
		this.selection = selection;
	}
	
	public void init(IWorkbench workbench, IStructuredSelection selection, String listItems) {
		this.workbench = workbench;
		this.selection = selection;
		this.listItems = listItems;
	}

	public boolean canFinish(){
		return addPage.getHostText().length()>0;
	}
	
	public boolean performFinish(){
		if (addPage.getHostText().length()>0){
			String docbroker="";
			if (addPage.getPortText().length()>0){
				docbroker=addPage.getHostText()+Constants.docbrokerToken+addPage.getPortText();
			}else{
				docbroker=addPage.getHostText()+Constants.docbrokerToken+Constants.defaultPort;
			}
			String globalRegistry=null;
			if (addPage.getGlobalRegUsernameText().length()>0 && addPage.getGlobalRegUsernameText().length()>0 && addPage.getGlobalRegPasswordText().length()>0){
				globalRegistry=addPage.getGlobalRegRepositoryText()+Constants.docbrokerToken+addPage.getGlobalRegUsernameText()+Constants.docbrokerToken+addPage.getGlobalRegPasswordText();
			}
			IPreferenceStore store=Activator.getDefault().getPreferenceStore();
			if (globalRegistry!=null){
				store.setValue(Constants.P_DOCBROKERS,listItems+Constants.docbrokerListToken+docbroker+Constants.docbrokerToken+globalRegistry);
			}else{
				store.setValue(Constants.P_DOCBROKERS,listItems+Constants.docbrokerListToken+docbroker);
			}
		}
		return true;
	}
}
