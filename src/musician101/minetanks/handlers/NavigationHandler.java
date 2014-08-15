package musician101.minetanks.handlers;

import musician101.minetanks.tankinfo.Countries;
import musician101.minetanks.tankinfo.TankTypes;

public class NavigationHandler
{
	Countries country;
	TankTypes type;
	
	public NavigationHandler(Countries country)
	{
		this(country, null);
	}
	
	public NavigationHandler(Countries country, TankTypes type)
	{
		this.country = country;
		this.type = type;
	}
	
	public Countries getCountry()
	{
		return country;
	}
	
	public TankTypes getType()
	{
		return type;
	}
	
	public void setType(TankTypes type)
	{
		this.type = type;
	}
}
