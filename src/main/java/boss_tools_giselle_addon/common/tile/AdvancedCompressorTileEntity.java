package boss_tools_giselle_addon.common.tile;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import boss_tools_giselle_addon.common.block.ElectricBlastFurnaceBlock;
import boss_tools_giselle_addon.common.inventory.container.AdvancedCompressorContainer;
import boss_tools_giselle_addon.common.registries.AddonRecipes;
import boss_tools_giselle_addon.common.registries.AddonTileEntitTypes;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.state.BooleanProperty;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.BossToolsRecipeTypes;
import net.mrscauthd.boss_tools.crafting.ItemStackToItemStackRecipeType;
import net.mrscauthd.boss_tools.machines.tile.ItemStackToItemStackTileEntity;
import net.mrscauthd.boss_tools.machines.tile.NamedComponentRegistry;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemEnergyCommon;
import net.mrscauthd.boss_tools.machines.tile.PowerSystemRegistry;

public class AdvancedCompressorTileEntity extends ItemStackToItemStackTileEntity
{
	public static final String KEY_MODE = "mode";

	public AdvancedCompressorTileEntity()
	{
		super(AddonTileEntitTypes.ADVANCED_COMPRESSOR.get());
	}

	protected void createEnergyStorages(NamedComponentRegistry<IEnergyStorage> registry)
	{
		super.createEnergyStorages(registry);
		registry.put(this.createEnergyStorageCommon());
	}

	protected void createPowerSystems(PowerSystemRegistry map)
	{
		super.createPowerSystems(map);
		map.put(new PowerSystemEnergyCommon(this)
		{
			public int getBasePowerForOperation()
			{
				return AdvancedCompressorTileEntity.this.getBasePowerForOperation();
			}
		});
	}

	@Override
	@Nullable
	public Container createMenu(int windowId, PlayerInventory inv)
	{
		return new AdvancedCompressorContainer(windowId, inv, this);
	}

	public int getBasePowerForOperation()
	{
		return 1;
	}

	protected BooleanProperty getBlockActivatedProperty()
	{
		return ElectricBlastFurnaceBlock.LIT;
	}

	public ItemStackToItemStackRecipeType<?> getRecipeType()
	{
		return this.getMode().getRecipeType();
	}

	public List<ICompressorMode> getAvailableModes()
	{
		return Lists.newArrayList(CompressorMode.values());
	}

	public ICompressorMode findMode(ResourceLocation key)
	{
		IRecipeType<?> recipeType = Registry.RECIPE_TYPE.get(key);

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
		ItemStackToItemStackTileEntityUtils.getItemStackCacher(this).set(ItemStack.EMPTY);
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

		public default ITextComponent getText()
		{
			ResourceLocation key = this.getRecipeTypeKey();
			return new TranslationTextComponent("compressormode." + key.getNamespace() + "." + key.getPath());
		}

		public default ResourceLocation getRecipeTypeKey()
		{
			return Registry.RECIPE_TYPE.getKey(this.getRecipeType());
		}

	}

	public enum CompressorMode implements ICompressorMode
	{
		COMPRESSING(BossToolsRecipeTypes.COMPRESSING, () -> new ItemStack(ModInnet.COMPRESSED_STEEL.get())),
		ROLLING(AddonRecipes.ROLLING, () -> new ItemStack(ModInnet.IRON_PLATE.get())),
		EXTRUDING(AddonRecipes.EXTRUDING, () -> new ItemStack(ModInnet.IRON_STICK.get())),
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
