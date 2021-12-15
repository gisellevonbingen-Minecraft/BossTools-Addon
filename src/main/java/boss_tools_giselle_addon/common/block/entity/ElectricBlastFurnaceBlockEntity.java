package boss_tools_giselle_addon.common.block.entity;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.inventory.ElectricBlastFurnaceContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackBlockEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class ElectricBlastFurnaceBlockEntity extends ItemStackToItemStackBlockEntity
{
	public ElectricBlastFurnaceBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntities.ELECTRIC_BLAST_FURNACE.get(), pos, state);
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

	public ItemStackToItemStackRecipeType<?> getRecipeType()
	{
		return BossToolsRecipeTypes.BLASTING;
	}

}