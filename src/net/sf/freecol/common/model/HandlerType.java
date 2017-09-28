package net.sf.freecol.common.model;

import java.util.List;
import static net.sf.freecol.common.util.CollectionUtils.*;
import net.sf.freecol.common.model.Colony.NoBuildReason;

public class HandlerType implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {

		if (buildableType.getRequiredPopulation() > colony.getUnitCount()) {
			return NoBuildReason.POPULATION_TOO_SMALL;
		}

		else if (buildableType.hasAbility(Ability.COASTAL_ONLY) && !colony.getTile().isCoastland()) {
			return NoBuildReason.COASTAL;
		} 
		else {

			if (!all(buildableType.getRequiredAbilities().entrySet(),
					e -> e.getValue() == colony.hasAbility(e.getKey()))) {
				return NoBuildReason.MISSING_ABILITY;
			}
			if (!all(buildableType.getLimits(), l -> l.evaluate(colony))) {
				return NoBuildReason.LIMIT_EXCEEDED;
			}

			return null;
		}
	}

	public boolean canHandle(BuildableType buildableType, Colony colony) {

		if (buildableType.getRequiredPopulation() > colony.getUnitCount()) {
			return true;
		} else if (buildableType.hasAbility(Ability.COASTAL_ONLY) && !colony.getTile().isCoastland()) {
			return true;
		} else {
			if (!all(buildableType.getRequiredAbilities().entrySet(),
					e -> e.getValue() == colony.hasAbility(e.getKey()))) {
				return true;
			}
			if (!all(buildableType.getLimits(), l -> l.evaluate(colony))) {
				return true;
			}

			return false;
		}
	}
}