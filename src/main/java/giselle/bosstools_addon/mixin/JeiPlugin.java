package giselle.bosstools_addon.mixin;

import java.util.ArrayList;
import java.util.List;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import giselle.bosstools_addon.config.AddonConfigs;
import net.mrscauthd.boss_tools.JeiPlugin.BlastingFurnaceJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.CompressorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.FuelMaker2JeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.FuelMakerJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.GeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenGeneratorJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.OxygenMachineJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.RoverJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.Tier1RocketItemItemJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.Tier2RocketItemItemJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.Tier3RocketItemItemJeiCategory;
import net.mrscauthd.boss_tools.JeiPlugin.WorkbenchJeiCategory;

@Mixin(net.mrscauthd.boss_tools.JeiPlugin.class)
public abstract class JeiPlugin
{
	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateBlastingFurnaceRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateBlastingFurnaceRecipes(CallbackInfoReturnable<List<BlastingFurnaceJeiCategory.BlastingFurnaceRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideBlastFurnaceRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateCompressorRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateCompressorRecipes(CallbackInfoReturnable<List<CompressorJeiCategory.CompressorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCompressorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateCompressorRecipes2()Ljava/util/List;", cancellable = true, remap = false)
	private void generateCompressorRecipes2(CallbackInfoReturnable<List<CompressorJeiCategory.CompressorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCompressorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateCompressorRecipes3()Ljava/util/List;", cancellable = true, remap = false)
	private void generateCompressorRecipes3(CallbackInfoReturnable<List<CompressorJeiCategory.CompressorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCompressorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateFuelMakerRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateFuelMakerRecipes(CallbackInfoReturnable<List<FuelMakerJeiCategory.FuelMakerRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideFuelRefineryRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateFuelMakerRecipes2()Ljava/util/List;", cancellable = true, remap = false)
	private void generateFuelMakerRecipes2(CallbackInfoReturnable<List<FuelMaker2JeiCategory.FuelMakerRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideFuelRefineryRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateGeneratorRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateGeneratorRecipes(CallbackInfoReturnable<List<GeneratorJeiCategory.GeneratorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCoalGeneratorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateGeneratorRecipes2()Ljava/util/List;", cancellable = true, remap = false)
	private void generateGeneratorRecipes2(CallbackInfoReturnable<List<GeneratorJeiCategory.GeneratorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCoalGeneratorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateGeneratorRecipes3()Ljava/util/List;", cancellable = true, remap = false)
	private void generateGeneratorRecipes3(CallbackInfoReturnable<List<GeneratorJeiCategory.GeneratorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideCoalGeneratorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateOxygenGeneratorRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateOxygenGeneratorRecipes(CallbackInfoReturnable<List<OxygenGeneratorJeiCategory.OxygenGeneratorRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideOxygenGeneratorRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateOxygenMachineRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateOxygenMachineRecipes(CallbackInfoReturnable<List<OxygenMachineJeiCategory.OxygenMachineRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideOxygenLoaderRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateOxygenMachineRecipes2()Ljava/util/List;", cancellable = true, remap = false)
	private void generateOxygenMachineRecipes2(CallbackInfoReturnable<List<OxygenMachineJeiCategory.OxygenMachineRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideOxygenLoaderRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateRoverRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateRoverRecipes(CallbackInfoReturnable<List<RoverJeiCategory.RoverRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideRoverRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateTier1RocketItemItemRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateTier1RocketItemItemRecipes(CallbackInfoReturnable<List<Tier1RocketItemItemJeiCategory.Tier1RocketItemItemRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideRocketTier1Recipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateTier2RocketItemItemRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateTier2RocketItemItemRecipes(CallbackInfoReturnable<List<Tier2RocketItemItemJeiCategory.Tier2RocketItemItemRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideRocketTier2Recipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateTier3RocketItemItemRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateTier3RocketItemItemRecipes(CallbackInfoReturnable<List<Tier3RocketItemItemJeiCategory.Tier3RocketItemItemRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideRocketTier3Recipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateWorkbenchRecipes()Ljava/util/List;", cancellable = true, remap = false)
	private void generateWorkbenchRecipes(CallbackInfoReturnable<List<WorkbenchJeiCategory.WorkbenchRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideNasaWorkbenchRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateWorkbenchRecipes2()Ljava/util/List;", cancellable = true, remap = false)
	private void generateWorkbenchRecipes2(CallbackInfoReturnable<List<WorkbenchJeiCategory.WorkbenchRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideNasaWorkbenchRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

	@Inject(at = @At(value = "HEAD"), method = "Lnet/mrscauthd/boss_tools/JeiPlugin;generateWorkbenchRecipes3()Ljava/util/List;", cancellable = true, remap = false)
	private void generateWorkbenchRecipes3(CallbackInfoReturnable<List<WorkbenchJeiCategory.WorkbenchRecipeWrapper>> info)
	{
		if (AddonConfigs.Common.hideNasaWorkbenchRecipes.get() == true)
		{
			info.setReturnValue(new ArrayList<>());
		}

	}

}
