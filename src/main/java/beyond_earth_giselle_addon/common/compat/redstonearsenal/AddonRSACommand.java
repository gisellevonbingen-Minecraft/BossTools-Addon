package beyond_earth_giselle_addon.common.compat.redstonearsenal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import beyond_earth_giselle_addon.common.command.AddonCommand;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;

public class AddonRSACommand
{
	public static int fluxarmor(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
	{
		CommandSourceStack source = context.getSource();
		ServerPlayer player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlot.HEAD, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_helmet"), AddonEnchantments.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlot.CHEST, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_chestplate"), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
		player.setItemSlot(EquipmentSlot.LEGS, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_leggings")));
		player.setItemSlot(EquipmentSlot.FEET, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_boots"), AddonEnchantments.GRAVITY_NORMALIZING.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	private AddonRSACommand()
	{

	}

}
