package boss_tools_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.compat.mekanism.AddonMekanismCommand;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.StringTextComponent;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.capability.OxygenUtil;

public class AddonCommand
{
	private AddonCommand()
	{

	}

	public static LiteralArgumentBuilder<CommandSource> builder()
	{
		return Commands.literal("btga") //
				.then(PlanetSelection.builder()) //
				.then(Equip.builder()) //
		;
	}

	public static boolean isPlayerHasPermission(CommandSource cs, int permission)
	{
		return cs.hasPermission(2) && cs.getEntity() instanceof ServerPlayerEntity;
	}

	public static boolean isPlayerHasPermission2(CommandSource cs)
	{
		return isPlayerHasPermission(cs, 2);
	}

	public static int sendEquipedMessage(CommandSource source)
	{
		source.sendSuccess(new StringTextComponent("Equipped"), false);
		return 0;
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

	private static class Equip
	{
		public static LiteralArgumentBuilder<CommandSource> builder()
		{
			LiteralArgumentBuilder<CommandSource> builder = Commands.literal("equip").requires(AddonCommand::isPlayerHasPermission2) //
					.then(Commands.literal("spacesuit1").executes(Equip::spacesuit1)) //
					.then(Commands.literal("spacesuit2").executes(Equip::spacesuit2)) //
			;

			if (AddonCompatibleManager.MEKANISM.isLoaded() == true)
			{
				builder.then(Commands.literal("mekasuit").executes(AddonMekanismCommand::mekasuit));
			}

			return builder;
		}

		public static int spacesuit1(CommandContext<CommandSource> context) throws CommandSyntaxException
		{
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ModInnet.OXYGEN_MASK.get()));
			player.setItemSlot(EquipmentSlotType.CHEST, OxygenUtil.makeFull(new ItemStack(ModInnet.SPACE_SUIT.get())));
			player.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ModInnet.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlotType.FEET, new ItemStack(ModInnet.SPACE_BOOTS.get()));
			
			return sendEquipedMessage(source);
		}

		public static int spacesuit2(CommandContext<CommandSource> context) throws CommandSyntaxException
		{
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlotType.HEAD, new ItemStack(ModInnet.NETHERITE_OXYGEN_MASK.get()));
			player.setItemSlot(EquipmentSlotType.CHEST, OxygenUtil.makeFull(new ItemStack(ModInnet.NETHERITE_SPACE_SUIT.get())));
			player.setItemSlot(EquipmentSlotType.LEGS, new ItemStack(ModInnet.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlotType.FEET, new ItemStack(ModInnet.NETHERITE_SPACE_BOOTS.get()));
			
			return sendEquipedMessage(source);
		}

	}

}
