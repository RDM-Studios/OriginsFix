package com.rdm.originsfix.server.events;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import com.rdm.originsfix.OriginsFix;
import com.rdm.originsfix.manager.OFConfigManager;
import com.rdm.originsfix.util.MessageUtil;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.UserBanListEntry;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = OriginsFix.MODID, bus = Bus.FORGE)
public class OFMiscServerEvents {

	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerTickEvent(PlayerTickEvent event) {
		Player player = event.player;

		if (event.side == LogicalSide.SERVER) {
			if (player != null && player instanceof ServerPlayer serverPlayer) {
				double enforcedMaxHealth = OFConfigManager.MAIN_COMMON.maxPlayerHealth.get() * 2;

				if (serverPlayer.getHealth() > enforcedMaxHealth) serverPlayer.setHealth((float) enforcedMaxHealth);

				if (serverPlayer.getAttributeBaseValue(Attributes.MAX_HEALTH) > enforcedMaxHealth || serverPlayer.getAttributeValue(Attributes.MAX_HEALTH) > enforcedMaxHealth) {
					serverPlayer.getAttribute(Attributes.MAX_HEALTH).setBaseValue(enforcedMaxHealth);
				}
				
				if (!serverPlayer.getAttribute(Attributes.MAX_HEALTH).getModifiers().isEmpty()) serverPlayer.getAttribute(Attributes.MAX_HEALTH).removeModifiers();
			}
		}
	}

	@SuppressWarnings("resource")
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public static void onPlayerDeathEvent(LivingDeathEvent event) {
		LivingEntity target = event.getEntityLiving();

		if (!target.getCommandSenderWorld().isClientSide && target instanceof ServerPlayer serverPlayer && !serverPlayer.getServer().isSingleplayer()) {
			MinecraftServer curServer = serverPlayer.getServer();
			LocalDateTime curDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
			Date now = new Date();
			UserBanListEntry entry = new UserBanListEntry(serverPlayer.getGameProfile(), now, "Death", DateUtils.addMinutes(now, 15), MessageUtil.getDeathReason(serverPlayer, event.getSource()).replaceFirst("You", serverPlayer.getGameProfile().getName()));

			curServer.getPlayerList().getBans().add(entry);
			serverPlayer.connection.disconnect(MessageUtil.getBanMessage(entry.getReason(), MessageUtil.getTimeRemaining(curDate, curDate.plusMinutes(15))));
		}
	}

}
