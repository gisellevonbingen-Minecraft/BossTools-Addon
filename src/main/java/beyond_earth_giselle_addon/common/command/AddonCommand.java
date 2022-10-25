package beyond_earth_giselle_addon.common.command;

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
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;
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

	public static class PlanetSelection
	{
		public static LiteralArgumentBuilder<CommandSourceStack> builder()
		{
			return Commands.literal("planetselection").requires(AddonCommand::isPlayerHasPermission2) //
					.executes(ctx -> PlanetSelection.execute(ctx, EntitiesRegistry.TIER_4_ROCKET.get())) //
					.then(Commands.literal("1").executes(ctx -> PlanetSelection.execute(ctx, EntitiesRegistry.TIER_1_ROCKET.get()))) //
					.then(Commands.literal("2").executes(ctx -> PlanetSelection.execute(ctx, EntitiesRegistry.TIER_2_ROCKET.get()))) //
					.then(Commands.literal("3").executes(ctx -> PlanetSelection.execute(ctx, EntitiesRegistry.TIER_3_ROCKET.get()))) //
					.then(Commands.literal("4").executes(ctx -> PlanetSelection.execute(ctx, EntitiesRegistry.TIER_4_ROCKET.get()))) //
			;
		}

		public static int execute(CommandContext<CommandSourceStack> context, EntityType<?> entityType) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();
			CompoundTag persistentData = player.getPersistentData();
			persistentData.putBoolean(BeyondEarthAddon.prl("planet_selection_gui_open").toString(), true);
			persistentData.putString(BeyondEarthAddon.prl("rocket_type").toString(), entityType.toString());
			persistentData.putString(BeyondEarthAddon.prl("slot0").toString(), Items.AIR.getRegistryName().toString());

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

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ItemsRegistry.OXYGEN_MASK.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ItemsRegistry.SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ItemsRegistry.SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ItemsRegistry.SPACE_BOOTS.get(), AddonEnchantments.GRAVITY_NORMALIZING.get()));

			return sendEquipedMessage(source);
		}

		public static int spacesuit2(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
		{
			CommandSourceStack source = context.getSource();
			ServerPlayer player = source.getPlayerOrException();

			player.setItemSlot(EquipmentSlot.HEAD, makeFull(ItemsRegistry.NETHERITE_OXYGEN_MASK.get(), AddonEnchantments.SPACE_BREATHING.get()));
			player.setItemSlot(EquipmentSlot.CHEST, makeFull(ItemsRegistry.NETHERITE_SPACE_SUIT.get(), AddonEnchantments.SPACE_FIRE_PROOF.get(), AddonEnchantments.VENUS_ACID_PROOF.get()));
			player.setItemSlot(EquipmentSlot.LEGS, makeFull(ItemsRegistry.NETHERITE_SPACE_PANTS.get()));
			player.setItemSlot(EquipmentSlot.FEET, makeFull(ItemsRegistry.NETHERITE_SPACE_BOOTS.get(), AddonEnchantments.GRAVITY_NORMALIZING.get()));

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
