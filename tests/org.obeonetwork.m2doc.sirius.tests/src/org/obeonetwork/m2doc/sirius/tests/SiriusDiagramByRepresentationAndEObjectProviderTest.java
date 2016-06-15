/*******************************************************************************
 *  Copyright (c) 2016 Obeo. 
 *  All rights reserved. This program and the accompanying materials
 *  are made available under the terms of the Eclipse Public License v1.0
 *  which accompanies this distribution, and is available at
 *  http://www.eclipse.org/legal/epl-v10.html
 *   
 *   Contributors:
 *       Obeo - initial API and implementation
 *  
 *******************************************************************************/
package org.obeonetwork.m2doc.sirius.tests;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.AssertionFailedError;

import org.junit.Before;
import org.junit.Test;
import org.obeonetwork.m2doc.provider.ProviderConstants;
import org.obeonetwork.m2doc.provider.ProviderException;
import org.obeonetwork.m2doc.sirius.SiriusDiagramByRepresentationAndEObjectProvider;
import org.obeonetwork.m2doc.ui.genconf.Generation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * {@link SiriusDiagramByRepresentationAndEObjectProvider} test class.
 * 
 * @author pguilet<pierre.guilet@obeo.fr>
 */
public class SiriusDiagramByRepresentationAndEObjectProviderTest extends AbstractM2DocSiriusTest {

    /**
     * Component to test.
     */
    private SiriusDiagramByRepresentationAndEObjectProvider siriusDiagramProvider;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();
        siriusDiagramProvider = new SiriusDiagramByRepresentationAndEObjectProvider();
    }

    /**
     * Tests
     * {@link SiriusDiagramByRepresentationAndEObjectProvider#getRepresentationImagePath(Map)}
     * . When all options given are correct, an image from the diagram
     * corresponding to the title must be created. The tested tag is {m:diagram
     * diagramProvider:'org.obeonetwork.m2doc.sirius.
     * SiriusDiagramByRepresentationAndEObjectProvider' width:'200' height:'200'
     * rootObject:'db.schemas->first()' diagramDescriptionName:'Schema Diagram'}
     *
     * @throws ProviderException
     *             if a problem occurs.
     */
    @Test
    public void testAllOptionPresentAndCorrect() throws ProviderException {
        Map<String, Object> options = new HashMap<String, Object>();
        Generation generation = (Generation) getSemanticResource().getContents().get(0);
        options.put(ProviderConstants.KEY_CONF_ROOT_OBJECT, generation);
        options.put(ProviderConstants.KEY_PROJECT_ROOT_PATH, "org.obeonetwork.m2doc.sirius.tests");
        // CHECKSTYLE:OFF
        options.put(ProviderConstants.KEY_IMAGE_HEIGHT, 500);
        options.put(ProviderConstants.KEY_IMAGE_WIDTH, 500);
        options.put("diagramDescriptionName", "diagram");
        options.put("rootObject", generation);
        // CHECKSTYLE:ON
        List<String> representationImagePaths = siriusDiagramProvider.getRepresentationImagePath(options);
        assertEquals(2, representationImagePaths.size());
        File imageFile = new File(representationImagePaths.get(0));
        assertTrue(imageFile.exists());
        imageFile = new File(representationImagePaths.get(1));
        assertTrue(imageFile.exists());

    }

    /**
     * Tests
     * {@link SiriusDiagramByRepresentationAndEObjectProvider#getRepresentationImagePath(Map)}
     * . The root object is not provided. An exception should be thrown.
     */
    @Test
    public void testNoRootObject() {
        Map<String, Object> options = new HashMap<String, Object>();
        Generation generation = (Generation) getSemanticResource().getContents().get(0);
        options.put(ProviderConstants.KEY_CONF_ROOT_OBJECT, generation);
        options.put(ProviderConstants.KEY_PROJECT_ROOT_PATH, "org.obeonetwork.m2doc.sirius.tests");
        // CHECKSTYLE:OFF
        options.put(ProviderConstants.KEY_IMAGE_HEIGHT, 500);
        options.put(ProviderConstants.KEY_IMAGE_WIDTH, 500);
        // CHECKSTYLE:ON
        options.put("diagramDescriptionName", "diagram");
        try {
            siriusDiagramProvider.getRepresentationImagePath(options);
            throw new AssertionFailedError("An exception should have been thrown");
        } catch (ProviderException e) {
            assertEquals(
                    "Image cannot be computed because no root EObject has been provided to the provider \"org.obeonetwork.m2doc.sirius.SiriusDiagramByRepresentationAndEObjectProvider\"",
                    e.getMessage());
        }
    }

    /**
     * Tests
     * {@link SiriusDiagramByRepresentationAndEObjectProvider#getRepresentationImagePath(Map)}
     * . The diagram description does not exist in the ODesign. An exception
     * should be thrown.
     */
    @Test
    public void testDiagramDescriptionInvalid() {
        Map<String, Object> options = new HashMap<String, Object>();
        Generation generation = (Generation) getSemanticResource().getContents().get(0);
        options.put(ProviderConstants.KEY_CONF_ROOT_OBJECT, generation);
        options.put(ProviderConstants.KEY_PROJECT_ROOT_PATH, "org.obeonetwork.m2doc.sirius.tests");
        // CHECKSTYLE:OFF
        options.put(ProviderConstants.KEY_IMAGE_HEIGHT, 500);
        options.put(ProviderConstants.KEY_IMAGE_WIDTH, 500);
        // CHECKSTYLE:ON
        options.put("diagramDescriptionName", "wrongDiagramDescription");
        options.put("rootObject", generation);
        try {
            siriusDiagramProvider.getRepresentationImagePath(options);
            throw new AssertionFailedError("An exception should have been thrown");
        } catch (ProviderException e) {
            assertEquals("The provided diagram description 'wrongDiagramDescription' does not exist in the loaded aird",
                    e.getMessage());
        }
    }

    /**
     * Tests
     * {@link SiriusDiagramByRepresentationAndEObjectProvider#getRepresentationImagePath(Map)}
     * . The diagram description is not provided. An exception should be thrown.
     */
    @Test
    public void testDiagramDescriptionNotPresent() {
        Map<String, Object> options = new HashMap<String, Object>();
        Generation generation = (Generation) getSemanticResource().getContents().get(0);
        options.put(ProviderConstants.KEY_CONF_ROOT_OBJECT, generation);
        options.put(ProviderConstants.KEY_PROJECT_ROOT_PATH, "org.obeonetwork.m2doc.sirius.tests");
        // CHECKSTYLE:OFF
        options.put(ProviderConstants.KEY_IMAGE_HEIGHT, 500);
        options.put(ProviderConstants.KEY_IMAGE_WIDTH, 500);
        // CHECKSTYLE:ON
        options.put("rootObject", generation);
        try {
            siriusDiagramProvider.getRepresentationImagePath(options);
            throw new AssertionFailedError("An exception should have been thrown");
        } catch (ProviderException e) {
            assertEquals(
                    "Image cannot be computed because no diagram description name has been provided to the provider \"org.obeonetwork.m2doc.sirius.SiriusDiagramByRepresentationAndEObjectProvider\"",
                    e.getMessage());
        }
    }

    /**
     * Tests
     * {@link SiriusDiagramByRepresentationAndEObjectProvider#getRepresentationImagePath(Map)}
     * . The root object has no diagram pointing on it with the given
     * description. An empty list should be provided.
     *
     * @throws ProviderException
     */
    @Test
    public void testNoResult() throws ProviderException {
        Map<String, Object> options = new HashMap<String, Object>();
        Generation generation = (Generation) getSemanticResource().getContents().get(0);
        options.put(ProviderConstants.KEY_CONF_ROOT_OBJECT, generation);
        options.put(ProviderConstants.KEY_PROJECT_ROOT_PATH, "org.obeonetwork.m2doc.sirius.tests");
        // CHECKSTYLE:OFF
        options.put(ProviderConstants.KEY_IMAGE_HEIGHT, 500);
        options.put(ProviderConstants.KEY_IMAGE_WIDTH, 500);
        // CHECKSTYLE:ON
        options.put("diagramDescriptionName", "diagram");
        options.put("rootObject", generation.getDefinitions().get(0));
        List<String> representationImagePaths = siriusDiagramProvider.getRepresentationImagePath(options);
        assertEquals(0, representationImagePaths.size());
    }

    @Override
    protected String getAirdPluginPath() {
        return "/org.obeonetwork.m2doc.sirius.tests/resources/representations.aird";
    }

}
