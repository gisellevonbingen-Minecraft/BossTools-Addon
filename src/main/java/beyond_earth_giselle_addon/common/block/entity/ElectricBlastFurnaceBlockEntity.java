package beyond_earth_giselle_addon.common.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import beyond_earth_giselle_addon.common.item.crafting.ItemStackToItemStackRecipeWrapper;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.PowerSystemEnergyCommon;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.PowerSystemRegistry;
import net.mrscauthd.beyond_earth.common.capabilities.energy.EnergyStorageBasic;

public class ElectricBlastFurnaceBlockEntity extends ItemStackToItemStackBlockEntityMultiRecipe
{
	public static final String KEY_EXPEREICNET = "experience";

	private float experience;

	public ElectricBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.ELECTRIC_BLAST_FURNACE.get(), pos, state);
	}

	public void awardExp(ServerLevel level, Vec3 position)
	{
		float stored = this.getExperience();
		int awarding = (int) stored;

		if (awarding > 0)
		{
			this.setExperience(stored - awarding);
			ExperienceOrb.award(level, position, awarding);
		}

	}

	@Override
	protected void onCooking()
	{
		super.onCooking();

		if (this.getTimer() >= this.getMaxTimer())
		{
			if (this.getCachedRecipe() instanceof ItemStackToItemStackRecipeWrapper recipe)
			{
				this.setExperience(this.getExperience() + recipe.getExperience());
			}

		}

	}

	@Override
	public void load(CompoundTag compound)
	{
		super.load(compound);

		this.experience = compound.getFloat(KEY_EXPEREICNET);
	}

	@Override
	protected void saveAdditional(CompoundTag compound)
	{
		super.saveAdditional(compound);

		compound.putFloat(KEY_EXPEREICNET, this.experience);
	}

	@Override
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry)
	{
		super.createEnergyStorages(registry);
		int capacity = AddonConfigs.Common.machines.electricBlastFurnace_energyCapcity.get();
		int transfer = AddonConfigs.Common.machines.electricBlastFurnace_energyTransfer.get();
		registry.put(new EnergyStorageBasic(this, capacity, transfer, capacity));
	}

	@Override
	protected void createPowerSystems(PowerSystemRegistry map)
	{
		super.createPowerSystems(map);
		map.put(new PowerSystemEnergyCommon(this)
		{
			@Override
			public int getBasePowerForOperation()
			{
				return ElectricBlastFurnaceBlockEntity.this.getBasePowerForOperation();
			}
		});
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv)
	{
		return new ElectricBlastFurnaceContainerMenu(windowId, inv, this);
	}

	public int getBasePowerForOperation()
	{
		return 1;
	}

	@Override
	public List<RecipeType<? extends Recipe<Container>>> getRecipeTypes()
	{
		List<RecipeType<? extends Recipe<Container>>> list = super.getRecipeTypes();
		list.add(RecipeType.BLASTING);
		return list;
	}

	public float getExperience()
	{
		return this.experience;
	}

	public void setExperience(float experience)
	{
		this.experience = Mth.clamp(experience, 0.0F, Integer.MAX_VALUE);
	}

}
