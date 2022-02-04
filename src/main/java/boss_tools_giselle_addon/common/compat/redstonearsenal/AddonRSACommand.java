package boss_tools_giselle_addon.common.compat.redstonearsenal;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.command.AddonCommand;
import boss_tools_giselle_addon.common.enchantment.AddonEnchantments;
import net.minecraft.command.CommandSource;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;

public class AddonRSACommand
{
	public static int fluxarmor(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlotType.HEAD, makeFull("flux_helmet", AddonEnchantments.SPACE_BREATHING.get()));
		player.setItemSlot(EquipmentSlotType.CHEST, makeFull("flux_chestplate", AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
		player.setItemSlot(EquipmentSlotType.LEGS, makeFull("flux_leggings"));
		player.setItemSlot(EquipmentSlotType.FEET, makeFull("flux_boots", AddonEnchantments.GRAVITY_NORMALIZING.get()));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(String name, Enchantment... enchantments)
	{
		ResourceLocation key = AddonRedstoneArsenalCompat.rl(name);
		Item item = ForgeRegistries.ITEMS.getValue(key);
		return makeFull(item, enchantments);
	}

	private static ItemStack makeFull(Item item, Enchantment... enchantments)
	{
		ItemStack stack = new ItemStack(item);

		for (Enchantment enchantment : enchantments)
		{
			stack.enchant(enchantment, 1);
		}

		IEnergyStorage energyStorage = stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);

		if (energyStorage != null)
		{
			for (int i = 0; i < 100; i++)
			{
				energyStorage.receiveEnergy(energyStorage.getMaxEnergyStored(), false);
			}
			
		}

		return stack;
	}

	private AddonRSACommand()
	{

	}

}
