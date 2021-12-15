package boss_tools_giselle_addon.common.capability;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface IChargeModeHandler
{
	public default List<IChargeMode> getAvailableChargeModes()
	{
		return Arrays.asList(ChargeMode.values());
	}

	public void setChargeMode(@Nullable IChargeMode mode);

	@Nonnull
	public IChargeMode getChargeMode();

}
