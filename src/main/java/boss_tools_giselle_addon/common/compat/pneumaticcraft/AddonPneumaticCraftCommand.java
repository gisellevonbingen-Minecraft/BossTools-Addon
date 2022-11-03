package boss_tools_giselle_addon.common.compat.pneumaticcraft;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.command.AddonCommand;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.core.ModItems;
import me.desht.pneumaticcraft.common.item.ItemPneumaticArmor;
import me.desht.pneumaticcraft.common.util.UpgradableItemUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class AddonPneumaticCraftCommand
{
	public static int pneumatic_armor(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlotType.HEAD, makeFull(ModItems.PNEUMATIC_HELMET.get(), AddonEnumUpgrade.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlotType.CHEST, makeFull(ModItems.PNEUMATIC_CHESTPLATE.get(), AddonEnumUpgrade.SPACE_FIRE_PROOF.get(), AddonEnumUpgrade.VENUS_ACID_PROOF.get()));
		player.setItemSlot(EquipmentSlotType.LEGS, makeFull(ModItems.PNEUMATIC_LEGGINGS.get()));
		player.setItemSlot(EquipmentSlotType.FEET, makeFull(ModItems.PNEUMATIC_BOOTS.get(), AddonEnumUpgrade.GRAVITY_NORMALIZING.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(ItemPneumaticArmor item, EnumUpgrade... upgrades)
	{
		ItemStack stack = new ItemStack(item);
		ItemStackHandler upgradeInventory = new ItemStackHandler(UpgradableItemUtils.UPGRADE_INV_SIZE);

		for (int i = 0; i < upgrades.length; i++)
		{
			EnumUpgrade upgrade = upgrades[i];
			upgradeInventory.setStackInSlot(i, new ItemStack(upgrade.getItem(upgrade.getMaxTier())));
		}

		UpgradableItemUtils.setUpgrades(stack, upgradeInventory);

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		airHandler.addAir((int) (airHandler.getBaseVolume() * airHandler.maxPressure()));

		return stack;
	}

	private AddonPneumaticCraftCommand()
	{

	}

}
