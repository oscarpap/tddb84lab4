package net.sf.freecol.common.model;

import java.util.List;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public class AdapterBuildingType implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {

		BuildingType newBuildingType = (BuildingType) buildableType;
	
		
		Building colonyBuilding = colony.getBuilding((BuildingType) buildableType);
		if (colonyBuilding == null) {
			// the colony has no similar building yet
			BuildingType from = newBuildingType.getUpgradesFrom();
			if (from != null && !assumeBuilt.contains(from)) {
				// we are trying to build an advanced factory, we
				// should build lower level shop first
				return NoBuildReason.WRONG_UPGRADE;
			}
		} else {
			// a building of the same family already exists
			BuildingType from = colonyBuilding.getType().getUpgradesTo();
			if (from != newBuildingType && !assumeBuilt.contains(from)) {
				// the existing building's next upgrade is not the
				// new one we want to build
				return NoBuildReason.WRONG_UPGRADE;
			}
		}

		return NoBuildReason.NONE;

	}

}
