package com.rdm.originsfix;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.rdm.originsfix.manager.OFModManager;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(OriginsFix.MODID)
public class OriginsFix {
	public static final String MODID = "originsfix";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static OriginsFix INSTANCE;
    public static IEventBus FORGE_BUS;
    public static IEventBus MOD_BUS;

    public OriginsFix() {
    	INSTANCE = this;
    	FORGE_BUS = MinecraftForge.EVENT_BUS;
    	MOD_BUS = FMLJavaModLoadingContext.get().getModEventBus();
    	
    	if (FORGE_BUS != null && MOD_BUS != null) OFModManager.registerAll();
    }
}
