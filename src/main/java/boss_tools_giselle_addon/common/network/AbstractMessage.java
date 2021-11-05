package boss_tools_giselle_addon.common.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public abstract class AbstractMessage
{
	public abstract void decode(PacketBuffer buffer);

	public abstract void encode(PacketBuffer buffer);

	public abstract void onHandle(NetworkEvent.Context context);

}
