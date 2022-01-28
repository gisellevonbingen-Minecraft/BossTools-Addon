package beyond_earth_giselle_addon.common.block.entity;

import java.util.List;

import javax.annotation.Nullable;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.content.gravity.GravityNormalizeUtils;
import beyond_earth_giselle_addon.common.inventory.GravityNormalizerContainerMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.machines.tile.AbstractMachineBlockEntity;
import net.mrscauthd.beyond_earth.machines.tile.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystem;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemRegistry;

public class GravityNormalizerBlockEntity extends AbstractMachineBlockEntity
{
	public static final String DATA_RANGE_KEY = "range";
	public static final String DATA_WORKINGAREA_VISIBLE_KEY = "workingAreaVisible";

	private PowerSystem energyPowerSystem;

	public GravityNormalizerBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntities.GRAVITY_NORMALIZER.get(), pos, state);
		this.setWorkingAreaVisible(false);
	}

	@Override
	public AABB getRenderBoundingBox()
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
				return GravityNormalizerBlockEntity.this.getBasePowerForOperation();
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
	public AbstractContainerMenu createMenu(int windowId, Inventory inv)
	{
		return new GravityNormalizerContainerMenu(windowId, inv, this);
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

	protected void doNormalize(AABB workingArea)
	{
		Level level = this.getLevel();
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

	public AABB getWorkingArea()
	{
		return this.getWorkingArea(this.getRange());
	}

	public AABB getWorkingArea(double range)
	{
		return this.getWorkingArea(this.getBlockPos(), range);
	}

	public AABB getWorkingArea(BlockPos pos, double range)
	{
		return new AABB(pos).inflate(range).move(0.0D, range, 0.0D);
	}

	public PowerSystem getEnergyPowerSystem()
	{
		return this.energyPowerSystem;
	}

}
