package net.sf.freecol.common.model;

import java.util.List;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public class SimpleHandlerType implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType type, List<BuildableType> assumeBuilt, Colony colony) {
		
		if (type == null) {
			return NoBuildReason.NOT_BUILDING;
		} 
		else if (!type.needsGoodsToBuild()) {
			return NoBuildReason.NOT_BUILDABLE;
		}
		else {
			return null;
		}
	}

	public boolean canHandle(BuildableType buildableType) {

		if (buildableType == null) {
			return true;
		} else if (!buildableType.needsGoodsToBuild()) {
			return true;
		} else {
			return false;
		}
	}
}
