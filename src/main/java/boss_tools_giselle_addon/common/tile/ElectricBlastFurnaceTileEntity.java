package boss_tools_giselle_addon.common.tile;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.block.ElectricBlastFurnaceBlock;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class ElectricBlastFurnaceTileEntity extends ItemStackToItemStackTileEntity
{
	public ElectricBlastFurnaceTileEntity()
	{
		super(AddonTiles.ELECTRIC_BLAST_FURNACE.get());
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
				return ElectricBlastFurnaceTileEntity.this.getBasePowerForOperation();
			}
		});
	}

	@Override
	@Nullable
	public Container createMenu(int windowId, PlayerInventory inv)
	{
		return new ElectricBlastFurnaceContainer(windowId, inv, this);
	}

	@Override
	public ITextComponent getDisplayName()
	{
		return new TranslationTextComponent("container.boss_tools_giselle_addon.electric_blast_furnace");
	}

	public int getBasePowerForOperation()
	{
		return 1;
	}

	protected BooleanProperty getBlockActivatedProperty()
	{
		return ElectricBlastFurnaceBlock.LIT;
	}

	public ItemStackToItemStackRecipeType<?> getRecipeType()
	{
		return BossToolsRecipeTypes.BLASTING;
	}

}
