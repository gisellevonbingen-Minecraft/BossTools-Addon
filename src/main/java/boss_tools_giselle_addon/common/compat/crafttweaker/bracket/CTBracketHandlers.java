package boss_tools_giselle_addon.common.compat.crafttweaker.bracket;

import org.openzen.zencode.java.ZenCodeType;

import com.blamejared.crafttweaker.api.annotations.BracketResolver;
import com.blamejared.crafttweaker.api.annotations.ZenRegister;

import boss_tools_giselle_addon.common.compat.crafttweaker.CTConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.mrscauthd.boss_tools.ModInnet;
import net.mrscauthd.boss_tools.crafting.RocketPart;

@ZenRegister
@ZenCodeType.Name(CTConstants.BRACKET_HANDLER)
public class CTBracketHandlers
{
	@ZenCodeType.Method
	@BracketResolver(CTConstants.BRACKET_ROCKET_PART)
	public static RocketPart getRocketPart(String name)
	{
		return findRegistryEntry(CTConstants.BRACKET_ROCKET_PART, name, ModInnet.ROCKET_PARTS_REGISTRY);
	}

	public static String toString(RocketPart part)
	{
		return CTBracketUtils.toBracketString(CTConstants.BRACKET_ROCKET_PART, part.getRegistryName());
	}

	private static <V extends IForgeRegistryEntry<V>> V findRegistryEntry(String bracket, String name, IForgeRegistry<V> registry)
	{
		ResourceLocation registryName = ResourceLocation.tryParse(name);

		if (registryName == null)
		{
			throw new IllegalArgumentException("Invalid name : " + name);
		}
		else if (registry.containsKey(registryName) == false)
		{
			throw new IllegalArgumentException("RegistryEntry not found by registryName :" + registryName);
		}
		else
		{
			return registry.getValue(registryName);
		}

	}

}
