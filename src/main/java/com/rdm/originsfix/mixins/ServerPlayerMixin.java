package com.rdm.originsfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.mojang.authlib.GameProfile;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin({ServerPlayer.class, LivingEntity.class})
public abstract class ServerPlayerMixin extends Player {
	@Shadow
	protected ItemStack useItem;

	public ServerPlayerMixin(Level pLevel, BlockPos pPos, float pYRot, GameProfile pGameProfile) {
		super(pLevel, pPos, pYRot, pGameProfile);
	}
	
	@Inject(method = "Lnet/minecraft/server/level/ServerPlayer;completeUsingItem()V", at = @At("HEAD"), cancellable = true)
	private void of$completeUsingItem() {
		if (!useItem.isEmpty()) {
			if (useItem.getHoverName().getContents().contains("rune")) useItem.shrink(1); //TODO Hardcoded datapack item method! Bad >:(
		}
	}

}
