package boss_tools_giselle_addon.common;

import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FlagEditMessageOpen;
import boss_tools_giselle_addon.common.util.SkullUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.network.PacketDistributor;
import net.mrscauthd.boss_tools.flag.FlagBlock;
import net.mrscauthd.boss_tools.flag.FlagTileEntity;

public class EventListenerFlagEdit
{
	@SubscribeEvent
	public static void onFlagShiftRightClick(RightClickBlock e)
	{
		Player player = e.getPlayer();

		if (e.isCanceled() == true)
		{
			return;
		}

		BlockPos pos = e.getPos();
		BlockEntity blockEntity = e.getWorld().getBlockEntity(pos);
		BlockState blockState = e.getWorld().getBlockState(pos);
		EnumProperty<DoubleBlockHalf> property = FlagBlock.HALF;

		if (blockEntity instanceof FlagTileEntity && blockState.hasProperty(property) == true && blockState.getValue(property) == DoubleBlockHalf.UPPER)
		{
			FlagTileEntity flag = (FlagTileEntity) blockEntity;
			ItemStack itemStack = e.getItemStack();

			if (e.getHand() != InteractionHand.MAIN_HAND)
			{
				e.setCanceled(true);
			}
			else if (player.isShiftKeyDown() == false || itemStack.isEmpty() == true)
			{
				if (itemStack.getItem() == Items.PLAYER_HEAD)
				{
					SkullUtils.getGameProfile(itemStack.getTag(), flag::setOwner);
					e.setCanceled(true);
				}
				else
				{
					if (player instanceof ServerPlayer)
					{
						ServerPlayer serverPlayer = (ServerPlayer) player;
						AddonNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> serverPlayer), new FlagEditMessageOpen(pos));
					}

					e.setCanceled(true);
				}

			}

		}

	}

}
