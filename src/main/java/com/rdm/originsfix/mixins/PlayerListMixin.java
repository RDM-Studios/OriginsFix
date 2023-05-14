package com.rdm.originsfix.mixins;

import java.io.File;
import java.net.SocketAddress;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.mojang.authlib.GameProfile;
import com.rdm.originsfix.util.MessageUtil;

import net.minecraft.network.chat.Component;
import net.minecraft.server.players.PlayerList;
import net.minecraft.server.players.UserBanList;
import net.minecraft.server.players.UserBanListEntry;

@Mixin(PlayerList.class)
public abstract class PlayerListMixin {
    @Shadow
    public static File USERBANLIST_FILE = new File("banned-players.json");
    @Shadow
    private UserBanList bans = new UserBanList(USERBANLIST_FILE);

    @Inject(method = "Lnet/minecraft/server/players/PlayerList;canPlayerLogin(Ljava/net/SocketAddress;Lcom/mojang/authlib/GameProfile;)Lnet/minecraft/network/chat/Component;", at = @At("HEAD"), cancellable = true)
    private void canPlayerLogin(SocketAddress targetAddress, GameProfile targetProfile, CallbackInfoReturnable<Component> cir) {
        if (this.bans.isBanned(targetProfile)) {
            UserBanListEntry curBannedPlayer = this.bans.get(targetProfile);
            
            if (curBannedPlayer.getExpires() != null) {
                LocalDateTime curDate = LocalDateTime.ofInstant(new Date().toInstant(), ZoneId.systemDefault());
                LocalDateTime banExpiryDate = LocalDateTime.ofInstant(bans.get(targetProfile).getExpires().toInstant(), ZoneId.systemDefault());
                Component banComponent = MessageUtil.getBanMessage(curBannedPlayer.getReason(), MessageUtil.getTimeRemaining(curDate, banExpiryDate));
                
                cir.setReturnValue(banComponent);
            }
        }
    }

}
