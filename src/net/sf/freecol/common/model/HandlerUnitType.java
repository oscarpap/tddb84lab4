package net.sf.freecol.common.model;

import static net.sf.freecol.common.util.CollectionUtils.none;

import java.util.List;

import net.sf.freecol.common.model.Colony.NoBuildReason;

public class HandlerUnitType extends FreeColObject implements InterfaceType {

	@Override
	public NoBuildReason getNoBuildReason(BuildableType buildableType, List<BuildableType> assumeBuilt, Colony colony) {

		// Non-person units need a BUILD ability, present or assumed.
        if (!buildableType.hasAbility(Ability.PERSON)
            && !hasAbility(Ability.BUILD, buildableType)
            && none(assumeBuilt, bt -> bt.hasAbility(Ability.BUILD,
                    buildableType))) {
            return NoBuildReason.MISSING_BUILD_ABILITY;
        }
        
        return NoBuildReason.NONE;
	}

	@Override
	public String getXMLTagName() {
		// TODO Auto-generated method stub
		return null;
	}
}
