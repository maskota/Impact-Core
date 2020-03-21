package com.impact.modChest.chestNt;

import com.impact.lib.Refstrings;
import com.impact.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestNt extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation(Refstrings.MODID, "textures/entity/chestNt.png");

	public TEChestNt() {
		super(64, 207);
	}

	@Override
	public final String getInventoryName()
	{
		return "Neutronium Chest";
	}

	@Override
	public int invSize(){
		return 207;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}