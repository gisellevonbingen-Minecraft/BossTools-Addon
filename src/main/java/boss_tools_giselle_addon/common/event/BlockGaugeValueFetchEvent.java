package boss_tools_giselle_addon.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.eventbus.api.Event;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class BlockGaugeValueFetchEvent extends Event implements IGaugeValueFetchEvent
{
	private final BlockEntity blockEntity;
	private final List<IGaugeValue> values;

	public BlockGaugeValueFetchEvent(BlockEntity blockEntity)
	{
		this.blockEntity = blockEntity;
		this.values = new ArrayList<>();
	}

	public BlockEntity getBlockState()
	{
		return this.blockEntity;
	}

	@Override
	public List<IGaugeValue> getValues()
	{
		return this.values;
	}

}
