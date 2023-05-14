package com.rdm.originsfix.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;

// Credit goes to https://github.com/graacelin/DeathBan/blob/1.18/src/main/java/in/gracel/deathban/helpers/MessageParser.java
// Slightly refactored -- Meme Man
public class MessageUtil {
	
    public static String getDeathReason(ServerPlayer deadPlayer, DamageSource source) {
        String deathMessage = source.getLocalizedDeathMessage(deadPlayer).getString();
        return deathMessage
                .replaceFirst(deadPlayer.getName().getString(), "You")
                .replaceFirst("was", "were");
    }

    public static Component getBanMessage(String banReason, String expiryDate) {
        String banMessage = String.format(
        		"""
        			  You Died!
                Cause of death: §e%s§r
                Ban expires in: §e%s§r
                """,
                banReason, expiryDate);
        return new TranslatableComponent(banMessage);
    }

    public static String getTimeRemaining(LocalDateTime currentDate, LocalDateTime expireDate) {
        long days = currentDate.until(expireDate, ChronoUnit.DAYS);
        currentDate = currentDate.plusDays(days);
        long hours = currentDate.until(expireDate, ChronoUnit.HOURS);
        currentDate = currentDate.plusHours(hours);
        long minutes = currentDate.until(expireDate, ChronoUnit.MINUTES);
        currentDate = currentDate.plusMinutes(minutes);
        long seconds = currentDate.until(expireDate, ChronoUnit.SECONDS);

        Long[] concatenatedDate = {seconds, minutes, hours, days};
        String[] timeUnits = {" second(s)", " minute(s), ", " hour(s), ", " day(s), "};

        StringBuilder toReturn = new StringBuilder();
        for (int i = 0; i < concatenatedDate.length; i++) {
            Long time = concatenatedDate[i];
            if (i != 0 && time == 0) {
                break;
            }
            toReturn.insert(0, timeUnits[i]);
            toReturn.insert(0, concatenatedDate[i]);
        }
        return String.valueOf(toReturn);
    }

}
