package beyond_earth_giselle_addon.common.block.entity;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import beyond_earth_giselle_addon.common.config.AddonConfigs;
import beyond_earth_giselle_addon.common.inventory.AdvancedCompressorContainerMenu;
import beyond_earth_giselle_addon.common.registries.AddonBlockEntityTypes;
import beyond_earth_giselle_addon.common.registries.AddonRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.PowerSystemEnergyCommon;
import net.mrscauthd.beyond_earth.common.blocks.entities.machines.power.PowerSystemRegistry;
import net.mrscauthd.beyond_earth.common.capabilities.energy.EnergyStorageBasic;
import net.mrscauthd.beyond_earth.common.data.recipes.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.common.registries.ItemsRegistry;
import net.mrscauthd.beyond_earth.common.registries.RecipeTypeRegistry;

public class AdvancedCompressorBlockEntity extends ItemStackToItemStackBlockEntityMultiRecipe
{
	public static final String KEY_MODE = "mode";

	public AdvancedCompressorBlockEntity(BlockPos pos, BlockState state)
	{
		super(AddonBlockEntityTypes.ADVANCED_COMPRESSOR.get(), pos, state);
	}

	@Override
	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry)
	{
		super.createEnergyStorages(registry);
		int capacity = AddonConfigs.Common.machines.advancedCompressor_energyCapcity.get();
		int transfer = AddonConfigs.Common.machines.advancedCompressor_energyTransfer.get();
		registry.put(new EnergyStorageBasic(this, capacity, transfer, capacity));
	}

	@Override
	protected void createPowerSystems(PowerSystemRegistry map)
	{
		super.createPowerSystems(map);
		map.put(new PowerSystemEnergyCommon(this)
		{
			@Override
			public int getBasePowerForOperation()
			{
				return AdvancedCompressorBlockEntity.this.getBasePowerForOperation();
			}
		});
	}

	@Override
	@Nullable
	public AbstractContainerMenu createMenu(int windowId, Inventory inv)
	{
		return new AdvancedCompressorContainerMenu(windowId, inv, this);
	}

	public int getBasePowerForOperation()
	{
		return 1;
	}

	@Override
	public List<RecipeType<? extends Recipe<Container>>> getRecipeTypes()
	{
		List<RecipeType<? extends Recipe<Container>>> list = super.getRecipeTypes();
		list.add(this.getMode().getRecipeType());
		return list;
	}

	public List<ICompressorMode> getAvailableModes()
	{
		return Lists.newArrayList(CompressorMode.values());
	}

	public ICompressorMode findMode(ResourceLocation key)
	{
		RecipeType<?> recipeType = ForgeRegistries.RECIPE_TYPES.getValue(key);

		List<ICompressorMode> availableModes = this.getAvailableModes();
		ICompressorMode mode = availableModes.stream().filter(m -> m.getRecipeType() == recipeType).findFirst().orElse(null);
		return mode == null ? availableModes.get(0) : mode;
	}

	public ICompressorMode getMode()
	{
		String key = this.getPersistentData().getString(KEY_MODE);
		return this.findMode(new ResourceLocation(key));
	}

	public void setMode(ICompressorMode mode)
	{
		if (mode == null || this.getMode() == mode)
		{
			return;
		}

		ResourceLocation key = mode.getRecipeTypeKey();

		this.getPersistentData().putString(KEY_MODE, key.toString());
		this.resetTimer();
		this.clearRecipeCache();
		this.setChanged();
	}

	public ICompressorMode getCyclicMode(int direction)
	{
		List<ICompressorMode> modes = this.getAvailableModes();
		int size = modes.size();

		int nextIndex = (((modes.indexOf(this.getMode()) + direction) % size) + size) % size;
		ICompressorMode nextMode = modes.get(nextIndex);
		return nextMode;
	}

	public ICompressorMode getNextMode()
	{
		return this.getCyclicMode(+1);
	}

	public ICompressorMode getPrevMode()
	{
		return this.getCyclicMode(-1);
	}

	public interface ICompressorMode
	{
		public ItemStack getIcon();

		public ItemStackToItemStackRecipeType<?> getRecipeType();

		public default Component getText()
		{
			ResourceLocation key = this.getRecipeTypeKey();
			return Component.translatable("compressormode." + key.getNamespace() + "." + key.getPath());
		}

		public default ResourceLocation getRecipeTypeKey()
		{
			return ForgeRegistries.RECIPE_TYPES.getKey(this.getRecipeType());
		}

	}

	public enum CompressorMode implements ICompressorMode
	{
		COMPRESSING(RecipeTypeRegistry.COMPRESSING, () -> new ItemStack(ItemsRegistry.COMPRESSED_STEEL.get())),
		ROLLING(AddonRecipes.ROLLING, () -> new ItemStack(ItemsRegistry.IRON_PLATE.get())),
		EXTRUDING(AddonRecipes.EXTRUDING, () -> new ItemStack(ItemsRegistry.IRON_ROD.get())),
		// EOL
		;

		private final RegistryObject<? extends ItemStackToItemStackRecipeType<?>> recipeType;
		private final Supplier<ItemStack> iconSupplier;
		private ItemStack icon;

		private CompressorMode(RegistryObject<? extends ItemStackToItemStackRecipeType<?>> recipeType, Supplier<ItemStack> iconSupplier)
		{
			this.recipeType = recipeType;
			this.iconSupplier = iconSupplier;
		}

		@Override
		public ItemStackToItemStackRecipeType<?> getRecipeType()
		{
			return this.recipeType.get();
		}

		protected ItemStack createIcon()
		{
			return this.iconSupplier.get();
		}

		@Override
		public ItemStack getIcon()
		{
			if (this.icon == null)
			{
				this.icon = this.createIcon();
			}

			return this.icon;
		}

	}

}
