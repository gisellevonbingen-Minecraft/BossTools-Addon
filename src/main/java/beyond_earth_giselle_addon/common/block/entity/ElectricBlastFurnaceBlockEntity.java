package beyond_earth_giselle_addon.common.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.machines.tile.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemRegistry;

public class ElectricBlastFurnaceBlockEntity extends ItemStackToItemStackBlockEntityMultiRecipe
{
	public ElectricBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.ELECTRIC_BLAST_FURNACE.get(), pos, state);
	}

	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry)
	{
		super.createEnergyStorages(registry);
		registry.put(this.createEnergyStorageCommon());
	}

	protected void createPowerSystems(PowerSystemRegistry map)
	{
		super.createPowerSystems(map);
		map.put(new PowerSystemEnergyCommon(this)
		{
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

}
