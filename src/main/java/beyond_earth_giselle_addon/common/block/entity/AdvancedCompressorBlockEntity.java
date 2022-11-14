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
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.beyond_earth.capabilities.energy.EnergyStorageBasic;
import net.mrscauthd.beyond_earth.crafting.BeyondEarthRecipeTypes;
import net.mrscauthd.beyond_earth.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.beyond_earth.machines.tile.NamedComponentRegistry;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.beyond_earth.machines.tile.PowerSystemRegistry;
import net.mrscauthd.beyond_earth.registries.ItemsRegistry;

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
		return AddonConfigs.Common.machines.advancedCompressor_energyUsing.get();
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
		RecipeType<?> recipeType = Registry.RECIPE_TYPE.get(key);

		List<ICompressorMode> availableModes = this.getAvailableModes();
		ICompressorMode mode = availableModes.stream().filter(m -> m.getRecipeType() == recipeType).findFirst().orElse(null);
		return mode == null ? availableModes.get(0) : mode;
	}

	public ICompressorMode getMode()
	{
		String key = this.getTileData().getString(KEY_MODE);
		return this.findMode(new ResourceLocation(key));
	}

	public void setMode(ICompressorMode mode)
	{
		if (mode == null || this.getMode() == mode)
		{
			return;
		}

		ResourceLocation key = mode.getRecipeTypeKey();

		this.getTileData().putString(KEY_MODE, key.toString());
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
			return new TranslatableComponent("compressormode." + key.getNamespace() + "." + key.getPath());
		}

		public default ResourceLocation getRecipeTypeKey()
		{
			return Registry.RECIPE_TYPE.getKey(this.getRecipeType());
		}

	}

	public enum CompressorMode implements ICompressorMode
	{
		COMPRESSING(BeyondEarthRecipeTypes.COMPRESSING, () -> new ItemStack(ItemsRegistry.COMPRESSED_STEEL.get())),
		ROLLING(AddonRecipes.ROLLING, () -> new ItemStack(ItemsRegistry.IRON_PLATE.get())),
		EXTRUDING(AddonRecipes.EXTRUDING, () -> new ItemStack(ItemsRegistry.IRON_STICK.get())),
		// EOL
		;

		private final ItemStackToItemStackRecipeType<?> recipeType;
		private final Supplier<ItemStack> iconSupplier;
		private ItemStack icon;

		private CompressorMode(ItemStackToItemStackRecipeType<?> recipeType, Supplier<ItemStack> iconSupplier)
		{
			this.recipeType = recipeType;
			this.iconSupplier = iconSupplier;
		}

		@Override
		public ItemStackToItemStackRecipeType<?> getRecipeType()
		{
			return this.recipeType;
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
