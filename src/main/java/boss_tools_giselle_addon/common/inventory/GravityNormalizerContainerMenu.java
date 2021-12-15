package boss_tools_giselle_addon.common.inventory;

import boss_tools_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.boss_tools.gui.helper.ContainerHelper;

public class GravityNormalizerContainerMenu extends AbstractMachineContainerMenu<GravityNormalizerContainerMenu, GravityNormalizerBlockEntity>
{
	public GravityNormalizerContainerMenu(int windowId, Inventory inv, GravityNormalizerBlockEntity blockEntity)
	{
		super(AddonMenuTypes.GRAVITY_NORMALIZER.get(), windowId, inv, blockEntity);

		ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
	}

}
