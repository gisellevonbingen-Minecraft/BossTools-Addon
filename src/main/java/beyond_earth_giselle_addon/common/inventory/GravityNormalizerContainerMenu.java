package beyond_earth_giselle_addon.common.inventory;

import beyond_earth_giselle_addon.common.block.entity.GravityNormalizerBlockEntity;
import beyond_earth_giselle_addon.common.registries.AddonMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.mrscauthd.beyond_earth.guis.helper.ContainerHelper;

public class GravityNormalizerContainerMenu extends AbstractMachineContainerMenu<GravityNormalizerContainerMenu, GravityNormalizerBlockEntity>
{
	public GravityNormalizerContainerMenu(int windowId, Inventory inv, GravityNormalizerBlockEntity blockEntity)
	{
		super(AddonMenuTypes.GRAVITY_NORMALIZER.get(), windowId, inv, blockEntity);

		ContainerHelper.addInventorySlots(this, inv, 8, 84, this::addSlot);
	}

}
