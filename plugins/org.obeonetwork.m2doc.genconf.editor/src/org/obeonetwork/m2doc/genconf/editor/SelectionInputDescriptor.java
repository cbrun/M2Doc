package org.obeonetwork.m2doc.genconf.editor;

import org.eclipse.core.runtime.Platform;
import org.eclipse.eef.core.api.InputDescriptor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.IStructuredSelection;

public class SelectionInputDescriptor implements InputDescriptor {

	private IStructuredSelection selection;

	public SelectionInputDescriptor(IStructuredSelection selection) {
		this.selection = selection;
	}

	@Override
	public Object getOriginalSelection() {
		return selection.getFirstElement();
	}

	@Override
	public EObject getSemanticElement() {
		return Platform.getAdapterManager().getAdapter(selection.getFirstElement(), EObject.class);
	}

}
