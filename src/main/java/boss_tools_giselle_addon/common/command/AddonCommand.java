package boss_tools_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.mrscauthd.boss_tools.ModInnet;

public class AddonCommand
{
	private AddonCommand()
	{

	}

	public static LiteralArgumentBuilder<CommandSource> builder()
	{
		return Commands.literal("btga").then(PlanetSelection.builder());
	}

	public static boolean isPlayerHasPermission(CommandSource cs, int permission)
	{
		return cs.hasPermission(2) && cs.getEntity() instanceof ServerPlayerEntity;
	}

	public static boolean isPlayerHasPermission2(CommandSource cs)
	{
		return isPlayerHasPermission(cs, 2);
	}

	private static class PlanetSelection
	{
		public static LiteralArgumentBuilder<CommandSource> builder()
		{
			return Commands.literal("planetselection").requires(AddonCommand::isPlayerHasPermission2).executes(PlanetSelection::execute);
		}

		public static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException
		{
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.getPlayerOrException();
			CompoundNBT persistentData = player.getPersistentData();
			persistentData.putBoolean("boss_tools:planet_selection_gui_open", true);
			persistentData.putString("boss_tools:rocket_type", ModInnet.TIER_3_ROCKET.get().getDescriptionId());
			persistentData.putString("boss_tools:slot0", Items.AIR.getRegistryName().toString());

			return 0;
		}

	}

}
