package boss_tools_giselle_addon.common.compat.mekanism;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.command.AddonCommand;
import mekanism.api.gear.IModule;
import mekanism.api.math.FloatingLong;
import mekanism.api.providers.IModuleDataProvider;
import mekanism.common.config.MekanismConfig;
import mekanism.common.content.gear.shared.ModuleEnergyUnit;
import mekanism.common.item.gear.ItemMekaSuitArmor;
import mekanism.common.registries.MekanismItems;
import mekanism.common.registries.MekanismModules;
import mekanism.common.util.StorageUtils;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;

public class AddonMekanismCommand
{
	public static int mekasuit(CommandContext<CommandSource> context) throws CommandSyntaxException
	{
		CommandSource source = context.getSource();
		ServerPlayerEntity player = source.getPlayerOrException();

		player.setItemSlot(EquipmentSlotType.HEAD, makeFull(MekanismItems.MEKASUIT_HELMET.get(), AddonMekanismModules.SPACE_BREATHING_UNIT));
		player.setItemSlot(EquipmentSlotType.CHEST, makeFull(MekanismItems.MEKASUIT_BODYARMOR.get(), AddonMekanismModules.SPACE_FIRE_PROOF_UNIT, AddonMekanismModules.VENUS_ACID_PROOF_UNIT));
		player.setItemSlot(EquipmentSlotType.LEGS, makeFull(MekanismItems.MEKASUIT_PANTS.get()));
		player.setItemSlot(EquipmentSlotType.FEET, makeFull(MekanismItems.MEKASUIT_BOOTS.get(), AddonMekanismModules.GRAVITY_NORMALIZING_UNIT));

		return AddonCommand.sendEquipedMessage(source);
	}

	private static ItemStack makeFull(ItemMekaSuitArmor item, IModuleDataProvider<?>... moduleProviders)
	{
		ItemStack stack = new ItemStack(item);

		for (IModuleDataProvider<?> moduleProvider : moduleProviders)
		{
			item.addModule(stack, moduleProvider.getModuleData());
		}

		IModule<ModuleEnergyUnit> energyUnit = item.getModule(stack, MekanismModules.ENERGY_UNIT);
		FloatingLong maxEnergy = energyUnit == null ? MekanismConfig.gear.mekaSuitBaseEnergyCapacity.get() : energyUnit.getCustomInstance().getEnergyCapacity(energyUnit);

		return StorageUtils.getFilledEnergyVariant(stack, maxEnergy);
	}

	private AddonMekanismCommand()
	{

	}

}
