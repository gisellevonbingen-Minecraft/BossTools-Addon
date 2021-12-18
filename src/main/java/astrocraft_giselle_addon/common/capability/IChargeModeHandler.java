package astrocraft_giselle_addon.common.capability;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface IChargeModeHandler extends INBTSerializable<CompoundTag>
{
	public default List<IChargeMode> getAvailableChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	@Nonnull
	public IChargeMode getChargeMode();

	public void setChargeMode(@Nullable IChargeMode mode);

	public void setChanged();

}
