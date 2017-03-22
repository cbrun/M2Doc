package org.obeonetwork.m2doc.genconf.editor;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.part.IPageSite;

public class EditorSiteToPageSite implements IPageSite {

	private IEditorSite site;

	public EditorSiteToPageSite(IEditorSite site) {
		this.site = site;
	}

	@Override
	public boolean hasService(Class<?> api) {
		return site.hasService(api);
	}

	@Override
	public <T> T getService(Class<T> api) {
		return site.getService(api);
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return site.getAdapter(adapter);
	}

	@Override
	public void setSelectionProvider(ISelectionProvider provider) {
		site.setSelectionProvider(provider);
	}

	@Override
	public IWorkbenchWindow getWorkbenchWindow() {
		return site.getWorkbenchWindow();
	}

	@Override
	public Shell getShell() {
		return site.getShell();
	}

	@Override
	public ISelectionProvider getSelectionProvider() {
		return site.getSelectionProvider();
	}

	@Override
	public IWorkbenchPage getPage() {
		return site.getPage();
	}

	@Override
	public void registerContextMenu(String menuId, MenuManager menuManager, ISelectionProvider selectionProvider) {
		site.registerContextMenu(menuId, menuManager, selectionProvider);

	}

	@Override
	public IActionBars getActionBars() {
		return site.getActionBars();
	}
}
