package boss_tools_giselle_addon.common.content.flag;

import com.mojang.authlib.GameProfile;

import boss_tools_giselle_addon.common.network.AddonNetwork;
import boss_tools_giselle_addon.common.network.FlagEditMessageOpen;
import boss_tools_giselle_addon.common.util.SkullUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.properties.DoubleBlockHalf;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.boss_tools.flag.FlagBlock;
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
		BlockState blockState = e.getWorld().getBlockState(pos);
		EnumProperty<DoubleBlockHalf> property = FlagBlock.HALF;

		if (tileEntity instanceof FlagTileEntity && blockState.hasProperty(property) == true && blockState.getValue(property) == DoubleBlockHalf.UPPER)
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
						AddonNetwork.sendToPlayer(serverPlayer, new FlagEditMessageOpen(pos));
					}

					e.setCanceled(true);
				}

			}

		}

	}

}
