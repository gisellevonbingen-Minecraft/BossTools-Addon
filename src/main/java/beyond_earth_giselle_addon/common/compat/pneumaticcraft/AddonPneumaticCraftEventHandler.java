package beyond_earth_giselle_addon.common.compat.pneumaticcraft;

import javax.annotation.Nonnull;

import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.content.proof.LivingSpaceOxygenProofEvent;
import me.desht.pneumaticcraft.api.PNCCapabilities;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandler;
import me.desht.pneumaticcraft.api.tileentity.IAirHandlerItem;
import me.desht.pneumaticcraft.common.item.PneumaticArmorItem;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.events.forge.EntityGravityEvent;
import net.mrscauthd.beyond_earth.events.forge.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.beyond_earth.events.forge.LivingSetVenusRainEvent;

public class AddonPneumaticCraftEventHandler
{
	public static boolean testFullParts(LivingEntity living)
	{
		if (!AddonConfigs.Common.pneumaticcraft.upgradesWorkFullParts.get())
		{
			return true;
		}

		for (EquipmentSlot slot : EquipmentSlot.values())
		{
			if (slot.getType() == EquipmentSlot.Type.ARMOR)
			{
				ItemStack stack = living.getItemBySlot(slot);

				if (!(stack.getItem() instanceof PneumaticArmorItem))
				{
					return false;
				}

			}

		}

		return true;
	}

	@SubscribeEvent
	public static void onLivingSpaceOxygenProofEvent(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntityLiving();

		if (!(entity instanceof Player player))
		{
			return;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, AddonCommonUpgradeHandlers.SPACE_BREATHING);

		if (stack.isEmpty() == true || !testFullParts(player))
		{
			return;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		int airUsing = AddonConfigs.Common.pneumaticcraft.upgrade_space_breating_airUsing.get();

		if (airHandler != null && useAir(airHandler, airUsing, true) == true)
		{
			int oxygenUsing = 1;
			IOxygenCharger oxygenCharnger = OxygenChargerUtils.firstExtractableOxygenCharger(entity, oxygenUsing, stack);

			if (oxygenCharnger != null)
			{
				if (entity.level.isClientSide() == false)
				{
					oxygenCharnger.getOxygenStorage().extractOxygen(oxygenUsing, false);
					useAir(airHandler, airUsing, false);
				}

				e.setProofDuration(AddonConfigs.Common.pneumaticcraft.upgrade_space_breating_oxygenDuration.get());
			}

		}

	}

	@SubscribeEvent
	public static void onLivingGravityEvent(EntityGravityEvent e)
	{
		tryCancel(e, AddonCommonUpgradeHandlers.GRAVITY_NORMALIZING, AddonConfigs.Common.pneumaticcraft.upgrade_gravity_normalizing_airUsing.get());
	}

	@SubscribeEvent
	public static void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		tryCancel(e, AddonCommonUpgradeHandlers.SPACE_FIRE_PROOF, AddonConfigs.Common.pneumaticcraft.upgrade_space_fire_proof_airUsing.get(), true);
	}

	@SubscribeEvent
	public static void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		tryCancel(e, AddonCommonUpgradeHandlers.VENUS_ACID_PROOF, AddonConfigs.Common.pneumaticcraft.upgrade_venus_acid_proof_airUsing.get(), true);
	}

	public static boolean tryCancel(EntityEvent e, IArmorUpgradeHandler<?> upgradeHandler, int airUsing)
	{
		return tryCancel(e, upgradeHandler, airUsing, false);
	}

	public static boolean tryCancel(EntityEvent e, IArmorUpgradeHandler<?> upgradeHandler, int airUsing, boolean testFullParts)
	{
		if (e.isCancelable() == false || e.isCanceled() == true || !(e.getEntity() instanceof Player player))
		{
			return false;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, upgradeHandler);

		if (stack.isEmpty() == true || (testFullParts && !testFullParts(player)))
		{
			return false;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);

		if (airHandler != null && useAir(airHandler, airUsing, true) == true)
		{
			if (player.level.isClientSide() == false)
			{
				useAir(airHandler, airUsing, false);
			}

			e.setCanceled(true);
			return true;
		}
		else
		{
			return false;
		}

	}

	public static @Nonnull ItemStack getUpgradeUsablePneumaticArmorItem(Player player, IArmorUpgradeHandler<?> upgradeHandler)
	{
		ItemStack stack = player.getItemBySlot(upgradeHandler.getEquipmentSlot());

		if (stack.getItem() instanceof PneumaticArmorItem)
		{
			CommonArmorHandler commonHandler = CommonArmorHandler.getHandlerForPlayer(player);

			if (commonHandler.upgradeUsable(upgradeHandler, true) == true)
			{
				return stack;
			}

		}

		return ItemStack.EMPTY;
	}

	public static boolean useAir(IAirHandler airHandler, int airUsing, boolean simulate)
	{
		if (airHandler.getAir() >= airUsing)
		{
			if (simulate == false)
			{
				airHandler.addAir(-airUsing);
			}

			return true;
		}

		return false;
	}

}
