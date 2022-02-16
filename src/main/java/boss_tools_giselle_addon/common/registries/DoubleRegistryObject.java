package boss_tools_giselle_addon.common.registries;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class DoubleRegistryObject<P extends IForgeRegistryEntry<? super P>, S extends IForgeRegistryEntry<? super S>>
{
	private final ResourceLocation id;
	private final RegistryObject<? extends P> primary;
	private final RegistryObject<? extends S> secondary;

	public DoubleRegistryObject(RegistryObject<? extends P> primary, RegistryObject<? extends S> secondary)
	{
		this.id = primary.getId();
		this.primary = primary;
		this.secondary = secondary;
	}

	public ResourceLocation getId()
	{
		return this.id;
	}

	public P getPrimary()
	{
		return this.primary.get();
	}

	public S getSecondary()
	{
		return this.secondary.get();
	}

}
