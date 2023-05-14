package com.rdm.originsfix.manager;

import org.apache.commons.lang3.tuple.Pair;

import com.rdm.originsfix.config.common.OFCommonConfig;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class OFConfigManager {
	public static final ForgeConfigSpec MAIN_COMMON_SPEC;
	public static final OFCommonConfig MAIN_COMMON;
	
	static {
		final Pair<OFCommonConfig, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(OFCommonConfig::new);
		
		MAIN_COMMON_SPEC = commonSpecPair.getRight();
		MAIN_COMMON = commonSpecPair.getLeft();
	}
	
	protected static void registerConfig() {
		registerCommonConfig();
	}
	
	private static void registerCommonConfig() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MAIN_COMMON_SPEC);
	}

}
