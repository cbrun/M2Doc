package org.obeonetwork.m2doc.genconf.editor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.eef.EEFViewDescription;
import org.eclipse.eef.core.api.EEFExpressionUtils;
import org.eclipse.eef.core.api.EEFPage;
import org.eclipse.eef.core.api.EEFView;
import org.eclipse.eef.core.api.EEFViewFactory;
import org.eclipse.eef.core.api.InputDescriptor;
import org.eclipse.eef.ide.ui.properties.api.EEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptor;
import org.eclipse.eef.properties.ui.api.IEEFTabDescriptorProvider;
import org.eclipse.eef.properties.ui.api.IEEFTabbedPropertySheetPageContributor;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EStringToStringMapEntryImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sirius.common.interpreter.api.IVariableManager;
import org.eclipse.sirius.common.interpreter.api.VariableManagerFactory;
import org.eclipse.sirius.common.interpreter.aql.AQLInterpreter;
import org.eclipse.sirius.ext.emf.edit.EditingDomainServices;
import org.eclipse.ui.IWorkbenchPart;
import org.obeonetwork.m2doc.genconf.GenconfPackage;

public class EEFTabDescriptorProvider1 implements IEEFTabDescriptorProvider {

	private IQueryEnvironment queryEnvironment;

	public EEFTabDescriptorProvider1() {
		this.queryEnvironment = Query.newEnvironmentWithDefaultServices(null);
		this.queryEnvironment.registerEPackage(EcorePackage.eINSTANCE);
		this.queryEnvironment.registerEPackage(GenconfPackage.eINSTANCE);
		this.queryEnvironment.registerCustomClassMapping(EcorePackage.eINSTANCE.getEStringToStringMapEntry(),
				EStringToStringMapEntryImpl.class);
		ServiceUtils.registerServices(queryEnvironment,
				ServiceUtils.getServices(queryEnvironment, EditingDomainServices.class));
	}

	@Override
	public Collection<IEEFTabDescriptor> get(IWorkbenchPart part, ISelection selection,
			IEEFTabbedPropertySheetPageContributor contributor) {
		if (selection instanceof IStructuredSelection && part instanceof IEditingDomainProvider) {
			EditingDomain domain = ((IEditingDomainProvider) part).getEditingDomain();
			IStructuredSelection structuredSelection = (IStructuredSelection) selection;
			Object[] objects = structuredSelection.toArray();

			InputDescriptor descriptor = new SelectionInputDescriptor((IStructuredSelection) selection);
			// We will first retrieve the description of the user interface
			EEFViewDescription viewDescription = this.getViewDescription(descriptor);
			IVariableManager variableManager = new VariableManagerFactory().createVariableManager();
			variableManager.put(EEFExpressionUtils.SELF, descriptor.getSemanticElement());

			// See the documentation regarding the interpreter and the editing
			// context adapter
			EEFView eefView = new EEFViewFactory().createEEFView(viewDescription, variableManager,
					new AQLInterpreter(queryEnvironment), new BasicEMFEditingContextAdapter(domain), descriptor);

			List<IEEFTabDescriptor> descriptors = new ArrayList<IEEFTabDescriptor>();
			List<EEFPage> eefPages = eefView.getPages();
			for (EEFPage eefPage : eefPages) {
				descriptors.add(new EEFTabDescriptor(eefPage));
			}
			return descriptors;
		}
		return new ArrayList<IEEFTabDescriptor>();
	}

	private EEFViewDescription getViewDescription(InputDescriptor object) {
		// Programmatically create the description of the view or load it from
		// an EMF model

		XMIResource r = new XMIResourceImpl();
		try {
			r.load(this.getClass().getResourceAsStream("properties.xmi"), Collections.EMPTY_MAP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (EEFViewDescription) r.getContents().iterator().next();
		// EEFViewDescription r =
		// EefFactory.eINSTANCE.createEEFViewDescription();
		// EEFPageDescription p =
		// EefFactory.eINSTANCE.createEEFPageDescription();
		// p.setLabelExpression("'Hello World, selection: ' +
		// self.eClass().name");
		//
		// r.getPages().add(p);
		//
		// return r;
	}

}
