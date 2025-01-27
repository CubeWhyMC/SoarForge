package me.eldodebug.soar.injection.mixin.mixins.item;

import me.eldodebug.soar.injection.interfaces.IMixinItemFood;
import net.minecraft.item.ItemFood;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemFood.class)
public class MixinItemFood implements IMixinItemFood {

    @Shadow
    private int potionId;

    @Override
    public int getPotionID() {
        return potionId;
    }
}
