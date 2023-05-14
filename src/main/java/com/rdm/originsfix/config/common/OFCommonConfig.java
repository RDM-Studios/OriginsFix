package com.rdm.originsfix.config.common;

import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;

public class OFCommonConfig {
	public final DoubleValue maxPlayerHealth;
	
	public OFCommonConfig(Builder configBuilder) {
		configBuilder.push("Player Health");
		maxPlayerHealth = configBuilder
				.comment("The maximum amount of HP a player can attain. Overrides everything to ensure that the specified health limit is enforced and applied.")
				.defineInRange("Max Player Health (Hearts)", 20.0F, 1F, 5000F);
		configBuilder.pop();
	}

}
