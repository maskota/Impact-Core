package com.impact.mods.GregTech.tileentities.storage;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_DynamoTunnel;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyTunnel;
import com.impact.mods.GregTech.blocks.Casing_Helper;
import com.impact.util.MultiBlockTooltipBuilder;
import com.impact.util.Vector3i;
import com.impact.util.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Dynamo;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_MultiBlockBase;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.impact.mods.GregTech.enums.Texture.Icons.LPS;
import static com.impact.mods.GregTech.enums.Texture.Icons.LPS_ACTIVE;
import static com.impact.loader.ItemRegistery.IGlassBlock;
import static com.impact.loader.ItemRegistery.lscLapotronicEnergyUnit;
import static net.minecraft.util.EnumChatFormatting.*;
/**
 * Made by Kekzdealer
 * Edit by 4gname
 */

public class GTMTE_LapPowerStation extends GT_MetaTileEntity_MultiBlockBase  {

    private static final Block LSC_PART = lscLapotronicEnergyUnit;
    private static final Block CASING = Casing_Helper.sCaseCore2;
    private static final int CASING_META = 8;
    private static final ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[3][16+CASING_META];
    private static final int CASING_TEXTURE_ID = CASING_META + 16 + 128*3;

    private static final BigInteger MAX_LONG = BigInteger.valueOf(Long.MAX_VALUE);
    private static final BigDecimal PASSIVE_DISCHARGE_FACTOR_PER_TICK = BigDecimal.ZERO;
            //BigDecimal.valueOf(0.01D / 1728000.0D); // The magic number is ticks per 24 hours

    private final Set<GT_MetaTileEntity_Hatch_EnergyMulti> mEnergyHatchesTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_DynamoMulti> mDynamoHatchesTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_EnergyTunnel> mEnergyTunnelsTT = new HashSet<>();
    private final Set<GT_MetaTileEntity_Hatch_DynamoTunnel> mDynamoTunnelsTT = new HashSet<>();
    // Count the amount of capacitors of each tier in each slot (translate with meta - 1)
    private final int[] capacitors = new int[6];
    public BigInteger capacity = BigInteger.ZERO;
    public BigInteger stored = BigInteger.ZERO;
    public BigInteger passiveDischargeAmount = BigInteger.ZERO;
    public BigInteger intputLastTick = BigInteger.ZERO;
    public BigInteger outputLastTick = BigInteger.ZERO;
    private int repairStatusCache = 0;

    public GTMTE_LapPowerStation(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public GTMTE_LapPowerStation(String aName) {
        super(aName);
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity var1) {
        return new GTMTE_LapPowerStation(super.mName);
    }

    @Override
    public String[] getDescription() {
        final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
        b
                .addInfo("Multi battery buffer!")
                .addInfo("Very big battery")
                .addSeparator()
                .beginStructureBlock(5, 4, 5)
                .addController()
                .addDynamoHatch("Instead of any casing")
                .addEnergyHatch("Instead of any casing")
                .addOtherStructurePart("Lapotronic Capacitor Base", "5x2x5 base (at least 17x)")
                .addOtherStructurePart("Lapotronic Capacitor, Ultimate Capacitor", "Center 3x(1-15)x3 above base (9-135 blocks)")
                .addOtherStructurePart("I-Glass", "41-265x, Encase capacitor pillar")
                .addMaintenanceHatch("Instead of any casing")
                .signAndFinalize("IMPACT");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return b.getInformation();
        } else {
            return b.getStructureInformation();
        }
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex,
                                 boolean aActive, boolean aRedstone) {
        return aSide == aFacing
                ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(
                aActive
                        ? LPS_ACTIVE
                        : LPS)}
                : new ITexture[]{INDEX_CASE};
    }

    public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
        return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, this.getLocalName(),
                "MultiblockDisplay.png");
    }

    @Override
    public boolean isCorrectMachinePart(ItemStack stack) {
        return true;
    }

    @Override
    public boolean checkRecipe(ItemStack stack) {
        this.mProgresstime = 1;
        this.mMaxProgresstime = 1;
        this.mEUt = 0;
        this.mEfficiencyIncrease = 10000;
        return true;
    }

    public Vector3ic rotateOffsetVector(Vector3ic forgeDirection, int x, int y, int z) {
        final Vector3i offset = new Vector3i();

        // either direction on z-axis
        if (forgeDirection.x() == 0 && forgeDirection.z() == -1) {
            offset.x = x;
            offset.y = y;
            offset.z = z;
        }
        if (forgeDirection.x() == 0 && forgeDirection.z() == 1) {
            offset.x = -x;
            offset.y = y;
            offset.z = -z;
        }
        // either direction on x-axis
        if (forgeDirection.x() == -1 && forgeDirection.z() == 0) {
            offset.x = z;
            offset.y = y;
            offset.z = -x;
        }
        if (forgeDirection.x() == 1 && forgeDirection.z() == 0) {
            offset.x = -z;
            offset.y = y;
            offset.z = x;
        }
        // either direction on y-axis
        if (forgeDirection.y() == -1) {
            offset.x = x;
            offset.y = z;
            offset.z = y;
        }

        return offset;
    }

    @Override
    public boolean checkMachine(IGregTechTileEntity thisController, ItemStack guiSlotItem) {
        // Figure out the vector for the direction the back face of the controller is facing
        final Vector3ic forgeDirection = new Vector3i(
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
                ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
        );
        boolean formationChecklist = true;
        int minCasingAmount = 16;
        int firstGlassMeta = -1;
        // Reset capacitor counts
        Arrays.fill(capacitors, 0);
        // Clear TT hatches
        mEnergyHatchesTT.clear();
        mDynamoHatchesTT.clear();
        mEnergyTunnelsTT.clear();
        mDynamoTunnelsTT.clear();
        // Temp var for loss calculation
        BigInteger tempCapacity = BigInteger.ZERO;

        // Capacitor base
        for(int Y = 0; Y <= 1; Y++) {
            for(int X = -2; X <= 2; X++) {
                for(int Z = 0; Z >= -4; Z--) {
                    if(X == 0 && Y == 0 && Z == 0) {
                        continue; // Skip controller
                    }

                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    final IGregTechTileEntity currentTE =
                            thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());

                    // Tries to add TE as either of those kinds of hatches.
                    // The number is the texture index number for the texture that needs to be painted over the hatch texture
                    if (   !super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !this.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
                            && !this.addDynamoToMachineList(currentTE, CASING_TEXTURE_ID)) {

                        // If it's not a hatch, is it the right casing for this machine? Check block and block meta.
                        if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
                                && (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
                            // Seems to be valid casing. Decrement counter.
                            minCasingAmount--;
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }
        // Capacitor units
        int firstGlassHeight = 3; // Initialize at basic height (-1 because it's an offset)
        for(int X = -1; X <= 1; X++) {
            for(int Z = -1; Z >= -3; Z--) {
                // Y has to be the innermost loop to properly deal with the dynamic height.
                // This way each "pillar" of capacitors is checked from bottom to top until it hits glass.
                for(int Y = 2; Y <= 17; Y++) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);

                    final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
                    if(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == LSC_PART && (meta > 0) ) {
                        // Add capacity
                        long c = 0;
                        switch (meta) {
                            case 1:
                                c = 250000000L;
                                tempCapacity = tempCapacity.add(BigInteger.valueOf(c));
                                capacity = capacity.add(BigInteger.valueOf(c));
                                break;
                            case 2:
                            case 3:
                            case 4:
                                c = (long) (100000000L * Math.pow(10, meta - 1));
                                tempCapacity = tempCapacity.add(BigInteger.valueOf(c));
                                capacity = capacity.add(BigInteger.valueOf(c));
                                break;
                            case 5:
                                tempCapacity = tempCapacity.add(BigInteger.valueOf((long) (100000000L * Math.pow(10, 3))));
                                capacity = capacity.add(MAX_LONG);
                                break;
                            case 6:
                                c = 819200000L;
                                tempCapacity = tempCapacity.add(BigInteger.valueOf(c));
                                capacity = capacity.add(BigInteger.valueOf(c));
                                break;
                        }
                        capacitors[meta - 1]++;
                    } else if(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock){
                        firstGlassHeight = Y;
                        break;
                    } else {
                        formationChecklist = false;
                    }
                }
            }
        }
        // Glass shell
        // Make Y the outermost loop, so each layer is checked completely before moving up
        for(int Y = 2; Y <= firstGlassHeight; Y++) {
            for(int X = -2; X <= 2; X++) {
                for(int Z = 0; Z >= -4; Z--) {
                    final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
                    final int meta = thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z());
                    // Check only outer ring, except when on roof height
                    if((Y < firstGlassHeight)){
                        if(X == -2 || X == 2 || Z == 0 || Z == 4){
                            if(thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                                if(firstGlassMeta == -1) {
                                    firstGlassMeta = meta;
                                } else if(meta != firstGlassMeta) {
                                    formationChecklist = false;
                                }
                            } else {
                                formationChecklist = false;
                            }
                        }
                    } else {
                        if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == IGlassBlock) {
                            if(meta != firstGlassMeta) {
                                formationChecklist = false;
                            }
                        } else {
                            formationChecklist = false;
                        }
                    }
                }
            }
        }

        if(minCasingAmount > 0){
            formationChecklist = false;
        }

        for(int highestCapacitor = capacitors.length - 1; highestCapacitor >= 0; highestCapacitor--){
            if(capacitors[highestCapacitor] > 0){
                break;
            }
        }

//        if(mEnergyTunnelsTT.size() > 0 || mDynamoTunnelsTT.size() > 0) {
//            formationChecklist = false;
//        }




        // Calculate total capacity
        capacity = BigInteger.ZERO;
        for(int i = 0; i < capacitors.length; i++){
            if (i == 0) {
                final long c = 250000000;
                capacity = capacity.add(BigInteger.valueOf(c).multiply(BigInteger.valueOf(capacitors[i])));
            } else if(i <= 3){
                final long c = (long) (100000000L * Math.pow(10, i));
                capacity = capacity.add(BigInteger.valueOf(c).multiply(BigInteger.valueOf(capacitors[i])));
            } else if (i == 4) {
                capacity = capacity.add(MAX_LONG.multiply(BigInteger.valueOf(capacitors[i])));
            } else {
                final long c = 819200000L;
                capacity = capacity.add(BigInteger.valueOf(c).multiply(BigInteger.valueOf(capacitors[i])));
            }
        }
        // Calculate how much energy to void each tick
        //passiveDischargeAmount = new BigDecimal(tempCapacity).multiply(PASSIVE_DISCHARGE_FACTOR_PER_TICK).toBigInteger();
        //passiveDischargeAmount = recalculateLossWithMaintenance(super.getRepairStatus());
        passiveDischargeAmount = BigInteger.ZERO;
        return formationChecklist;
    }

    @Override
    public boolean addEnergyInputToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
        if (te == null) {
            return false;
        } else {
            final IMetaTileEntity mte = te.getMetaTileEntity();
            if (mte instanceof GT_MetaTileEntity_Hatch_Energy) {
                // Add GT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return super.mEnergyHatches.add((GT_MetaTileEntity_Hatch_Energy) mte);
            } else if(mte instanceof  GT_MetaTileEntity_Hatch_EnergyTunnel) {
                // Add TT Laser hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mEnergyTunnelsTT.add((GT_MetaTileEntity_Hatch_EnergyTunnel) mte);
            } else if(mte instanceof GT_MetaTileEntity_Hatch_EnergyMulti) {
                // Add TT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mEnergyHatchesTT.add((GT_MetaTileEntity_Hatch_EnergyMulti) mte);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean addDynamoToMachineList(IGregTechTileEntity te, int aBaseCasingIndex) {
        if (te == null) {
            return false;
        } else {
            final IMetaTileEntity mte = te.getMetaTileEntity();
            if (mte instanceof GT_MetaTileEntity_Hatch_Dynamo) {
                // Add GT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return super.mDynamoHatches.add((GT_MetaTileEntity_Hatch_Dynamo) mte);
            } else if(mte instanceof  GT_MetaTileEntity_Hatch_DynamoTunnel) {
                // Add TT Laser hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mDynamoTunnelsTT.add((GT_MetaTileEntity_Hatch_DynamoTunnel) mte);
            } else if(mte instanceof GT_MetaTileEntity_Hatch_DynamoMulti) {
                // Add TT hatches
                ((GT_MetaTileEntity_Hatch) mte).updateTexture(aBaseCasingIndex);
                return mDynamoHatchesTT.add((GT_MetaTileEntity_Hatch_DynamoMulti) mte);
            } else {
                return false;
            }
        }
    }

    @Override
    public boolean onRunningTick(ItemStack stack){
        // Reset I/O cache
        intputLastTick = BigInteger.ZERO;
        outputLastTick = BigInteger.ZERO;

        // Draw energy from GT hatches
        for(GT_MetaTileEntity_Hatch_Energy eHatch : super.mEnergyHatches) {
            if(eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
            if(eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored = stored.add(BigInteger.valueOf(power));
                intputLastTick = intputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Output energy to GT hatches
        for(GT_MetaTileEntity_Hatch_Dynamo eDynamo : super.mDynamoHatches){
            if(eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()){
                continue;
            }
            final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
            if(power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored = stored.subtract(BigInteger.valueOf(power));
                outputLastTick = outputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Draw energy from TT hatches
        for(GT_MetaTileEntity_Hatch_EnergyMulti eHatch : mEnergyHatchesTT) {
            if(eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long power = getPowerToDraw(eHatch.maxEUInput() * eHatch.maxAmperesIn());
            if(eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored = stored.add(BigInteger.valueOf(power));
                intputLastTick = intputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Output energy to TT hatches
        for(GT_MetaTileEntity_Hatch_DynamoMulti eDynamo : mDynamoHatchesTT){
            if(eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()){
                continue;
            }
            final long power = getPowerToPush(eDynamo.maxEUOutput() * eDynamo.maxAmperesOut());
            if(power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored = stored.subtract(BigInteger.valueOf(power));
                outputLastTick = outputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Draw energy from TT Laser hatches
        for(GT_MetaTileEntity_Hatch_EnergyTunnel eHatch : mEnergyTunnelsTT) {
            if(eHatch == null || eHatch.getBaseMetaTileEntity().isInvalidTileEntity()) {
                continue;
            }
            final long ttLaserWattage = eHatch.maxEUInput() * eHatch.Amperes - (eHatch.Amperes / 20);
            final long power = getPowerToDraw(ttLaserWattage);
            if(eHatch.getEUVar() >= power) {
                eHatch.setEUVar(eHatch.getEUVar() - power);
                stored = stored.add(BigInteger.valueOf(power));
                intputLastTick = intputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Output energy to TT Laser hatches
        for(GT_MetaTileEntity_Hatch_DynamoTunnel eDynamo : mDynamoTunnelsTT){
            if(eDynamo == null || eDynamo.getBaseMetaTileEntity().isInvalidTileEntity()){
                continue;
            }
            final long ttLaserWattage = eDynamo.maxEUOutput() * eDynamo.Amperes - (eDynamo.Amperes / 20);
            final long power = getPowerToPush(ttLaserWattage);
            if(power <= eDynamo.maxEUStore() - eDynamo.getEUVar()) {
                eDynamo.setEUVar(eDynamo.getEUVar() + power);
                stored = stored.subtract(BigInteger.valueOf(power));
                outputLastTick = outputLastTick.add(BigInteger.valueOf(power));
            }
        }
        // Loose some energy
        // Recalculate if the repair status changed
        if(super.getRepairStatus() != repairStatusCache) {
            passiveDischargeAmount = recalculateLossWithMaintenance(super.getRepairStatus());
        }
        stored = stored.subtract(passiveDischargeAmount);
        stored = (stored.compareTo(BigInteger.ZERO) <= 0) ? BigInteger.ZERO : stored;

        return true;
    }

    /**
     * To be called whenever the maintenance status changes or the capacity was recalculated
     * @param repairStatus
     * 		This machine's repair status
     * @return new BigInteger instance for passiveDischargeAmount
     */
    private BigInteger recalculateLossWithMaintenance(int repairStatus) {
        repairStatusCache = repairStatus;
        return new BigDecimal(passiveDischargeAmount)
                .multiply(BigDecimal.valueOf(1.0D + 0.2D * repairStatus)).toBigInteger();
    }

    /**
     * Calculate how much EU to draw from an Energy Hatch
     * @param hatchWatts
     * 		Hatch amperage * voltage
     * @return EU amount
     */
    private long getPowerToDraw(long hatchWatts){
        final BigInteger remcapActual = capacity.subtract(stored);
        final BigInteger recampLimited = (MAX_LONG.compareTo(remcapActual) > 0) ? remcapActual : MAX_LONG;
        return Math.min(hatchWatts, recampLimited.longValue());
    }

    /**
     * Calculate how much EU to push into a Dynamo Hatch
     * @param hatchWatts
     * 		Hatch amperage * voltage
     * @return EU amount
     */
    private long getPowerToPush(long hatchWatts){
        final BigInteger remStoredLimited = (MAX_LONG.compareTo(stored) > 0) ? stored : MAX_LONG;
        return Math.min(hatchWatts, remStoredLimited.longValue());
    }

    @Override
    public String[] getInfoData() {
        final ArrayList<String> ll = new ArrayList<>();
        ll.add("Used Capacity: ");
        ll.add(GREEN + NumberFormat.getNumberInstance().format(stored) + RESET + " EU");
        ll.add("Total Capacity: ");
        ll.add(YELLOW + NumberFormat.getNumberInstance().format(capacity) + RESET + " EU");
        ll.add("EU IN: ");
        ll.add(GREEN + NumberFormat.getNumberInstance().format(intputLastTick) + RESET + " EU/t");
        ll.add("EU OUT: ");
        ll.add(RED + NumberFormat.getNumberInstance().format(outputLastTick) + RESET + " EU/t");
        ll.add("Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
                ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
                : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET));

        final String[] a = new String[ll.size()];
        return ll.toArray(a);
    }

    @Override
    public void saveNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        nbt.setByteArray("capacity", capacity.toByteArray());
        nbt.setByteArray("stored", stored.toByteArray());
        nbt.setByteArray("passiveDischargeAmount", passiveDischargeAmount.toByteArray());

        super.saveNBTData(nbt);
    }

    @Override
    public void loadNBTData(NBTTagCompound nbt) {
        nbt = (nbt == null) ? new NBTTagCompound() : nbt;

        capacity = new BigInteger(nbt.getByteArray("capacity"));
        stored = new BigInteger(nbt.getByteArray("stored"));
        passiveDischargeAmount = new BigInteger(nbt.getByteArray("passiveDischargeAmount"));

        super.loadNBTData(nbt);
    }

    @Override
    public boolean isGivingInformation() {
        return true;
    }

    @Override
    public int getMaxEfficiency(ItemStack stack) { return 10000; }

    @Override
    public int getPollutionPerTick(ItemStack stack) { return 0; }

    @Override
    public int getDamageToComponent(ItemStack stack) { return 0; }

    @Override
    public boolean explodesOnComponentBreak(ItemStack stack) { return false; }



}
