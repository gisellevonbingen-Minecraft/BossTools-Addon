package boss_tools_giselle_addon.common.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import boss_tools_giselle_addon.common.compat.AddonCompatibleManager;
import boss_tools_giselle_addon.common.compat.mekanism.AddonMekanismCommand;
import boss_tools_giselle_addon.common.compat.redstonearsenal.AddonRSACommand;
import boss_tools_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;
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

	public static class PlanetSelection
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

	public static class Equip
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

			if (AddonCompatibleManager.REDSTONE_ARSENAL.isLoaded() == true)
			{
				builder.then(Commands.literal("flux_armor").executes(AddonRSACommand::fluxarmor));
			}

			return builder;
		}

		public static int spacesuit1(CommandContext<CommandSource> context) throws CommandSyntaxException
		{
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlotType.HEAD, makeFull(ModInnet.OXYGEN_MASK.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlotType.CHEST, makeFull(ModInnet.SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlotType.LEGS, makeFull(ModInnet.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlotType.FEET, makeFull(ModInnet.SPACE_BOOTS.get(), AddonEnchantments.GRAVITY_NORMALIZING.get()));

			return sendEquipedMessage(source);
		}

		public static int spacesuit2(CommandContext<CommandSource> context) throws CommandSyntaxException
		{
			CommandSource source = context.getSource();
			ServerPlayerEntity player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlotType.HEAD, makeFull(ModInnet.NETHERITE_OXYGEN_MASK.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlotType.CHEST, makeFull(ModInnet.NETHERITE_SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlotType.LEGS, makeFull(ModInnet.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlotType.FEET, makeFull(ModInnet.NETHERITE_SPACE_BOOTS.get(), AddonEnchantments.GRAVITY_NORMALIZING.get()));

			return sendEquipedMessage(source);
		}

		public static ItemStack makeFull(ResourceLocation name, Enchantment... enchantments)
		{
			Item item = ForgeRegistries.ITEMS.getValue(name);
			return makeFull(item, enchantments);
		}

		public static ItemStack makeFull(Item item, Enchantment... enchantments)
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

			return OxygenUtil.makeFull(stack);
		}

	}

}
