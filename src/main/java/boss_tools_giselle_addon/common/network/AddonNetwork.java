package boss_tools_giselle_addon.common.network;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import boss_tools_giselle_addon.BossToolsAddon;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkEvent.Context;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class AddonNetwork
{
	public static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(BossToolsAddon.rl("main"), () -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals);
	private static int messageID;

	public static void registerAll()
	{
		registerMessage(GravityNormalizerMessageWorkingAreaVisible.class, GravityNormalizerMessageWorkingAreaVisible::new);
		registerMessage(GravityNormalizerMessageRange.class, GravityNormalizerMessageRange::new);
		registerMessage(AdvancedCompressorMessageMode.class, AdvancedCompressorMessageMode::new);
	}

	public static <T extends AbstractMessage> void registerMessage(Class<T> messageType, Supplier<T> supplier)
	{
		registerMessage(messageType, AbstractMessage::encode, buffer ->
		{
			T msg = supplier.get();
			msg.decode(buffer);
			return msg;
		}, (msg, s) ->
		{
			Context context = s.get();
			msg.onHandle(context);
			context.setPacketHandled(true);
		});
	}

	public static <T> void registerMessage(Class<T> messageType, BiConsumer<T, PacketBuffer> encoder, Function<PacketBuffer, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> messageConsumer)
	{
		CHANNEL.registerMessage(messageID, messageType, encoder, decoder, messageConsumer);
		messageID++;
	}

	private AddonNetwork()
	{

	}

}
