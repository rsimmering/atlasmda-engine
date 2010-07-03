package org.atlas.mda.adapter;

import java.io.File;

import org.atlas.metamodel.Model;

/**
 * Responsible for reading in a source model and normalizing it to the Atlas metamodel
 *
 * @author andrews
 */
public interface Adapter {

    public Model adapt(File file, Model model) throws AdapterException;
}
