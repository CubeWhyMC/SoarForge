package me.eldodebug.soar.injection.mixin.mixins.block;

import me.eldodebug.soar.injection.interfaces.ICachedHashcode;
import net.minecraft.block.properties.PropertyInteger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(PropertyInteger.class)
public class MixinPropertyInteger {

    @Overwrite
    public int hashCode() {
        return ((ICachedHashcode) ((Object) this)).getCachedHashcode();
    }
}