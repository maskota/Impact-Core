package com.impact.mods.gregtech.tileentities.multi.processing.parallel;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.base.GUI_BASE;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Recipe;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.mods.gregtech.blocks.Casing_Helper.sCaseCore1;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_Centrifuge extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_Centrifuge> {
	
	public static String mModed;
	Block CASING = Casing_Helper.sCaseCore1;
	byte CASING_META = 7;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META];
	int CASING_TEXTURE_ID = CASING_META + 128 * 3;
	static IStructureDefinition<GTMTE_Centrifuge> definition =
			StructureDefinition.<GTMTE_Centrifuge>builder()
					.addShapeOldApi("main", new String[][]{
							{"00000", ".101.", ".101.", "00.00",},
							{"00000", "1AAA1", "1AAA1", "00000",},
							{"00000", "0A.A0", "0A.A0", "00000",},
							{"00000", "1AAA1", "1AAA1", "00000",},
							{"00000", ".101.", ".101.", "00000",},
					})
					.addElement('0', ofBlock(sCaseCore1, 7))
					.addElement('1', ofBlock(IGlassBlock, 0))
					.build();
	
	public GTMTE_Centrifuge(int aID, String aNameRegional) {
		super(aID, "impact.multimachine.centrifuge", aNameRegional);
	}
	
	public GTMTE_Centrifuge(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Centrifuge(this.mName);
	}
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 2, 3, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_Centrifuge> getStructureDefinition() {
		return definition;
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("multi_centrifuge");
		b
				.addSingleAnalog()
				.addParallelInfo(1, 256)
				.addPollution(200, 12800)
				.addTypeMachine("name", "Centrifuge, Thermal Centrifuge")
				.addScrew()
				.addSeparatedBus()
				.addSeparator()
				.addController()
				.addEnergyHatch(4)
				.addMaintenanceHatch()
				.addMuffler()
				.addInputBus(6)
				.addOutputBus(3)
				.addOutputHatch(6)
				.addInputHatch(6)
				.addParallelHatch(1)
				.addCasingInfo("case", "Centrifuge Casing", 29)
				.signAndFinalize();
		return b;
	}
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_BASE(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
				"MultiParallelBlockGUI.png", mModed
		);
	}
	
	@Override
	public GT_Recipe.GT_Recipe_Map getRecipeMap() {
		return mRecipeMode == 0 ? GT_Recipe.GT_Recipe_Map.sMultiblockCentrifugeRecipes : GT_Recipe.GT_Recipe_Map.sThermalCentrifugeRecipes;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		
		int minCasingAmount = 12; // Минимальное количество кейсов
		boolean formationChecklist = true; // Если все ок, машина собралась
		
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				if (X == 0 && Z == 0) {
					continue;
				}
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
							== CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		for (byte X = -2; X <= 2; X++) {
			for (byte Y = 1; Y <= 2; Y++) {
				for (byte Z = 0; Z >= -4; Z--) {
					
					if (X == -2 && Z == 0 || X == -2 && Z == -4 || X == 2 && Z == 0 || X == 2 && Z == -4) {
						continue;
					}
					
					if (((X == -1 || X == 1) && (Z == -1 || Z == -2 || Z == -3)) || (X == 0 && (Z == -1 || Z == -3))) {
						continue;
					}
					
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					String glass = thisController.getBlockOffset(offset.x(), offset.y(), offset.z()).getUnlocalizedName();
					if (((X == -1 || X == 1) && (Z == 0 || Z == -4)) || ((X == -2 || X == 2) && (Z == -1 || Z == -3))) {
						if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
						} else {
							formationChecklist = false;
						}
						continue;
					}
					
					if (X == 0 && Z == -2) {
						continue;
					}
					
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
							&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
						
						if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
								&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							minCasingAmount--;
						} else {
							formationChecklist = false;
						}
					}
				}
			}
		}
		for (byte X = -2; X <= 2; X++) {
			for (byte Z = 0; Z >= -4; Z--) {
				
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 3, Z);
				
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addMufflerToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
						minCasingAmount--;
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		
		if (this.mInputBusses.size() > 6) {
			formationChecklist = false;
		}
		if (this.mInputHatches.size() > 6) {
			formationChecklist = false;
		}
		if (this.mOutputBusses.size() > 3) {
			formationChecklist = false;
		}
		if (this.mOutputHatches.size() > 6) {
			formationChecklist = false;
		}
		if (this.mMufflerHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.mEnergyHatches.size() > 4) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		if (this.sParallHatchesIn.size() > 1) {
			formationChecklist = false;
		}
		if (this.sParallHatchesOut.size() != 0) {
			formationChecklist = false;
		}
		
		return formationChecklist;
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		return impactRecipeWithStackSize();
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 200;
	}
	
	
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		
		if (aPlayer.isSneaking()) {
			ScrewClick(aSide, aPlayer, aX, aY, aZ);
		} else if (aSide == getBaseMetaTileEntity().getFrontFacing()) {
			mRecipeMode++;
			if (mRecipeMode > 1) {
				mRecipeMode = 0;
			}
			
			mModed = (mRecipeMode == 0 ? " Centrifuge " : " Thermal Centrifuge ");
			GT_Utility.sendChatToPlayer(aPlayer, "Now" + EnumChatFormatting.YELLOW + mModed + EnumChatFormatting.RESET + "Mode");
		}
	}
}