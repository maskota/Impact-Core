package com.impact.core;

import com.impact.mods.gregtech.enums.BiomeOreRegister;
import com.impact.util.PositionObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Impact_API {

  /**
   * The OreGeneration Map
   */
  public static final Map<String, BiomeOreRegister> sBiomeOres = new HashMap<>();

  /**
   * The Space Satellite Frequencies
   */
  public static final Map<String, int[]> sSpaceSatellite = new ConcurrentHashMap<>();

  /**
   * The MultiBlocks connect with Communication Tower Frequencies
   */
  public static final Map<String, int[]> sCommunicationTower = new ConcurrentHashMap<>();

  /**
   * The Space Elevator Frequencies
   */
  public static final Map<String, int[]> sElevatorSpace = new ConcurrentHashMap<>();
  
  /**
   * The Space Elevator Frequencies
   */
  public static final Map<String, PositionObject> sAerostat = new LinkedHashMap<>();

  public static final Map<String, int[]> sSpacePortConnector = new LinkedHashMap<>();
}