package net.sf.freecol.common.model;

import static net.sf.freecol.common.util.CollectionUtils.all;
import static net.sf.freecol.common.util.CollectionUtils.none;

import java.util.ArrayList;
import java.util.List;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public class FactoryType {

	private static int counter;

	static List<InterfaceType> tupleList = new ArrayList<InterfaceType>() {};
	
	
	//Here you add new reasons for NoBuildReasons
	static {
		tupleList.add(new BuildableTypeNull());
		tupleList.add(new NotBuildable());
		tupleList.add(new ToSmallPopulation());
		tupleList.add(new IsCoastal());
		tupleList.add(new MissingAbility());
		tupleList.add(new LimitExceeded());
		tupleList.add(new WrongUpgrade());
		tupleList.add(new WrongUpgradeBuild());
		tupleList.add(new WrongBuildAbility());
	}

	public static NoBuildReason getNoBuildReason(BuildableType buildableType, Colony colony, List<BuildableType> assumeBuilt) {
		
		for (counter = 0; counter < tupleList.size(); counter++) {

			if (tupleList.get(counter).getNoBuildReason(buildableType, assumeBuilt, colony) != null) {

				return tupleList.get(counter).getNoBuildReason(buildableType, assumeBuilt, colony);
			}

			else if (counter >= tupleList.size() - 1) {
				return NoBuildReason.NONE;
			}
		}

		return NoBuildReason.NONE;
	}
}

//Anonymous classes, reasons for NoBuildReasons
class BuildableTypeNull implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (buildableType == null) {
			return NoBuildReason.NOT_BUILDING;
		} else {
			return null;
		}
	}
}

class NotBuildable implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (!buildableType.needsGoodsToBuild()) {
			return NoBuildReason.NOT_BUILDABLE;
		} else {
			return null;
		}
	}
}

class ToSmallPopulation implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (buildableType.getRequiredPopulation() > colony.getUnitCount()) {
			return NoBuildReason.POPULATION_TOO_SMALL;
		} else {
			return null;
		}
	}
}

class IsCoastal implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (buildableType.hasAbility(Ability.COASTAL_ONLY) && !colony.getTile().isCoastland()) {
			return NoBuildReason.COASTAL;
		} else {
			return null;
		}
	}
}

class MissingAbility implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (!all(buildableType.getRequiredAbilities().entrySet(), e -> e.getValue() == colony.hasAbility(e.getKey()))) {
			return NoBuildReason.MISSING_ABILITY;
		} else {
			return null;
		}
	}
}

class LimitExceeded implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (!all(buildableType.getLimits(), l -> l.evaluate(colony))) {
			return NoBuildReason.LIMIT_EXCEEDED;
		} else {
			return null;
		}
	}
}

class WrongUpgrade implements InterfaceType {

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
		}
		return null;
	}
}

class WrongUpgradeBuild implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		BuildingType newBuildingType = (BuildingType) buildableType;

		Building colonyBuilding = colony.getBuilding((BuildingType) buildableType);
		// a building of the same family already exists
		BuildingType from = colonyBuilding.getType().getUpgradesTo();
		if (from != newBuildingType && !assumeBuilt.contains(from)) {
			// the existing building's next upgrade is not the
			// new one we want to build
			return NoBuildReason.WRONG_UPGRADE;
		} else {
			return null;
		}
	}
}

class WrongBuildAbility extends FreeColObject implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {
		if (!buildableType.hasAbility(Ability.PERSON) && !hasAbility(Ability.BUILD, buildableType)
				&& assumeBuilt != null && none(assumeBuilt, bt -> bt.hasAbility(Ability.BUILD, buildableType))) {
			return NoBuildReason.MISSING_BUILD_ABILITY;
		} else {
			return null;
		}
	}

	@Override
	public String getXMLTagName() {
		// TODO Auto-generated method stub
		return null;
	}
}
