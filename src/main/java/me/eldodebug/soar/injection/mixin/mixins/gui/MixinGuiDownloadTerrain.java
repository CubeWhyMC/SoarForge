package me.eldodebug.soar.injection.mixin.mixins.gui;

import me.eldodebug.soar.management.event.impl.EventLoadWorld;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiDownloadTerrain.class)
public class MixinGuiDownloadTerrain extends GuiScreen {

    @Overwrite
    public void initGui() {
        this.buttonList.clear();
        new EventLoadWorld().call();
    }
}
