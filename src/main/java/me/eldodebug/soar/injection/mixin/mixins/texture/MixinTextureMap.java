package me.eldodebug.soar.injection.mixin.mixins.texture;

import me.eldodebug.soar.Soar;
import me.eldodebug.soar.management.event.impl.EventSwitchTexture;
import net.minecraft.client.renderer.texture.TextureMap;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextureMap.class)
public class MixinTextureMap {

    @Inject(method = "loadTextureAtlas", at = @At("RETURN"))
    public void preLoadTextureAtlas(CallbackInfo ci) {
        if (Soar.getInstance().getEventManager() != null) {
            new EventSwitchTexture().call();
        }
    }
}
