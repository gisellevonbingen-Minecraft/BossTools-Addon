package beyond_earth_giselle_addon.common.compat.mekanism.gear;

import java.util.Map;
import java.util.function.Consumer;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import mekanism.api.Action;
import mekanism.api.MekanismAPI;
import mekanism.api.chemical.gas.GasStack;
import mekanism.api.chemical.gas.IGasHandler;
import mekanism.api.gear.ICustomModule;
import mekanism.api.gear.IHUDElement;
import mekanism.api.gear.IModule;
import mekanism.api.gear.config.ModuleConfigItemCreator;
import mekanism.api.math.FloatingLong;
import mekanism.common.capabilities.Capabilities;
import mekanism.common.registries.MekanismGases;
import mekanism.common.tags.MekanismTags;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MekanismUtils.FluidInDetails;
import mekanism.common.util.StorageUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.AABB;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class ModuleSpaceBreathingUnit implements ICustomModule<ModuleSpaceBreathingUnit>
{
	public static final ResourceLocation ICON = BeyondEarthAddon.rl(MekanismUtils.ResourceType.GUI_HUD.getPrefix() + "space_breathing_unit.png");

	private int oxygenDuration = 0;
	private FloatingLong energyUsingProvide;
	private FloatingLong energyUsingProduce;

	public ModuleSpaceBreathingUnit()
	{

	}

	@Override
	public void init(IModule<ModuleSpaceBreathingUnit> module, ModuleConfigItemCreator configItemCreator)
	{
		ICustomModule.super.init(module, configItemCreator);

		this.oxygenDuration = AddonConfigs.Common.mekanism.moduleSpaceBreathing_oxygenDuration.get();
		this.energyUsingProvide = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_energyUsingProvide.get());
		this.energyUsingProduce = FloatingLong.create(AddonConfigs.Common.mekanism.moduleSpaceBreathing_energyUsingProduce.get());
	}

	/**
	 *
	 * @param entity
	 * @return provided oxygen to entity
	 */
	public int provideOxygen(IModule<ModuleSpaceBreathingUnit> module, LivingEntity entity)
	{
		if (this.useResources(module, entity) == true)
		{
			return this.getOxygenDuration();
		}
		else
		{
			return 0;
		}

	}

	@Override
	public void tickServer(IModule<ModuleSpaceBreathingUnit> module, Player player)
	{
		ICustomModule.super.tickServer(module, player);

		this.produceOxygen(module, player);
	}

	private void produceOxygen(IModule<ModuleSpaceBreathingUnit> module, Player player)
	{
		long productionRate = this.getProduceRate(module, player);

		if (productionRate == 0)
		{
			return;
		}

		FloatingLong energyUsing = this.getEnergyUsingProduce();
		productionRate = Math.min(productionRate, module.getContainerEnergy().divideToInt(energyUsing));
		long productionRateFirst = productionRate;

		ItemStack handStack = player.getItemBySlot(EquipmentSlot.MAINHAND);
		IGasHandler handCapability = handStack.getCapability(Capabilities.GAS_HANDLER_CAPABILITY).orElse(null);

		if (handCapability != null)
		{
			GasStack remain = handCapability.insertChemical(MekanismGases.OXYGEN.getStack(productionRate), Action.EXECUTE);
			productionRate = remain.getAmount();
		}

		long oxygenUsed = productionRateFirst - productionRate;
		FloatingLong multiply = energyUsing.multiply(oxygenUsed);

		if (player.level.isClientSide() == false)
		{
			module.useEnergy(player, multiply);
		}

		int airSupply = player.getAirSupply();
		int airFill = (int) Math.min(productionRateFirst, player.getMaxAirSupply() - airSupply);
		player.setAirSupply(airSupply + airFill);
	}

	public long getProduceRate(IModule<ModuleSpaceBreathingUnit> module, Player player)
	{
		float eyeHeight = player.getEyeHeight();
		Map<Fluid, FluidInDetails> fluidsIn = MekanismUtils.getFluidsIn(player, bb ->
		{
			double centerX = (bb.minX + bb.maxX) / 2;
			double centerZ = (bb.minZ + bb.maxZ) / 2;
			return new AABB(centerX, Math.min(bb.minY + eyeHeight - 0.27, bb.maxY), centerZ, centerX, Math.min(bb.minY + eyeHeight - 0.14, bb.maxY), centerZ);
		});
		if (fluidsIn.entrySet().stream().anyMatch(entry -> MekanismTags.Fluids.WATER_LOOKUP.contains(entry.getKey()) && entry.getValue().getMaxHeight() >= 0.11))
		{
			return this.getMaxProduceRate(module);
		}
		else if (player.isInWaterOrRain() == true)
		{
			return this.getMaxProduceRate(module) / 2L;
		}

		return 0L;
	}

	public boolean useResources(IModule<ModuleSpaceBreathingUnit> module, LivingEntity entity)
	{
		int oyxgenUsing = 1;
		IOxygenCharger oxygenCharnger = OxygenChargerUtils.firstExtractableOxygenCharger(entity, oyxgenUsing, module.getContainer());

		if (oxygenCharnger != null)
		{
			FloatingLong energyUsing = this.getEnergyUsingProvide();

			if (module.canUseEnergy(entity, energyUsing) == true)
			{
				if (entity.level.isClientSide() == false)
				{
					oxygenCharnger.getOxygenStorage().extractOxygen(oyxgenUsing, false);
					module.useEnergy(entity, energyUsing);
				}

				return true;
			}

		}

		return false;
	}

	@Override
	public void addHUDElements(IModule<ModuleSpaceBreathingUnit> module, Player player, Consumer<IHUDElement> hudElementAdder)
	{
		ICustomModule.super.addHUDElements(module, player, hudElementAdder);

		if (module.isEnabled() == false)
		{
			return;
		}

		IGaugeValue gauge = OxygenChargerUtils.getInventoryOxygenChargerStorage(player);
		int capacity = gauge.getCapacity();
		double ratio = capacity > 0 ? StorageUtils.getRatio(gauge.getAmount(), capacity) : 0.0D;
		hudElementAdder.accept(MekanismAPI.getModuleHelper().hudElementPercent(ICON, ratio));
	}

	public long getMaxProduceRate(IModule<ModuleSpaceBreathingUnit> module)
	{
		return (long) Math.pow(2L, module.getInstalledCount() - 1);
	}

	public int getOxygenDuration()
	{
		return this.oxygenDuration;
	}

	public FloatingLong getEnergyUsingProvide()
	{
		return this.energyUsingProvide;
	}

	public FloatingLong getEnergyUsingProduce()
	{
		return this.energyUsingProduce;
	}

}
