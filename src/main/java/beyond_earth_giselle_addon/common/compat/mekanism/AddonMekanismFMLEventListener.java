package beyond_earth_giselle_addon.common.compat.mekanism;

import mekanism.api.MekanismIMC;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;

public class AddonMekanismFMLEventListener
{
	@SubscribeEvent
	public static void onInterModeEnqueue(InterModEnqueueEvent event)
	{
		String MODID = AddonMekanismCompat.MODID;
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_HELMET_MODULES, AddonMekanismModules.SPACE_BREATHING_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.SPACE_FIRE_PROOF_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BODYARMOR_MODULES, AddonMekanismModules.VENUS_ACID_PROOF_UNIT);
		InterModComms.sendTo(MODID, MekanismIMC.ADD_MEKA_SUIT_BOOTS_MODULES, AddonMekanismModules.GRAVITY_NORMALIZING_UNIT);
	}

	private AddonMekanismFMLEventListener()
	{

	}

}
