package com.rdm.originsfix.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	@Shadow
	protected ItemStack useItem;
	
	public LivingEntityMixin(EntityType<?> pEntityType, Level pLevel) {
		super(pEntityType, pLevel);
	}

	@Inject(method = "Lnet/minecraft/world/entity/LivingEntity;completeUsingItem()V", at = @At("HEAD"), cancellable = true)
	private void of$completeUsingItem() {
		if (!useItem.isEmpty()) {
			if (useItem.getHoverName().getContents().contains("rune")) useItem.shrink(1); //TODO Hardcoded datapack item method! Bad >:(
		}
	}

}
