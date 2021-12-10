package boss_tools_giselle_addon;

import com.mojang.authlib.GameProfile;

import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FlagEditMessageOpen;
import boss_tools_giselle_addon.common.util.SkullUtils;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.network.PacketDistributor;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class EventListenerFlagEdit
{
	@SubscribeEvent
	public static void onFlagShiftRightClick(RightClickBlock e)
	{
		PlayerEntity player = e.getPlayer();

		if (e.isCanceled() == true)
		{
			return;
		}

		BlockPos pos = e.getPos();
		TileEntity tileEntity = e.getWorld().getBlockEntity(pos);

		if (tileEntity instanceof FlagTileEntity)
		{
			FlagTileEntity flag = (FlagTileEntity) tileEntity;
			ItemStack itemStack = e.getItemStack();

			if (e.getHand() != Hand.MAIN_HAND)
			{
				e.setCanceled(true);
			}
			else if (player.isShiftKeyDown() == false || itemStack.isEmpty() == true)
			{
				if (itemStack.getItem() == Items.PLAYER_HEAD)
				{
					GameProfile gameProfile = SkullUtils.getGameProfile(itemStack.getTag());
					flag.setPlayerProfile(gameProfile);
					e.setCanceled(true);
				}
				else
				{
					if (player instanceof ServerPlayerEntity)
					{
						ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
						AddonNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new FlagEditMessageOpen(pos));
					}

					e.setCanceled(true);
				}

			}

		}

	}

}
