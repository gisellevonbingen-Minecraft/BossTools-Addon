package beyond_earth_giselle_addon.common.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import beyond_earth_giselle_addon.common.BeyondEarthAddon;
import beyond_earth_giselle_addon.common.compat.AddonCompatibleManager;
import beyond_earth_giselle_addon.common.compat.mekanism.AddonMekanismCommand;
import beyond_earth_giselle_addon.common.registries.AddonEnchantments;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;
import net.mrscauthd.beyond_earth.common.capabilities.oxygen.OxygenUtil;
import net.mrscauthd.beyond_earth.common.registries.ItemsRegistry;

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
		source.sendSuccess(Component.literal("Equipped"), false);
		return 0;
	}

	public static class PlanetSelection
	{
		public static LiteralArgumentBuilder<CommandSourceStack> builder()
		{
			return Commands.literal("planetselection").requires(AddonCommand::isPlayerHasPermission2) //
					.executes(ctx -> PlanetSelection.execute(ctx, 4)) //
					.then(Commands.argument("tier", IntegerArgumentType.integer(1, 9999)) //
							.executes(ctx -> PlanetSelection.execute(ctx, IntegerArgumentType.getInteger(ctx, "tier"))));
		}

		private static int execute(CommandContext<CommandSourceStack> context, int tier) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();
			CompoundTag persistentData = player.getPersistentData();
			persistentData.putBoolean(BeyondEarthAddon.prl("planet_selection_menu_open").toString(), true);
			persistentData.putInt(BeyondEarthAddon.prl("rocket_tier").toString(), tier);
			persistentData.put(BeyondEarthAddon.prl("rocket_item_list").toString(), new ListTag());
			return 0;
		}

	}

	public static class Equip
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

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ItemsRegistry.SPACE_HELMET.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ItemsRegistry.SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ItemsRegistry.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ItemsRegistry.SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static int spacesuit2(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ItemsRegistry.NETHERITE_SPACE_HELMET.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ItemsRegistry.NETHERITE_SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ItemsRegistry.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ItemsRegistry.NETHERITE_SPACE_BOOTS.get()));

			return sendEquipedMessage(source);
		}

		public static ItemStack makeFull(ResourceLocation name, Enchantment... enchantments)
		{
			Item item = ForgeRegistries.ITEMS.getValue(name);
			return makeFull(item, enchantments);
		}

		private static ItemStack makeFull(Item item, Enchantment... enchantments)
		{
			ItemStack stack = new ItemStack(item);

			for (Enchantment enchantment : enchantments)
			{
				stack.enchant(enchantment, 1);
			}

			IEnergyStorage energyStorage = stack.getCapability(ForgeCapabilities.ENERGY).orElse(null);

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
