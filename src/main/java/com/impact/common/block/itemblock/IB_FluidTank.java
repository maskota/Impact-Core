package com.impact.common.block.itemblock;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

public class IB_FluidTank extends ItemBlock {

  public IB_FluidTank(Block block) {
    super(block);
  }

  @Override
  public int getMetadata(int meta) {
    return meta;
  }

  @Override
  public boolean getHasSubtypes() {
    return true;
  }

  @Override
  public String getUnlocalizedName(ItemStack stack) {
    return super.getUnlocalizedName() + "." + stack.getItemDamage();
  }

  @SuppressWarnings("unchecked")
  @Override
  public void addInformation(ItemStack stack, EntityPlayer player, List lines,
      boolean advancedTooltips) {
    lines.add("This is not a fluid tank");
    switch (stack.getItemDamage()) {
      case 0:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 16 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW
            + " 0.5 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 80 000 000L"
            + EnumChatFormatting.YELLOW + " 1 EU/t");
        break;
      case 1:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 32 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW + " 1 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 160 000 000L"
            + EnumChatFormatting.YELLOW + " 2 EU/t");
        break;
      case 2:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 64 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW + " 2 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 320 000 000L"
            + EnumChatFormatting.YELLOW + " 4 EU/t");
        break;
      case 3:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 128 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW + " 4 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 640 000 000L"
            + EnumChatFormatting.YELLOW + " 8 EU/t");
        break;
      case 4:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 256 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW + " 8 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 1 280 000 000L"
            + EnumChatFormatting.YELLOW + " 16 EU/t");
        break;
      case 5:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 512 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW
            + " 32 EU/t");
        lines.add("Capacity Single-Tank:" + EnumChatFormatting.GREEN + " 2 000 000 000L"
            + EnumChatFormatting.YELLOW + " 64 EU/t");
        break;
      case 6:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 1 024 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW
            + " 128 EU/t");
        lines.add(EnumChatFormatting.RED + "Single-Tank not used" + EnumChatFormatting.RESET);
        break;
      case 7:
        lines.add("Capacity Multi-Tank:" + EnumChatFormatting.GREEN
            + " 2 048 000 000L for 1 fluid (Total 25 fluid)" + EnumChatFormatting.YELLOW
            + " 512 EU/t");
        lines.add(EnumChatFormatting.RED + "Single-Tank not used" + EnumChatFormatting.RESET);
        break;
    }
  }
}
