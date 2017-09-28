package net.sf.freecol.common.model;

public class FactoryType {

	private HandlerBuildingType adapterBuilding;
	private HandlerUnitType adapterUnit;
	private HandlerType handlerType;
	private SimpleHandlerType simpleHandler;

	public InterfaceType getAdapter(BuildableType type, Colony colony) {

		// List where everypart checks if it can handle , go through one by one to check
		// who can, list where you register potential handlers

		
		// Only needs the BuildableType
		if (simpleHandler.canHandle(type)) {

			return simpleHandler;
		}

		// Needs BuildableType and Colony
		if (handlerType.canHandle(type, colony)) {

			return handlerType;
		}

		// Needs to be correct type
		else if (type instanceof BuildingType) {

			return adapterBuilding;
		}

		// Needs to be correct type
		else if (type instanceof UnitType) {

			return adapterUnit;
		}

		else {
			return null;
		}
	}
}