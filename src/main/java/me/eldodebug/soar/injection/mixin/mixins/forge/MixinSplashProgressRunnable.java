package me.eldodebug.soar.injection.mixin.mixins.forge;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.eldodebug.soar.Soar;
import me.eldodebug.soar.gui.GuiSplashScreen;
import me.eldodebug.soar.management.nanovg.NanoVGManager;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Slf4j
@Mixin(targets = "net.minecraftforge.fml.client.SplashProgress$3", remap = false)
public class MixinSplashProgressRunnable {
    @SneakyThrows
    @Inject(method = "run()V", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private void run(@NotNull CallbackInfo callbackInfo) {
//        callbackInfo.cancel();
        log.debug("Splash progress running.");

//        new GuiSplashScreen().draw();
    }
}
