package boss_tools_giselle_addon.common.util;

import java.util.UUID;

import javax.annotation.Nullable;

import com.mojang.authlib.GameProfile;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraftforge.common.util.Constants.NBT;

public class SkullUtils
{
	public static final String SKULL_OWNER = "SkullOwner";

	@Nullable
	public static GameProfile getGameProfile(CompoundNBT compound)
	{
		if (compound == null)
		{
			return null;
		}
		else if (compound.contains(SKULL_OWNER, NBT.TAG_COMPOUND) == true)
		{
			return NBTUtil.readGameProfile(compound.getCompound(SKULL_OWNER));
		}
		else if (compound.contains(SKULL_OWNER, NBT.TAG_STRING) == true)
		{
			return SkullTileEntity.updateGameprofile(new GameProfile((UUID) null, compound.getString(SKULL_OWNER)));
		}

		return null;
	}

	private SkullUtils()
	{

	}

}
