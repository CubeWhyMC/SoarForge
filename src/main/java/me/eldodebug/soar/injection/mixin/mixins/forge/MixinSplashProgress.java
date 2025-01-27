package me.eldodebug.soar.injection.mixin.mixins.forge;

import lombok.extern.slf4j.Slf4j;
import me.eldodebug.soar.Soar;
import me.eldodebug.soar.management.nanovg.NanoVGManager;
import net.minecraftforge.fml.client.SplashProgress;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Slf4j
@Mixin(value = SplashProgress.class, remap = false)
public abstract class MixinSplashProgress {

//    /**
//     * @author qby
//     * @reason migrate to forge
//     */
//    @Overwrite(remap=false)
//    public static void start() {
//    }
}
