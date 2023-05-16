package com.rdm.originsfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {

	public ServerPlayerMixin(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
		super(pLevel, pPos, pYRot, pGameProfile);
	}
	
	@Inject(method = "Lnet/minecraft/server/level/ServerPlayer;completeUsingItem()V", at = @At("HEAD"), cancellable = true)
	private void of$completeUsingItem(CallbackInfo info) {
		if (!useItem.isEmpty()) {
			if (useItem.getHoverName().getContents().contains("rune")) useItem.shrink(1); //TODO Hardcoded datapack item method! Bad >:(
		}
	}

}
