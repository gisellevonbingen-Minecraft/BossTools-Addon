package boss_tools_giselle_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.block.AbstractMachineBlock;
import boss_tools_giselle_addon.common.entity.ExperienceOrbEntityHelper;
import boss_tools_giselle_addon.common.inventory.container.ElectricBlastFurnaceContainer;
import boss_tools_giselle_addon.common.item.crafting.ItemStackToItemStackRecipeWrapper;
import boss_tools_giselle_addon.common.registries.AddonTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class ElectricBlastFurnaceTileEntity extends ItemStackToItemStackTileEntityMultiRecipe
{
	public static final String KEY_EXPEREICNET = "experience";

	private float experience;

	public ElectricBlastFurnaceTileEntity()
	{
		super(AddonTileEntityTypes.ELECTRIC_BLAST_FURNACE.get());
	}

	public void awardExp(World level, Vector3d position)
	{
		float stored = this.getExperience();
		int awarding = (int) stored;

		if (awarding > 0)
		{
			this.setExperience(stored - awarding);
			ExperienceOrbEntityHelper.award(level, position, awarding);
		}

	}

	@Override
	protected void onCooking()
	{
		super.onCooking();

		if (this.getTimer() >= this.getMaxTimer())
		{
			if (this.getCachedRecipe() instanceof ItemStackToItemStackRecipeWrapper)
			{
				ItemStackToItemStackRecipeWrapper recipe = (ItemStackToItemStackRecipeWrapper) this.getCachedRecipe();
				this.setExperience(this.getExperience() + recipe.getExperience());
			}

		}

	}

	@Override
	public void load(BlockState blockState, CompoundNBT compound)
	{
		super.load(blockState, compound);

		this.experience = compound.getFloat(KEY_EXPEREICNET);
	}

	@Override
	public CompoundNBT save(CompoundNBT compound)
	{
		super.save(compound);

		compound.putFloat(KEY_EXPEREICNET, this.experience);
		return compound;
	}

	@Override
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry)
	{
		super.createEnergyStorages(registry);
		registry.put(this.createEnergyStorageCommon());
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

	public int getBasePowerForOperation()
	{
		return 1;
	}

	@Override
	protected BooleanProperty getBlockActivatedProperty()
	{
		return AbstractMachineBlock.LIT;
	}

	@Override
	public List<IRecipeType<? extends IRecipe<IInventory>>> getRecipeTypes()
	{
		List<IRecipeType<? extends IRecipe<IInventory>>> list = super.getRecipeTypes();
		list.add(BossToolsRecipeTypes.BLASTING);
		list.add(IRecipeType.BLASTING);
		return list;
	}

	public float getExperience()
	{
		return this.experience;
	}

	public void setExperience(float experience)
	{
		this.experience = MathHelper.clamp(experience, 0.0F, Integer.MAX_VALUE);
	}

}
