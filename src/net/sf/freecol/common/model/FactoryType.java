package net.sf.freecol.common.model;

public class FactoryType {

	public InterfaceType getAdapter(BuildableType type, Colony colony) {

		// Use a hashmap

		// List where everypart checks if it can handle , go through one by one to check
		// who can, list where you register potential handlers

		// initate handler and

		SimpleHandlerType simpleHandler = new SimpleHandlerType();

		if (simpleHandler.canHandle(type)) {

			return simpleHandler;
		}

		HandlerType handlerType = new HandlerType();

		if (handlerType.canHandle(type, colony)) {

			return handlerType;
		}

		else if (type instanceof BuildingType) {

			AdapterBuildingType adapterBuilding = new AdapterBuildingType();

			return adapterBuilding;
		}

		else if (type instanceof UnitType) {

			AdapterUnitType adapterUnit = new AdapterUnitType();

			return adapterUnit;

		}

		else {
			return null;
		}
	}
}