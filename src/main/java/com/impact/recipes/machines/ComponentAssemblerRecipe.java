package com.impact.recipes.machines;

import static com.impact.common.item.Core_List_Items.ChargedGlassLense;
import static com.impact.common.item.Core_List_Items.HugeChargedGlassLense;

import com.impact.common.item.Core_Items2;
import com.impact.mods.gregtech.GT_ItemList;
import gregtech.GT_Mod;
import gregtech.api.enums.GT_Values;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;

public class ComponentAssemblerRecipe implements Runnable {

  final Core_Items2 CoreItems2 = Core_Items2.getInstance();

  @Override
  public void run() {

    if (GT_Mod.gregtechproxy.mComponentAssembler) {

      //Motors
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.bolt, Materials.IronMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireFine, Materials.Tin, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 2L)}, GT_Values.NF,
          GT_ItemList.ULVMotor.get(1L), 40, 8);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.IronMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iron, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt01, Materials.Copper, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Cupronickel, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.stick, Materials.SteelMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Electrum, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Silver, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.AnnealedCopper, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.NeodymiumMagnetic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2L),
              GT_OreDictUnificator.get(OrePrefixes.wireGt02, Materials.Graphene, 4L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2L)}, GT_Values.NF,
          ItemList.Electric_Motor_IV.get(1L), 200, 7680);

      //Pumps
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Lead, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Lead, 2),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Copper, 1)}, GT_Values.NF,
          GT_ItemList.ULVPump.get(1L), 100, 8);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Bronze, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Bronze, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Steel, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)},
          GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)},
          GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.Steel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.StainlessSteel, 1)},
          GT_Values.NF, ItemList.Electric_Pump_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Rubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.StainlessSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.Titanium, 1)}, GT_Values.NF,
          ItemList.Electric_Pump_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.Silicone, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1)},
          GT_Values.NF, ItemList.Electric_Pump_IV.get(1L), 200, 7680);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.ring, Materials.StyreneButadieneRubber, 2),
              GT_OreDictUnificator.get(OrePrefixes.rotor, Materials.TungstenSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.screw, Materials.TungstenSteel, 1),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1),
              GT_OreDictUnificator.get(OrePrefixes.pipeMedium, Materials.TungstenSteel, 1)},
          GT_Values.NF, ItemList.Electric_Pump_IV.get(1L), 200, 7680);

      //Conveyors
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 1)}, GT_Values.NF,
          GT_ItemList.ULVConveyorModule.get(1L), 40, 4);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 1)}, GT_Values.NF,
          GT_ItemList.ULVConveyorModule.get(1L), 40, 4);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 1)}, GT_Values.NF,
          GT_ItemList.ULVConveyorModule.get(1L), 40, 4);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Rubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Silicone, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_IV.get(1L), 200, 7680);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(2L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StyreneButadieneRubber, 6),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 1)}, GT_Values.NF,
          ItemList.Conveyor_Module_IV.get(1L), 200, 7680);

      //Pistons
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{GT_ItemList.ULVMotor.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.WroughtIron, 2),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.WroughtIron, 1)},
          GT_Values.NF, GT_ItemList.ULVPiston.get(1L), 40, 8);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 3),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Steel, 1)}, GT_Values.NF,
          ItemList.Electric_Piston_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 3),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Aluminium, 1)}, GT_Values.NF,
          ItemList.Electric_Piston_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 3),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.StainlessSteel, 1)},
          GT_Values.NF, ItemList.Electric_Piston_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 3),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.Titanium, 1)}, GT_Values.NF,
          ItemList.Electric_Piston_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Electric_Motor_IV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 3),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2),
              GT_OreDictUnificator.get(OrePrefixes.gearGtSmall, Materials.TungstenSteel, 1)},
          GT_Values.NF, ItemList.Electric_Piston_IV.get(1L), 200, 7680);

      //Robot Arms
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_ItemList.ULVMotor.get(2L), GT_ItemList.ULVPiston.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.WroughtIron, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Primitive, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Lead, 3)}, GT_Values.NF,
          GT_ItemList.ULVRobotArm.get(1L), 40, 8);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{ItemList.Electric_Motor_LV.get(2L), ItemList.Electric_Piston_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Steel, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 3)}, GT_Values.NF,
          ItemList.Robot_Arm_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{ItemList.Electric_Motor_MV.get(2L), ItemList.Electric_Piston_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Aluminium, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 3)}, GT_Values.NF,
          ItemList.Robot_Arm_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{ItemList.Electric_Motor_HV.get(2L), ItemList.Electric_Piston_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.StainlessSteel, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 3)}, GT_Values.NF,
          ItemList.Robot_Arm_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{ItemList.Electric_Motor_EV.get(2L), ItemList.Electric_Piston_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Titanium, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 3)},
          GT_Values.NF, ItemList.Robot_Arm_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{ItemList.Electric_Motor_IV.get(2L), ItemList.Electric_Piston_IV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.TungstenSteel, 2),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 3)}, GT_Values.NF,
          ItemList.Robot_Arm_IV.get(1L), 200, 7680);

      //Emitter
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2)}, GT_Values.NF,
          ItemList.Emitter_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tin, 2)}, GT_Values.NF,
          ItemList.Emitter_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Copper, 2)}, GT_Values.NF,
          ItemList.Emitter_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemFlawed, Materials.Emerald, 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Gold, 2)}, GT_Values.NF,
          ItemList.Emitter_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Aluminium, 2)},
          GT_Values.NF, ItemList.Emitter_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 4),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 2L),
              GT_OreDictUnificator.get(OrePrefixes.cableGt01, Materials.Tungsten, 2)}, GT_Values.NF,
          ItemList.Emitter_IV.get(1L), 200, 7680);

      //Sensor
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.CertusQuartz, 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),}, GT_Values.NF,
          ItemList.Sensor_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.Quartzite, 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Steel, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Brass, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Basic, 1L),}, GT_Values.NF,
          ItemList.Sensor_LV.get(1L), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gem, Materials.NetherQuartz, 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Aluminium, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Electrum, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 1L),}, GT_Values.NF,
          ItemList.Sensor_MV.get(1L), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{GT_OreDictUnificator.get(OrePrefixes.gemFlawed, Materials.Emerald, 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.StainlessSteel, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Chrome, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 1L),}, GT_Values.NF,
          ItemList.Sensor_HV.get(1L), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{CoreItems2.getRecipe(ChargedGlassLense.getMetaID(), 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.Titanium, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Platinum, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 1L),}, GT_Values.NF,
          ItemList.Sensor_EV.get(1L), 180, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(
          new ItemStack[]{CoreItems2.getRecipe(HugeChargedGlassLense.getMetaID(), 1),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.TungstenSteel, 4),
              GT_OreDictUnificator.get(OrePrefixes.stick, Materials.Iridium, 1),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 1L),}, GT_Values.NF,
          ItemList.Sensor_IV.get(1L), 200, 7680);

      //FieldGenerators
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Emitter_LV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Good, 4),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.RedSteel, 4)}, null,
          ItemList.Field_Generator_LV.get(1), 80, 30);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Emitter_MV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Advanced, 4),
              GT_OreDictUnificator.get(OrePrefixes.plate, Materials.BT6, 4)}, null,
          ItemList.Field_Generator_MV.get(1), 120, 120);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Emitter_HV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Data, 4),
              GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.HSSG, 4)}, null,
          ItemList.Field_Generator_HV.get(1), 160, 480);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Emitter_EV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Elite, 4),
              GT_OreDictUnificator.get(OrePrefixes.plateDouble, Materials.NiobiumTitanium, 4)}, null,
          ItemList.Field_Generator_EV.get(1), 160, 1920);
      GT_Values.RA.addComponentAssemblerRecipe(new ItemStack[]{ItemList.Emitter_IV.get(1L),
              GT_OreDictUnificator.get(OrePrefixes.circuit, Materials.Master, 4),
              GT_OreDictUnificator.get(OrePrefixes.plateTriple, Materials.HSSS, 4)}, null,
          ItemList.Field_Generator_IV.get(1L), 200, 7680);

    }
  }
}
