package net.sf.freecol.common.model;

public class FactoryType {
	
	public InterfaceType getAdapter(BuildableType type) {
		
		//Use a hashmap
		
		//List where everypart checks if it can handle , go through one by one to check who can, list where you register potential handlers
		
		//initate handler and 
		
		if(type instanceof BuildingType) {
			
			AdapterBuildingType adapterBuilding = new AdapterBuildingType();
			
			return adapterBuilding;
		}
		
		else if(type instanceof UnitType) {
			
			AdapterUnitType adapterUnit = new AdapterUnitType();
			
			return adapterUnit;
			
		}
		
		else {
			return null;
		}
	}
}