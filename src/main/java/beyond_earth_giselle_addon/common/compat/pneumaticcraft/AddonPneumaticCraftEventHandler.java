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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mrscauthd.beyond_earth.common.events.forge.LivingSetFireInHotPlanetEvent;
import net.mrscauthd.beyond_earth.common.events.forge.LivingSetVenusRainEvent;

public class AddonPneumaticCraftEventHandler
{
	@SubscribeEvent
	public static void onLivingSpaceOxygenProofEvent(LivingSpaceOxygenProofEvent e)
	{
		LivingEntity entity = e.getEntity();

		if (!(entity instanceof Player player))
		{
			return;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, AddonCommonUpgradeHandlers.SPACE_BREATHING);

		if (stack.isEmpty() == true)
		{
			return;
		}

		IAirHandlerItem airHandler = stack.getCapability(PNCCapabilities.AIR_HANDLER_ITEM_CAPABILITY).orElse(null);
		int airUsing = AddonConfigs.Common.pneumaticcraft.upgrade_space_breating_airUsing.get();

		if (airHandler != null && useAir(airHandler, airUsing, true) == true)
		{
			int oyxgenUsing = AddonConfigs.Common.pneumaticcraft.upgrade_space_breating_oxygenDuration.get();
			IOxygenCharger oxygenCharnger = OxygenChargerUtils.firstExtractableOxygenCharger(entity, oyxgenUsing, stack);

			if (oxygenCharnger != null)
			{
				if (entity.level.isClientSide() == false)
				{
					oxygenCharnger.getOxygenStorage().extractOxygen(oyxgenUsing, false);
					useAir(airHandler, airUsing, false);
				}

				e.setProofDuration(4);
			}

		}

	}

	@SubscribeEvent
	public static void onLivingSetFireInHotPlanet(LivingSetFireInHotPlanetEvent e)
	{
		tryCancel(e, AddonCommonUpgradeHandlers.SPACE_FIRE_PROOF, AddonConfigs.Common.pneumaticcraft.upgrade_space_fire_proof_airUsing.get());
	}

	@SubscribeEvent
	public static void onLivingSetVenusRain(LivingSetVenusRainEvent e)
	{
		tryCancel(e, AddonCommonUpgradeHandlers.VENUS_ACID_PROOF, AddonConfigs.Common.pneumaticcraft.upgrade_venus_acid_proof_airUsing.get());
	}

	public static boolean tryCancel(EntityEvent e, IArmorUpgradeHandler<?> upgradeHandler, int airUsing)
	{
		if (e.isCancelable() == false || e.isCanceled() == true || !(e.getEntity() instanceof Player player))
		{
			return false;
		}

		ItemStack stack = getUpgradeUsablePneumaticArmorItem(player, upgradeHandler);

		if (stack.isEmpty() == true)
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
