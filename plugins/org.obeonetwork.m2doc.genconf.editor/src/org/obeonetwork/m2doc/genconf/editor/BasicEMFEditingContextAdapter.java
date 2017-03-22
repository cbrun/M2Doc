package org.obeonetwork.m2doc.genconf.editor;

import java.util.Collection;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.eef.core.api.EditingContextAdapter;
import org.eclipse.eef.core.api.LockStatusChangeEvent;
import org.eclipse.eef.core.api.LockStatusChangeEvent.LockStatus;
import org.eclipse.eef.core.api.controllers.IConsumer;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.ChangeCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

public class BasicEMFEditingContextAdapter implements EditingContextAdapter {

	private EditingDomain domain;

	public BasicEMFEditingContextAdapter(EditingDomain domain) {
		this.domain = domain;
	}

	@Override
	public IStatus performModelChange(final Runnable operation) {
		domain.getCommandStack().execute(new ChangeCommand(domain.getResourceSet()) {

			@Override
			protected void doExecute() {
				operation.run();
			}
		});
		return Status.OK_STATUS;
	}

	@Override
	public void registerModelChangeListener(IConsumer<List<Notification>> listener) {
		System.out.println("SimpleEditingContextAdapter.registerModelChangeListener()");
	}

	@Override
	public void unregisterModelChangeListener() {
		System.out.println("SimpleEditingContextAdapter.unregisterModelChangeListener()");

	}

	@Override
	public EditingDomain getEditingDomain() {
		return domain;
	}

	@Override
	public void addLockStatusChangedListener(IConsumer<Collection<LockStatusChangeEvent>> listener) {

	}

	@Override
	public void removeLockStatusChangedListener(IConsumer<Collection<LockStatusChangeEvent>> listener) {

	}

	@Override
	public LockStatus getLockStatus(EObject obj) {
		return LockStatus.UNLOCKED;
	}

}
