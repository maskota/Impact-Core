package com.impact.mods.gregtech.tileentities.multi.units;

import com.github.technus.tectech.mechanics.alignment.enumerable.ExtendedFacing;
import com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer;
import com.github.technus.tectech.mechanics.structure.IStructureDefinition;
import com.github.technus.tectech.mechanics.structure.StructureDefinition;
import com.impact.core.Impact_API;
import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.enums.Texture;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.PositionObject;
import com.impact.util.Utilits;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Materials;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import static com.github.technus.tectech.mechanics.constructable.IMultiblockInfoContainer.registerMetaClass;
import static com.github.technus.tectech.mechanics.structure.StructureUtility.ofBlock;
import static com.impact.loader.ItemRegistery.SpaceElevatorBlock;

public class GTMTE_Aerostat extends GT_MetaTileEntity_MultiParallelBlockBase {
	
	public final static int MAX_BUFFER = 50_000;
	public int aerID = 0;
	public int curID = 0;
	public boolean firstOpen = true;
	public boolean isFullBuffer = false;
	public int curBuffer = 0;
	
	public String aerName = "";
	Block CASING = Casing_Helper.sCaseCore2;
	byte CASING_META = 14;
	ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][CASING_META + 16];
	int CASING_TEXTURE_ID = CASING_META + 16 + 128 * 3;
	
	public GTMTE_Aerostat(int aID, String aName, String aNameRegional) {
		super(aID, aName, aNameRegional);
		holo();
	}
	
	public GTMTE_Aerostat(String aName) {
		super(aName);
	}
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide,
								 final byte aFacing,
								 final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == 1
				? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
				aActive
						? Texture.Icons.OVERLAY_SPACE_ELEVATOR_ACTIVE
						: Texture.Icons.OVERLAY_SPACE_ELEVATOR)}
				: new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_Aerostat(this.mName);
	}
	
	public boolean isFacingValid(byte aFacing) {
		return aFacing > 1;
	}
	
	@Override
	public String[] getDescription() {
		final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
		b
				.addInfo("Teleportation on Space Satellite")
				.addTypeMachine("Space Elevator")
				.addInfo("Setup is done using Laptop")
				.addInfo("Send a redstone signal to teleport")
				.addInfo("Passive usage: 1920 EU/t")
				.addController()
				.addEnergyHatch("Any casing")
				.addCasingInfo("Space Elevator Casing")
				.addOtherStructurePart("Space Elevator Hawser", "Center below Controller")
				.signAndFinalize(": " + EnumChatFormatting.RED + "IMPACT");
		if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
			return b.getInformation();
		} else {
			return b.getStructureInformation();
		}
	}
	
	public void holo() {
		registerMetaClass(GTMTE_Aerostat.class, new IMultiblockInfoContainer<GTMTE_Aerostat>() {
			//region Structure
			private final IStructureDefinition<GTMTE_Aerostat> definition =
					StructureDefinition.<GTMTE_Aerostat>builder()
							.addShape("main", new String[][]{
									{"AAA"},
									{"A~A"},
									{"AAA"},
							})
							.addElement('A', ofBlock(CASING, CASING_META))
							.addElement('B', ofBlock(SpaceElevatorBlock))
							.build();
			private final String[] desc = new String[]{
					EnumChatFormatting.RED + "Impact Details:",
					"- Space Elevator Casing",
					"- Space Elevator Hawser",
					"- Hatches (any Space Elevator Casing)",
			};
			//endregion
			
			@Override
			public void construct(ItemStack stackSize,
								  boolean hintsOnly, GTMTE_Aerostat tileEntity, ExtendedFacing aSide) {
				IGregTechTileEntity base = tileEntity.getBaseMetaTileEntity();
				definition.buildOrHints(tileEntity, stackSize, "main",
						base.getWorld(), aSide, base.getXCoord(), base.getYCoord(), base.getZCoord(),
						1, 0, 1, hintsOnly
				);
			}
			
			@Override
			public String[] getDescription(ItemStack stackSize) {
				return desc;
			}
		});
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return null;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ);
		
		boolean formationChecklist = true;
		
		for (int X = -1; X <= 1; X++) {
			for (int Z = -1; Z <= 1; Z++) {
				if (X == 0 && Z == 0) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, 0, Z);
				IGregTechTileEntity currentTE = thisController
						.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				
				//Utilits.setBlock(thisController, offset.x(), offset.y(), offset.z(), CASING, CASING_META);
				
				if (!super.addInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z())
							== CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		mWrench = true;
		mScrewdriver = true;
		mSoftHammer = true;
		mHardHammer = true;
		mSolderingTool = true;
		mCrowbar = true; //todo Пересмотреть мейнтенанс
		return formationChecklist;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide() && mMachine) {
			if (aTick % 20 == 0 && curBuffer < MAX_BUFFER - 100) {
				isFullBuffer = false;
			}
			if (!isFullBuffer && depleteInput(Materials.Gas.getGas(100))) {
				curBuffer += 100;
				if (curBuffer + 100 > MAX_BUFFER) {
					curBuffer = MAX_BUFFER;
					isFullBuffer = true;
				}
			}
		}
	}
	
	@Override
	public boolean onRightclick(IGregTechTileEntity aBaseMetaTileEntity, EntityPlayer aPlayer) {
		return mMachine && super.onRightclick(aBaseMetaTileEntity, aPlayer);
	}
	
	public void setLocationName(String name) {
		PositionObject thisLocation = new PositionObject(getBaseMetaTileEntity());
		Impact_API.sAerostat.remove(aerName);
		aerName = name;
		aerID = 1;
		Utilits.sendChatByTE(getBaseMetaTileEntity(), "Set location name: \"" + aerName + "\"");
		Impact_API.sAerostat.put(aerName, thisLocation);
		int id = 1;
		for (String names : Impact_API.sAerostat.keySet()) {
			if (names.equals(aerName)) {
				curID = id;
			}
			id++;
		}
	}
	
	@Override
	public void onFirstTick(IGregTechTileEntity aBaseMetaTileEntity) {
		super.onFirstTick(aBaseMetaTileEntity);
		if (aBaseMetaTileEntity.isServerSide()) {
			if (!aerName.equals("")) {
				setLocationName(aerName);
			}
		}
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("aerID", aerID);
		aNBT.setInteger("curBuffer", curBuffer);
		aNBT.setInteger("curID", curID);
		aNBT.setString("aerName", aerName);
		aNBT.setBoolean("firstOpen", firstOpen);
	}
	
	@Override
	public void inValidate() {
		Impact_API.sAerostat.remove(aerName);
		if (!aerName.equals("")) {
			Utilits.sendChatByTE(getBaseMetaTileEntity(), "Remove Station: \"" + aerName + "\"");
		}
		super.inValidate();
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		curID = aNBT.getInteger("curID");
		curBuffer = aNBT.getInteger("curBuffer");
		aerID = aNBT.getInteger("aerID");
		firstOpen = aNBT.getBoolean("firstOpen");
		aerName = aNBT.getString("aerName");
	}
	
	@Override
	public void onNotePadRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onNotePadRightClick(aSide, aPlayer, aX, aY, aZ);
	}
	
	@Override
	public boolean checkRecipe(ItemStack itemStack) {
		return false;
	}
	
	public void toTravel(EntityPlayer aPlayer) {
		int id = 1;
		for (String name : Impact_API.sAerostat.keySet()) {
			if (id == aerID) {
				PositionObject thisPos = new PositionObject(getBaseMetaTileEntity());
				PositionObject newPos = Impact_API.sAerostat.get(name);
				int distance = Utilits.distanceBetween3D(thisPos.xPos, newPos.xPos, thisPos.yPos, newPos.yPos, thisPos.zPos, newPos.zPos);
				if (distance > 16 && thisPos.dPos == newPos.dPos) {
					if (curBuffer - distance * 100 > 0) {
						curBuffer -= distance * 100;
						aPlayer.setPositionAndUpdate(newPos.xPos + 0.5D, newPos.yPos + 1, newPos.zPos + 0.5D);
					} else {
						GT_Utility.sendChatToPlayer(aPlayer, "No Fuel");
					}
				} else {
					GT_Utility.sendChatToPlayer(aPlayer, "Error Distance");
				}
				return;
			}
			id++;
		}
	}
	
	public void onScrewdriverRightClick(byte aSide, EntityPlayer aPlayer, float aX, float aY, float aZ) {
		super.onScrewdriverRightClick(aSide, aPlayer, aX, aY, aZ);
	}
}