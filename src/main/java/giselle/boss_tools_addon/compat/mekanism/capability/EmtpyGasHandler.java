package giselle.boss_tools_addon.compat.mekanism.capability;

import mekanism.api.Action;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;

public class EmtpyGasHandler implements IGasHandler
{
	@Override
	public GasStack extractChemical(int var1, long var2, Action var4)
	{
		return this.getEmptyStack();
	}

	@Override
	public GasStack getChemicalInTank(int var1)
	{
		return this.getEmptyStack();
	}

	@Override
	public long getTankCapacity(int var1)
	{
		return 0;
	}

	@Override
	public int getTanks()
	{
		return 0;
	}

	@Override
	public GasStack insertChemical(int var1, GasStack var2, Action var3)
	{
		return var2;
	}

	@Override
	public boolean isValid(int var1, GasStack var2)
	{
		return false;
	}

	@Override
	public void setChemicalInTank(int var1, GasStack var2)
	{

	}

	@Override
	public GasStack getEmptyStack()
	{
		return GasStack.EMPTY;
	}

}
