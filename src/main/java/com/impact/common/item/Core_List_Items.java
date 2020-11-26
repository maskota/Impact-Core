package com.impact.common.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public enum Core_List_Items {

    //MetaItems0 dust
    BarnardaEStoneDust(0, 0),
    BarnardaFStoneDust(1, 0),
    CallistoIceDust(2, 0),
    CallistoStoneDust(3, 0),
    CentauriASurfaceDust(4, 0),
    CentauriAStoneDust(5, 0),
    CeresStoneDust(6, 0),
    DeimosStoneDust(7, 0),
    EnceladusIceDust(8, 0),
    EnceladusStoneDust(9, 0),
    EuropaIceDust(10, 0),
    EuropaStoneDust(11, 0),
    GanymedStoneDust(12, 0),
    IoStoneDust(13, 0),
    HaumeaStoneDust(14, 0),
    MakeMakeStoneDust(15, 0),
    MercuryStoneDust(16, 0),
    MirandaStoneDust(17, 0),
    VenusStoneDust(18, 0),
    PhobosStoneDust(19, 0),
    PlutoIceDust(20, 0),
    PlutoStoneDust(21, 0),
    ProteusStoneDust(22, 0),
    OberonStoneDust(23, 0),
    TCetiEStoneDust(24, 0),
    TitanStoneDust(25, 0),
    TritonStoneDust(26, 0),
    VegaBStoneDust(27, 0),
    TCetiESeaweedExtract(28, 0),
    DustSmallFertilizer(29, 0),
    DustTinyFertilizer(30, 0),
    CokeOvenBrickDust(31, 1),
    SearedBrickDust(32, 1),
    ClearGlassDust(33, 1),
    RawNeutroniumDust(34, 1),
    BarnardaCBiomass(35, 1),
    StargateCrystalDust(36, 1),
    ConcreteDust(37, 1),
    ChargedQuartzDust(38, 1),
    SawDust(39, 1),
    DioxideYttrium(40, 1),

                            EMPTY(41,1), // пустой

    YSZdust(42, 1),
    GDCdust(43, 1),
    LSCFdust(44, 1),
    YSZCeramic(45, 1),
    GDCCeramic(46, 1),
    LSFCCeramic(47, 1),

    //MetaItems1
    SchematicsTier1(0, 1),
    SchematicsTier2(1, 1),
    SchematicsTier3(2, 1),
    SchematicsTier4(3, 1),
    SchematicsTier5(4, 1),
    SchematicsTier6(5, 1),
    SchematicsTier7(6, 1),
    SchematicsTier8(7, 1),
    SchematicsAstroMiner(8, 1),
    SchematicsCargoRocket(9, 1),
    SchematicsMoonBuggy(10, 1),
    HeavyDutyPlateTier4(11, 1),
    HeavyDutyPlateTier5(12, 1),
    HeavyDutyPlateTier6(13, 1),
    HeavyDutyPlateTier7(14, 1),
    HeavyDutyPlateTier8(15, 1),
    HeavyDutyAlloyIngotT4(16, 1),
    HeavyDutyAlloyIngotT5(17, 1),
    HeavyDutyAlloyIngotT6(18, 1),
    HeavyDutyAlloyIngotT7(19, 1),
    HeavyDutyAlloyIngotT8(20, 1),
    HeavyDutyNoseConeTier4(21, 1),
    HeavyDutyNoseConeTier5(22, 1),
    HeavyDutyNoseConeTier6(23, 1),
    HeavyDutyNoseConeTier7(24, 1),
    HeavyDutyNoseConeTier8(25, 1),
    HeavyDutyRocketEngineTier4(26, 1),
    HeavyDutyRocketEngineTier5(27, 1),
    HeavyDutyRocketEngineTier6(28, 1),
    HeavyDutyRocketEngineTier7(29, 1),
    HeavyDutyRocketEngineTier8(30, 1),
    HeavyDutyRocketFinsTier4(31, 1),
    HeavyDutyRocketFinsTier5(32, 1),
    HeavyDutyRocketFinsTier6(33, 1),
    HeavyDutyRocketFinsTier7(34, 1),
    HeavyDutyRocketFinsTier8(35, 1),
    Tier4Booster(36, 1),
    Tier5Booster(37, 1),
    Tier6Booster(38, 1),
    Tier7Booster(39, 1),
    Tier8Booster(40, 1),
    QuantumPartBoots(41, 1),
    QuantumPartChestplate(42, 1),
    QuantumPartHelmetEmpty(43, 1),
    QuantumPartHelmet(44, 1),
    QuantumPartLeggings(45, 1),
    QuantumCrystal(46, 1),
    RefinedReinforcedGlassLense(47, 1),
    ChargedGlassLense(48, 1),
    HugeRefinedReinforcedGlassLense(49, 1),
    HugeChargedGlassLense(50, 1),
    SteelBars(51, 1),
    IridiumAlloyItemCasing(52, 1),
    PistonPlate(53, 1),
    Empty180SpCell(54, 1),
    Empty360SpCell(55, 1),
    Empty540SpCell(56, 1),
    Empty1080SpCell(57, 1),
    OakScheme(58, 1),
    DarkOakScheme(59, 1),
    JungleScheme(60, 1),
    AcaciaScheme(61, 1),
    SpruceScheme(62, 1),
    BirchScheme(63, 1),
    RubberScheme(64, 1),
    CokeOvenBrick(65, 1),
    UnfiredSearedBrick(66, 1),
    UnfiredCokeOvenBrick(67, 1),
    UnfiredClayBrick(68, 1),
    CallistoIceColdIngot(69, 1),
    CallistoIceIngot(70, 1),
    LedoxColdIngot(71, 1),
    MysteriousCrystalColdIngot(72, 1),
    MysteriousCrystalIngot(73, 1),
    CallistoIcePlate(74, 1),
    LedoxColdPlate(75, 1),
    MysteriousCrystalColdPlate(76, 1),
    BlackPlutoniumCompressedPlate(77, 1),
    CallistoIceCompressedPlate(78, 1),
    MeitneriumCompressedPlate(79, 1),
    IceCompressedPlate(80, 1),
    IridiumAlloyCompressedPlate(81, 1),
    LedoxCompressedPlate(82, 1),
    MysteriousCrystalCompressedPlate(83, 1),
    NaquadahCompressedPlate(84, 1),
    QuantiumCompressedPlate(85, 1),
    MytrylCompressedPlate(86, 1),
    PalladiumCompressedPlate(87, 1),
    LeadNickelCompressedPlate(88, 1),
    LeadOriharukonCompressedPlate(89, 1),
    DeshDualCompressedPlate(90, 1),
    IceDualCompressedPlate(91, 1),
    IridiumAlloyDualCompressedPlate(92, 1),
    MeteoricIronDualCompressedPlate(93, 1),
    MysteriousCrystalDualCompressedPlate(94, 1),
    QuantiumDualCompressedPlate(95, 1),
    TitaniumDualCompressedPlate(96, 1),
    RawSDHCAlloy(97, 1),
    AdvancedCoolingCore(98, 1),
    MiningCrystal(99, 1),
    NanoCrystal(100, 1),
    AluminiumIronPlate(101, 1),
    TitaniumIronPlate(102, 1),
    TungstenIronPlate(103, 1),
    TungstenSteelIronPlate(104, 1),
    ChromeIronPlate(105, 1),
    IridiumIronPlate(106, 1),
    NaquadriaIronPlate(107, 1),
    NeutroniumIronPlate(108, 1),
    ReinforcedAluminiumIronPlate(109, 1),
    ReinforcedTitaniumIronPlate(110, 1),
    ReinforcedTungstenIronPlate(111, 1),
    ReinforcedTungstenSteelIronPlate(112, 1),
    ReinforcedChromeIronPlate(113, 1),
    ReinforcedIridiumIronPlate(114, 1),
    ReinforcedNaquadriaIronPlate(115, 1),
    ReinforcedNeutroniumIronPlate(116, 1),
    IrradiantReinforcedAluminiumPlate(117, 1),
    IrradiantReinforcedTitaniumPlate(118, 1),
    IrradiantReinforcedTungstenPlate(119, 1),
    IrradiantReinforcedTungstenSteelPlate(120, 1),
    IrradiantReinforcedChromePlate(121, 1),
    IrradiantReinforcedIridiumPlate(122, 1),
    IrradiantReinforcedNaquadriaPlate(123, 1),
    IrradiantReinforcedNeutroniumPlate(124, 1),
    SunnariumPiece(125, 1),
    Sunnarium(126, 1),
    SunnariumAlloy(127, 1),
    IrradiantUranium(128, 1),
    EnrichedSunnarium(129, 1),
    EnrichedSunnariumAlloy(130, 1),
    EnrichedNaquadriaSunnariumAlloy(131, 1),
    EnrichedNeutroniumSunnariumAlloy(132, 1),
    MediumFuelCanister(133, 1),
    LargeFuelCanister(134, 1),
    ExtraLargeFuelCanister(135, 1),
    CarbonPartHelmet(136, 1),
    CarbonPartHelmetNightVision(137, 1),
    CarbonPartChestplate(138, 1),
    CarbonPartLeggings(139, 1),
    CarbonPartBoots(140, 1),
    NeutronReflectorSmallParts(141, 1),
    NeutronReflectorParts(142, 1),
    TenKCell(143, 1),
    ThirtyKCell(144, 1),
    SixtyKCell(145, 1),
    IndustryFrame(146, 1),
    StargateShieldingFoil(147, 1),
    StargateFramePart(148, 1),
    StargateChevron(149, 1),
    BarnardaCScheme(150, 1),
    BarnardaCBall(151, 1),
    BarnardaCBiochaff(152, 1),
    ChargedQuartzRod(153, 1),
    MicrochipBoard1(154, 1),
    MicrochipBoard2(155, 1),
    MicrochipBoard3(156, 1),
    ALUBoard(157, 1),
    CUBoard(158, 1),
    YSZCeramicPlate(159, 1),
    GDCCeramicPlate(160, 1),
    LSFCCeramicPlate(161, 1),
    TachyonTube(162, 1),
    NaqChamberPart(163, 1),
    ConcreteTie(164, 1),
    ConcreteRailbed(165, 1),
    ExtruderShapeRail(166, 1),
    AdvancedMixIngot(167, 1),
    ElectricMixIngot(168, 1),
    ReinforcedMixIngot(169, 1),
    HSMixIngot(170, 1),

    WoodenBrickForm1(0, 2),

    FakeMVCircuit(0, 3),
    FakeEVCircuit(1, 3),
    FakeIVCircuit(2, 3),
    FakeLuVCircuit(3, 3),
    FakeZPMCircuit(4, 3),
    FakeUVCircuit(5, 3),
    FakeUHVCircuit(6, 3),
    FakeUEVCircuit(7, 3),

    ;

    static {
        BarnardaEStoneDust.setOreDictName("dustBarnardaE");
        BarnardaFStoneDust.setOreDictName("dustBarnardaF");
        CallistoIceDust.setOreDictName("dustIceCallisto");
        CallistoStoneDust.setOreDictName("dustCallisto");
        CentauriASurfaceDust.setOreDictName("dustSurfaceCentauriA");
        CentauriAStoneDust.setOreDictName("dustCentauriA");
        CeresStoneDust.setOreDictName("dustCeres");
        DeimosStoneDust.setOreDictName("dustDeimos");
        EnceladusIceDust.setOreDictName("dustIceEnceladus");
        EnceladusStoneDust.setOreDictName("dustEnceladus");
        EuropaIceDust.setOreDictName("dustIceEuropa");
        EuropaStoneDust.setOreDictName("dustEuropa");
        GanymedStoneDust.setOreDictName("dustGanymed");
        IoStoneDust.setOreDictName("dustIo");
        HaumeaStoneDust.setOreDictName("dustHaumea");
        MakeMakeStoneDust.setOreDictName("dustMakeMake");
        MercuryStoneDust.setOreDictName("dustMercuryP");
        MirandaStoneDust.setOreDictName("dustMiranda");
        VenusStoneDust.setOreDictName("dustVenus");
        PhobosStoneDust.setOreDictName("dustPhobos");
        PlutoIceDust.setOreDictName("dustIcePluto");
        PlutoStoneDust.setOreDictName("dustPluto");
        ProteusStoneDust.setOreDictName("dustProteus");
        OberonStoneDust.setOreDictName("dustOberon");
        TCetiEStoneDust.setOreDictName("dustTCetiE");
        TitanStoneDust.setOreDictName("dustTitan");
        TritonStoneDust.setOreDictName("dustTriton");
        VegaBStoneDust.setOreDictName("dustVegaB");
        DustSmallFertilizer.setOreDictName("dustSmallFertilizer");
        DustTinyFertilizer.setOreDictName("dustTinyFertilizer");
        CokeOvenBrickDust.setOreDictName("dustCokeOven");
        SearedBrickDust.setOreDictName("dustSearedBrick");
        CokeOvenBrick.setOreDictName("ingotCokeOvenBrick");
        BlackPlutoniumCompressedPlate.setOreDictName("compressedBlackPlutonium");
        MeitneriumCompressedPlate.setOreDictName("compressedDraconium");
        NaquadahCompressedPlate.setOreDictName("compressedNaquadah");
        QuantiumCompressedPlate.setOreDictName("compressedQuantium");
        MytrylCompressedPlate.setOreDictName("compressedMytryl");
        PalladiumCompressedPlate.setOreDictName("compressedPalladium");
        FakeMVCircuit.setOreDictName("circuitGood");
        FakeEVCircuit.setOreDictName("circuitData");
        FakeIVCircuit.setOreDictName("circuitElite");
        FakeLuVCircuit.setOreDictName("circuitMaster");
        FakeZPMCircuit.setOreDictName("circuitUltimate");
        FakeUVCircuit.setOreDictName("circuitSuperconductor");
        FakeUHVCircuit.setOreDictName("circuitInfinite");
        FakeUEVCircuit.setOreDictName("circuitBio");
        YSZCeramicPlate.setOreDictName("plateYSZ");
        GDCCeramicPlate.setOreDictName("plateGDC");
        LSFCCeramicPlate.setOreDictName("plateLSCF");
    }

    private final int metaID;
    private final int identifier;
    String OreDictName;

    private Core_List_Items(int metaID, int identifier) {
        this.metaID = metaID;
        this.identifier = identifier;
    }

    public static void registerOreDictNames() {
        Arrays.stream(Core_List_Items.values()).filter(e -> e.getOreDictName() != null).forEach(Core_List_Items::registerOreDict);
    }

    public int getMetaID() {
        return metaID;
    }

    private void registerOreDict() {
        OreDictionary.registerOre(getOreDictName(), getNonOreDictedItemStack(1));
    }

    public ItemStack getNonOreDictedItemStack(int amount) {
        switch (identifier) {
            case 0:
                return new ItemStack(Core_Items.getInstance(), amount, this.getMetaID());
            case 1:
                return new ItemStack(Core_Items2.getInstance(), amount, this.getMetaID());
            case 2:
                return new ItemStack(WoodBrickFormTool.getInstance(), amount, this.getMetaID());
            case 3:
                return new ItemStack(FakeCircuits.getInstance(), amount, this.getMetaID());
            default:
                return null;
        }
    }

    public String getOreDictName() {
        return OreDictName;
    }

    public void setOreDictName(String oreDictName) {
        OreDictName = oreDictName;
    }
}

