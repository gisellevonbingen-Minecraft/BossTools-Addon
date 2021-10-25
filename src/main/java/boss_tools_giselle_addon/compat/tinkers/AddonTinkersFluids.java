package boss_tools_giselle_addon.compat.tinkers;

import boss_tools_giselle_addon.BossToolsAddon;
import net.minecraft.block.material.Material;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.common.registration.FluidDeferredRegisterExtension;

public class AddonTinkersFluids
{
	protected static final FluidDeferredRegisterExtension FLUIDS = new FluidDeferredRegisterExtension(BossToolsAddon.MODID);
	public static final FluidObject<ForgeFlowingFluid> moltenDesh = FLUIDS.register("molten_desh", hotBuilder().temperature(800), Material.LAVA, 12);
	public static final FluidObject<ForgeFlowingFluid> moltenSilicon = FLUIDS.register("molten_silicon", hotBuilder().temperature(800), Material.LAVA, 12);

	/** Creates a builder for a hot fluid */
	private static FluidAttributes.Builder hotBuilder()
	{
		return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA);
	}

}
