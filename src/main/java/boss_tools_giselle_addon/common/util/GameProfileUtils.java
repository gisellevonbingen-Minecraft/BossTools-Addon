package boss_tools_giselle_addon.common.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.minecraft.tileentity.SkullTileEntity;

public class GameProfileUtils
{
	public static final String WHITELIST_HOST = "textures.minecraft.net";

	public static String toWhitelistUrl(String hash)
	{
		return "http://" + WHITELIST_HOST + "/texture/" + hash;
	}

	public static GameProfile fillTextures(GameProfile gameProfile, String input)
	{
		try
		{
			URL url = new URL(input);

			if (url.getHost().equalsIgnoreCase(WHITELIST_HOST) == true)
			{
				return fillTexturesFromUrl(gameProfile, input);
			}
			else
			{
				return gameProfile;
			}

		}
		catch (MalformedURLException e)
		{
			try
			{
				String json = new String(Base64.getDecoder().decode(input));
				new Gson().fromJson(json, JsonObject.class);
				return fillTexturesFromBase64(gameProfile, input);
			}
			catch (Exception e2)
			{
				GameProfile fromNickname = SkullTileEntity.updateGameprofile(gameProfile);

				if (fromNickname.getProperties().size() > 0)
				{
					return fromNickname;
				}
				else
				{
					return fillTexturesFromUrl(gameProfile, toWhitelistUrl(input));
				}

			}

		}

	}

	public static GameProfile fillTexturesFromUrl(GameProfile gameProfile, String url)
	{
		String str = "{\"textures\":{\"SKIN\":{\"url\":\"" + url + "\"}}}";
		String base64 = Base64.getEncoder().encodeToString(str.getBytes());
		return fillTexturesFromBase64(gameProfile, base64);
	}

	public static GameProfile fillTexturesFromBase64(GameProfile gameProfile, String base64)
	{
		Property property = new Property("textures", base64);
		gameProfile.getProperties().put("textures", property);

		return gameProfile;
	}

	private GameProfileUtils()
	{

	}

}
