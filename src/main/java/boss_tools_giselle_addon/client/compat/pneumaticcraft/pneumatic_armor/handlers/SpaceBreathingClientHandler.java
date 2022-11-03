package boss_tools_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.handlers;

import com.mojang.blaze3d.matrix.MatrixStack;

import boss_tools_giselle_addon.client.compat.pneumaticcraft.pneumatic_armor.options.SpaceBreathingOption;
import boss_tools_giselle_addon.common.capability.ChargeMode;
import boss_tools_giselle_addon.common.capability.IOxygenCharger;
import boss_tools_giselle_addon.common.capability.OxygenChargerUtils;
import boss_tools_giselle_addon.common.compat.pneumaticcraft.AddonEnumUpgrade;
import boss_tools_giselle_addon.common.registries.AddonItems;
import me.desht.pneumaticcraft.api.client.IGuiAnimatedStat;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IGuiScreen;
import me.desht.pneumaticcraft.api.client.pneumatic_helmet.IOptionPage;
import me.desht.pneumaticcraft.api.item.EnumUpgrade;
import me.desht.pneumaticcraft.api.pneumatic_armor.IArmorUpgradeHandler;
import me.desht.pneumaticcraft.api.pneumatic_armor.ICommonArmorHandler;
import me.desht.pneumaticcraft.client.gui.widget.WidgetAnimatedStat;
import me.desht.pneumaticcraft.client.render.pneumatic_armor.HUDHandler;
import me.desht.pneumaticcraft.common.config.subconfig.BossToolsAddonArmorHUDLayout;
import me.desht.pneumaticcraft.common.pneumatic_armor.CommonArmorHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mrscauthd.boss_tools.capability.IOxygenStorage;
import net.mrscauthd.boss_tools.gauge.GaugeTextHelper;
import net.mrscauthd.boss_tools.gauge.IGaugeValue;

public class SpaceBreathingClientHandler<T extends IArmorUpgradeHandler<?>> extends AddonSimpleToggleableHandler<T>
{
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
	private ITextComponent oxygenComponent;
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
	public void tickClient(ICommonArmorHandler armorHandler)
	{
		super.tickClient(armorHandler);

		if (this.stat.isStatOpen() == true)
		{
			PlayerEntity player = armorHandler.getPlayer();
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
	public void render2D(MatrixStack matrixStack, float partialTicks, boolean armorPieceHasPressure)
	{
		super.render2D(matrixStack, partialTicks, armorPieceHasPressure);

		if (this.stat != null && this.oxygenComponent != null)
		{
			Minecraft instance = Minecraft.getInstance();
			FontRenderer fr = instance.font;
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
			EnumUpgrade upgrade = AddonEnumUpgrade.SPACE_BREATHING.get();
			CommonArmorHandler handler = CommonArmorHandler.getHandlerForPlayer();
			int tier = Math.max(1, handler.getUpgradeCount(this.getCommonHandler().getEquipmentSlot(), upgrade));
			ItemStack stack = new ItemStack(upgrade.getItem(tier));

			this.stat = new WidgetAnimatedStat(null, new TranslationTextComponent(this.getCommonHandler().getTranslationKey()), WidgetAnimatedStat.StatIcon.of(stack), HUDHandler.getInstance().getStatOverlayColor(), null, BossToolsAddonArmorHUDLayout.INSTANCE.spaceBreathingStat);
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

}
