package boss_tools_giselle_addon.common.compat.mekanism.gear.mekasuit;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.BossToolsAddon;
import boss_tools_giselle_addon.common.compat.mekanism.gear.ModulesHelper;
import boss_tools_giselle_addon.common.config.AddonConfigs;
import mekanism.api.Action;
import mekanism.api.chemical.gas.Gas;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.content.gear.HUDElement;
import mekanism.common.content.gear.Modules.ModuleData;
import mekanism.common.content.gear.mekasuit.ModuleMekaSuit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismGases;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.StorageUtils;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class ModuleSpaceBreathingUnit extends ModuleMekaSuit
{
	private static final String OXYGEN_LIFE_KEY = "oxygenLife";

	public static final ResourceLocation ICON = BossToolsAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private long maxProduceRate = 0;
	private long oxygenUsing = 0;
	private long oxygenDuration = 0;
	private FloatingLong energyUsing;

	@Override
	public void init(ModuleData<?> data, ItemStack container)
	{
		super.init(data, container);

		this.maxProduceRate = AddonConfigs.Common.mekanism.moduleSpaceBreathing_maxProduceRate.get();
		this.oxygenUsing = AddonConfigs.Common.mekanism.moduleSpaceBreathing_oxygenUsing.get();
		this.oxygenDuration = AddonConfigs.Common.mekanism.moduleSpaceBreathing_oxygenDuration.get();
		this.energyUsing = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_energyUsing.get());
	}

	/**
	 * 
	 * @param entity
	 * @return whether provided oxygen to entity
	 */
	public boolean provideOxygen(LivingEntity entity)
	{
		if (this.getOxygenLife() > 0)
		{
			return true;
		}
		else if (this.useResources(entity) == true)
		{
			this.setOxygenLife(this.getOxygenDuration());
			return true;
		}
		else
		{
			return false;
		}

	}

	@Override
	protected void tickServer(PlayerEntity player)
	{
		super.tickServer(player);

		this.produceOxygen(player);
		this.reduceOxygenLife();
	}

	private void reduceOxygenLife()
	{
		long oxygenLife = this.getOxygenLife();

		if (oxygenLife > 0)
		{
			this.setOxygenLife(oxygenLife - 1);
		}

	}

	private void produceOxygen(PlayerEntity player)
	{
		long productionRate = getProduceRate(player);

		if (productionRate > 0)
		{
			ItemStack chestStack = this.getContainer();
			IGasHandler chestCapability = chestStack.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

			if (chestCapability != null)
			{
				chestCapability.insertChemical(MekanismGases.OXYGEN.getStack(productionRate), Action.EXECUTE);
			}

		}

	}

	public long getProduceRate(PlayerEntity player)
	{
		double maskHeight = player.getEyeHeight() - 0.15D;
		BlockPos headPos = new BlockPos(player.getBbWidth(), maskHeight, player.getBbHeight());
		FluidState fluidstate = player.level.getFluidState(headPos);

		if (fluidstate.is(FluidTags.WATER) && maskHeight <= (headPos.getY() + fluidstate.getHeight(player.level, headPos)))
		{
			return this.getMaxProduceRate();
		}
		else if (player.isInWaterOrRain())
		{
			return this.getMaxProduceRate() / 2;
		}

		return 0L;
	}

	public boolean useResources(LivingEntity entity)
	{
		ItemStack container = this.getContainer();
		IGasHandler gasHandlerItem = container.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);
		GasStack usingOxygen = new GasStack(this.getGas(), this.getOxygenUsing());

		if (gasHandlerItem != null && gasHandlerItem.extractChemical(usingOxygen, Action.SIMULATE).getAmount() >= usingOxygen.getAmount())
		{
			FloatingLong energyUsing = this.getEnergyUsing();

			if (this.canUseEnergy(entity, energyUsing) == true)
			{
				gasHandlerItem.extractChemical(usingOxygen, Action.EXECUTE);
				this.useEnergy(entity, energyUsing);
				return true;
			}

		}

		return false;
	}

	@Override
	public void addHUDElements(List<HUDElement> list)
	{
		super.addHUDElements(list);

		if (this.isEnabled() == false)
		{
			return;
		}

		ItemStack container = this.getContainer();
		Gas gas = this.getGas();
		GasStack stored = ((ItemMekaSuitArmor) container.getItem()).getContainedGas(container, gas);
		long capacity = this.getGasCapacity(container, gas);
		double ratio = StorageUtils.getRatio(stored.getAmount(), capacity);
		list.add(HUDElement.percent(ICON, ratio));
	}

	public Gas getGas()
	{
		return MekanismGases.OXYGEN.get();
	}

	@Nullable
	private long getGasCapacity(ItemStack container, Gas gas)
	{
		IGasHandler gasHandlerItem = container.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

		if (gasHandlerItem != null)
		{
			for (int i = 0; i < gasHandlerItem.getTanks(); ++i)
			{
				if (gasHandlerItem.isValid(i, new GasStack(gas, 1)))
				{
					return gasHandlerItem.getTankCapacity(i);
				}

			}

		}

		return 0;
	}

	public long getMaxProduceRate()
	{
		return this.maxProduceRate;
	}

	public long getOxygenUsing()
	{
		return this.oxygenUsing;
	}

	public long getOxygenDuration()
	{
		return this.oxygenDuration;
	}

	public FloatingLong getEnergyUsing()
	{
		return this.energyUsing;
	}

	public long getOxygenLife()
	{
		CompoundNBT compound = ModulesHelper.getCustomTag(this, this.getContainer());
		return compound != null ? compound.getLong(OXYGEN_LIFE_KEY) : 0L;
	}

	public void setOxygenLife(long oxygenLife)
	{
		CompoundNBT compound = ModulesHelper.getOrCreateCustomTag(this, this.getContainer());
		compound.putLong(OXYGEN_LIFE_KEY, Math.max(oxygenLife, 0L));
	}

}
