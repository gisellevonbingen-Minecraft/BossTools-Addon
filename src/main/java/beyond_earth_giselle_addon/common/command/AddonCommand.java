package beyond_earth_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.compat.mekanism.AddonMekanismCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.mrscauthd.beyond_earth.capabilities.oxygen.OxygenUtil;
import net.mrscauthd.beyond_earth.registries.EntitiesRegistry;
import net.mrscauthd.beyond_earth.registries.ItemsRegistry;

public class AddonCommand
{
	private AddonCommand()
	{

	}

	public static LiteralArgumentBuilder<CommandSourceStack> builder()
	{
		return Commands.literal("bega") //
				.then(PlanetSelection.builder()) //
				.then(Equip.builder()) //
		;
	}

	public static boolean isPlayerHasPermission(CommandSourceStack cs, int permission)
	{
		return cs.hasPermission(2) && cs.getEntity() instanceof ServerPlayer;
	}

	public static boolean isPlayerHasPermission2(CommandSourceStack cs)
	{
		return isPlayerHasPermission(cs, 2);
	}

	public static int sendEquipedMessage(CommandSourceStack source)
	{
		source.sendSuccess(new TextComponent("Equipped"), false);
		return 0;
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
			persistentData.putString("beyond_earth:rocket_type", EntitiesRegistry.TIER_4_ROCKET.get().toString());
			persistentData.putString("beyond_earth:slot0", Items.AIR.getRegistryName().toString());

			return 0;
		}

	}

	private static class Equip
	{
		public static LiteralArgumentBuilder<CommandSourceStack> builder()
		{
			LiteralArgumentBuilder<CommandSourceStack> builder = Commands.literal("equip").requires(AddonCommand::isPlayerHasPermission2) //
					.then(Commands.literal("spacesuit1").executes(Equip::spacesuit1)) //
					.then(Commands.literal("spacesuit2").executes(Equip::spacesuit2)) //
			;

			if (AddonCompatibleManager.MEKANISM.isLoaded() == true)
			{
				builder.then(Commands.literal("mekasuit").executes(AddonMekanismCommand::mekasuit));
			}

			return builder;
		}

		public static int spacesuit1(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ItemsRegistry.OXYGEN_MASK.get()));
			player.setItemSlot(EquipmentSlot.CHEST, OxygenUtil.makeFull(new ItemStack(ItemsRegistry.SPACE_SUIT.get())));
			player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ItemsRegistry.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, new ItemStack(ItemsRegistry.SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static int spacesuit2(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, new ItemStack(ItemsRegistry.NETHERITE_OXYGEN_MASK.get()));
			player.setItemSlot(EquipmentSlot.CHEST, OxygenUtil.makeFull(new ItemStack(ItemsRegistry.NETHERITE_SPACE_SUIT.get())));
			player.setItemSlot(EquipmentSlot.LEGS, new ItemStack(ItemsRegistry.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, new ItemStack(ItemsRegistry.NETHERITE_SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

	}

}
