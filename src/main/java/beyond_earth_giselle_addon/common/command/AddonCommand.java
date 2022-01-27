package beyond_earth_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Items;
import net.mrscauthd.beyond_earth.ModInit;

public class AddonCommand
{
	private AddonCommand()
	{

	}

	public static LiteralArgumentBuilder<CommandSourceStack> builder()
	{
		return Commands.literal("bega").then(PlanetSelection.builder());
	}

	public static boolean isPlayerHasPermission(CommandSourceStack cs, int permission)
	{
		return cs.hasPermission(2) && cs.getEntity() instanceof ServerPlayer;
	}

	public static boolean isPlayerHasPermission2(CommandSourceStack cs)
	{
		return isPlayerHasPermission(cs, 2);
	}

	private static class PlanetSelection
	{
		public static LiteralArgumentBuilder<CommandSourceStack> builder()
		{
			return Commands.literal("planetselection").requires(AddonCommand::isPlayerHasPermission2).executes(PlanetSelection::execute);
		}

		public static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();
			CompoundTag persistentData = player.getPersistentData();
			persistentData.putBoolean("beyond_earth:planet_selection_gui_open", true);
			persistentData.putString("beyond_earth:rocket_type", ModInit.TIER_4_ROCKET.get().getDescriptionId());
			persistentData.putString("beyond_earth:slot0", Items.AIR.getRegistryName().toString());

			return 0;
		}

	}

}
