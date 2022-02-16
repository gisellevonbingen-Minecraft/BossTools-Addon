package boss_tools_giselle_addon.common.tile;

import java.util.List;

import javax.annotation.Nullable;

import boss_tools_giselle_addon.common.config.AddonConfigs;
import boss_tools_giselle_addon.common.content.gravity.GravityNormalizeUtils;
import boss_tools_giselle_addon.common.inventory.container.GravityNormalizerContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.machines.tile.AbstractMachineTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystem;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class GravityNormalizerTileEntity extends AbstractMachineTileEntity
{
	public static final String DATA_RANGE_KEY = "range";
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	private PowerSystem energyPowerSystem;

	public GravityNormalizerTileEntity()
	{
		super(AddonTileEntitTypes.GRAVITY_NORMALIZER.get());
		this.setWorkingAreaVisible(false);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox()
	{
		return this.getWorkingArea();
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
		map.put(this.energyPowerSystem = new PowerSystemEnergyCommon(this)
		{
			@Override
			public int getBasePowerForOperation()
			{
				return GravityNormalizerTileEntity.this.getBasePowerForOperation();
			}
		});
	}

	public int getBasePowerForOperation()
	{
		return this.getBasePowerForOperation(this.getRange());
	}

	public int getBasePowerForOperation(int range)
	{
		int base = AddonConfigs.Common.machines.gravityNormalizer_energyUsingBase.get();
		int perRange = AddonConfigs.Common.machines.gravityNormalizer_energyUsingPerRange.get();
		return base + range * perRange;
	}

	@Override
	public boolean hasSpaceInOutput()
	{
		return true;
	}

	@Override
	@Nullable
	public Container createMenu(int windowId, PlayerInventory inv)
	{
		return new GravityNormalizerContainer(windowId, inv, this);
	}

	@Override
	public void tick()
	{
		this.doNormalize();

		super.tick();
	}

	@Override
	protected void tickProcessing()
	{

	}

	protected void doNormalize()
	{
		if (this.consumePowerForOperation() != null)
		{
			this.doNormalize(this.getWorkingArea());
		}

	}

	protected void doNormalize(AxisAlignedBB workingArea)
	{
		World level = this.getLevel();
		List<LivingEntity> livings = level.getEntitiesOfClass(LivingEntity.class, workingArea);

		for (LivingEntity entity : livings)
		{
			GravityNormalizeUtils.setNormalizing(entity, true);
		}

		List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class, workingArea);

		for (ItemEntity entity : items)
		{
			GravityNormalizeUtils.setNormalizing(entity, true);
		}

		this.setProcessedInThisTick();
	}

	public int getRange()
	{
		return Math.max(this.getTileData().getInt(DATA_RANGE_KEY), 1);
	}

	public void setRange(int range)
	{
		range = Math.min(Math.max(range, 1), 15);

		if (this.getRange() != range)
		{
			this.getTileData().putInt(DATA_RANGE_KEY, range);
			this.setChanged();
		}

	}

	public boolean isWorkingAreaVisible()
	{
		return this.getTileData().getBoolean(DATA_WORKINGAREA_VISIBLE_KEY);
	}

	public void setWorkingAreaVisible(boolean visible)
	{
		if (this.isWorkingAreaVisible() != visible)
		{
			this.getTileData().putBoolean(DATA_WORKINGAREA_VISIBLE_KEY, visible);
			this.setChanged();
		}

	}

	public AxisAlignedBB getWorkingArea()
	{
		return this.getWorkingArea(this.getRange());
	}

	public AxisAlignedBB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public AxisAlignedBB getWorkingArea(BlockPos pos, double range)
	{
		return new AxisAlignedBB(pos).inflate(range).move(0.0D, range, 0.0D);
	}

	public PowerSystem getEnergyPowerSystem()
	{
		return this.energyPowerSystem;
	}

}
