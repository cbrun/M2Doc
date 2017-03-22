package org.obeonetwork.m2doc.genconf.editor;

import org.eclipse.eef.properties.ui.api.EEFTabbedPropertySheetPage;
import org.eclipse.eef.properties.ui.api.IEEFTabbedPropertySheetPageContributor;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.provider.AdapterFactoryItemDelegator;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.obeonetwork.m2doc.genconf.Generation;
import org.obeonetwork.m2doc.genconf.presentation.GenconfEditor;

public class GenconfCustomEditor extends GenconfEditor {

	private static final String contributorId = "org.obeonetwork.m2doc.genconf.presentation.GenconfEditorID";
	private IEEFTabbedPropertySheetPageContributor contributor;
	private EEFTabbedPropertySheetPage propPage;

	public GenconfCustomEditor() {
		this.contributor = new IEEFTabbedPropertySheetPageContributor() {

			@Override
			public String getContributorId() {
				return contributorId;
			}

			@Override
			public void updateFormTitle(org.eclipse.ui.forms.widgets.Form form, ISelection selection) {

				form.setText("");
				form.setImage(null);

				if (selection instanceof IStructuredSelection) {
					final IStructuredSelection structuredSelection = (IStructuredSelection) selection;
					if (structuredSelection.getFirstElement() instanceof Notifier) {

						final Notifier eObject = (Notifier) structuredSelection.getFirstElement();
						AdapterFactoryItemDelegator delegator = new AdapterFactoryItemDelegator(adapterFactory);
						form.setText(delegator.getText(eObject));
						form.setImage(ExtendedImageRegistry.INSTANCE.getImage(delegator.getImage(eObject)));
					}
				}

			}

		};
		propPage = new EEFTabbedPropertySheetPage(contributor);
	}

	@Override
	public void init(final IEditorSite site, IEditorInput editorInput) {
		super.init(site, editorInput);
		propPage.init(new EditorSiteToPageSite(site));
	}

	@Override
	public void createPages() {
		super.createPages();
		propPage.createControl(getContainer());
		addPage(0, propPage.getControl());
		setPageText(0, "PropertiesSettings");
		setInputForForm();

	}

	private void setInputForForm() {
		Generation root = null;
		for (Resource resource : this.editingDomain.getResourceSet().getResources()) {
			for (EObject obj : resource.getContents()) {
				if (obj instanceof Generation) {
					root = (Generation) obj;
				}
			}
		}
		if (root != null) {
			propPage.selectionChanged(this, new StructuredSelection(root));
		}
	}

	private EEFTabbedPropertySheetPage eefpage;

	public Object getAdapter(@SuppressWarnings("rawtypes") Class type) {
		if (type == IPropertySheetPage.class) {
			if (this.eefpage == null) {
				this.eefpage = new EEFTabbedPropertySheetPage(contributor);
			}
			return this.eefpage;
		}

		return super.getAdapter(type);
	}

	@Override
	protected void handleChangedResources() {
		super.handleChangedResources();
		setInputForForm();
	}

}
