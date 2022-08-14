package boss_tools_giselle_addon.common.compat.redstonearsenal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.command.AddonCommand;
import boss_tools_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;

public class AddonRSACommand
{
	public static int fluxarmor(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlotType.HEAD, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_helmet"), AddonEnchantments.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlotType.CHEST, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_chestplate"), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
		player.setItemSlot(EquipmentSlotType.LEGS, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_leggings")));
		player.setItemSlot(EquipmentSlotType.FEET, AddonCommand.Equip.makeFull(AddonRedstoneArsenalCompat.rl("flux_boots"), AddonEnchantments.GRAVITY_NORMALIZING.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	private AddonRSACommand()
	{

	}

}
