package beyond_earth_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers;

import com.mojang.blaze3d.vertex.PoseStack;

import beyond_earth_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options.SpaceBreathingOption;
import beyond_earth_giselle_addon.common.capability.ChargeMode;
import beyond_earth_giselle_addon.common.capability.IOxygenCharger;
import beyond_earth_giselle_addon.common.capability.OxygenChargerUtils;
import beyond_earth_giselle_addon.common.compat.pneumaticcraft.AddonPNCUpgrade;
import beyond_earth_giselle_addon.common.compat.pneumaticcraft.AddonPneumaticCraftUpgrades;
import beyond_earth_giselle_addon.common.registries.AddonItems;
import me.desht.pneumaticcraft.api.client.IGuiAnimatedStat;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.StatPanelLayout;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.PneumaticHelmetRegistry;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.mrscauthd.beyond_earth.capabilities.oxygen.IOxygenStorage;
import net.mrscauthd.beyond_earth.gauge.GaugeTextHelper;
import net.mrscauthd.beyond_earth.gauge.IGaugeValue;

public class SpaceBreathingClientHandler<T extends IArmorUpgradeHandler<?>> extends AddonSimpleToggleableHandler<T>
{
	private static final StatPanelLayout DEFAULT_STAT_LAYOUT = new StatPanelLayout(0.5F, 0.005F, false);
	private static final ItemStack ICON_EMPTY = new ItemStack(AddonItems.OXYGEN_CAN.get());
	private static final ItemStack ICON_FULL = new ItemStack(AddonItems.OXYGEN_CAN.get());

	static
	{
		IOxygenCharger oxygenCharger = OxygenChargerUtils.getOxygenCharger(ICON_FULL);
		oxygenCharger.setChargeMode(ChargeMode.ALL);

		IOxygenStorage oxygenStorage = oxygenCharger.getOxygenStorage();
		oxygenStorage.setOxygenStored(oxygenStorage.getMaxOxygenStored());
	}

	private IGuiAnimatedStat stat;
	private Component oxygenComponent;
	private boolean oxygenEmpty;

	public SpaceBreathingClientHandler(T commonHandler)
	{
		super(commonHandler);
	}

	@Override
	public IOptionPage getGuiOptionsPage(IGuiScreen screen)
	{
		return new SpaceBreathingOption<>(screen, this);
	}

	@Override
	public void tickClient(ICommonArmorHandler armorHandler, boolean isEnabled)
	{
		super.tickClient(armorHandler, isEnabled);

		if (isEnabled == true && this.stat.isStatOpen() == true)
		{
			Player player = armorHandler.getPlayer();
			IGaugeValue oxygenGauge = OxygenChargerUtils.getInventoryOxygenChargerStorage(player);
			this.oxygenComponent = GaugeTextHelper.getPercentText(oxygenGauge).build();
			this.oxygenEmpty = oxygenGauge.getAmount() == 0;
		}
		else
		{
			this.oxygenComponent = null;
			this.oxygenEmpty = true;
		}

	}

	@Override
	public void render2D(PoseStack matrixStack, float partialTicks, boolean armorPieceHasPressure)
	{
		super.render2D(matrixStack, partialTicks, armorPieceHasPressure);

		if (this.stat != null && this.oxygenComponent != null)
		{
			Minecraft instance = Minecraft.getInstance();
			Font fr = instance.font;
			int xl = this.stat.getBaseX() + 4;
			int yt = this.stat.getBaseY() + 16;

			if (this.stat.isLeftSided() == true)
			{
				xl -= stat.getStatWidth();
			}

			int iconWidth = 16;
			int iconX = xl;
			instance.getItemRenderer().renderGuiItem(this.oxygenEmpty ? ICON_EMPTY : ICON_FULL, iconX, yt);

			int componentX = iconX + iconWidth + 2;
			int componentY = yt + 4;
			fr.drawShadow(matrixStack, this.oxygenComponent, componentX, componentY, 0xFFFFFF);
		}

	}

	@Override
	public IGuiAnimatedStat getAnimatedStat()
	{
		if (this.stat == null)
		{
			AddonPNCUpgrade upgrade = AddonPneumaticCraftUpgrades.SPACE_BREATHING.get();
			CommonArmorHandler handler = CommonArmorHandler.getHandlerForPlayer();
			int tier = Math.max(1, handler.getUpgradeCount(this.getCommonHandler().getEquipmentSlot(), upgrade));
			ItemStack stack = new ItemStack(upgrade.getItem(tier));

			this.stat = PneumaticHelmetRegistry.getInstance().makeHUDStatPanel(new TranslatableComponent(this.getCommonHandler().getTranslationKey()), stack, this);
			this.stat.setMinimumContractedDimensions(0, 0);
			this.stat.setMinimumExpandedDimensions(120, 30);
		}

		return this.stat;
	}

	@Override
	public void onResolutionChanged()
	{
		this.stat = null;
	}

	@Override
	public StatPanelLayout getDefaultStatLayout()
	{
		return DEFAULT_STAT_LAYOUT;
	}

}
