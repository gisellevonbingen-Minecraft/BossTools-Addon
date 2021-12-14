package boss_tools_giselle_addon.common.event;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.eventbus.api.Event;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class TileGaugeValueFetchEvent extends Event implements IGaugeValueFetchEvent
{
	private final TileEntity tileEntity;
	private final List<IGaugeValue> values;

	public TileGaugeValueFetchEvent(TileEntity tileEntity)
	{
		this.tileEntity = tileEntity;
		this.values = new ArrayList<>();
	}

	public TileEntity getBlockState()
	{
		return this.tileEntity;
	}

	@Override
	public List<IGaugeValue> getValues()
	{
		return this.values;
	}

}
