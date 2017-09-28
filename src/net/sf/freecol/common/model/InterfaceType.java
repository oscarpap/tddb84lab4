package net.sf.freecol.common.model;

import java.util.List;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public interface InterfaceType {
	
	public NoBuildReason getNoBuildReason(BuildableType type, List<BuildableType> assumeBuilt, Colony colony);
}
