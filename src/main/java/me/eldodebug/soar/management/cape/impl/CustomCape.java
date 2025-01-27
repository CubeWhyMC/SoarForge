package me.eldodebug.soar.management.cape.impl;

import me.eldodebug.soar.management.cape.CapeCategory;
import net.minecraft.util.ResourceLocation;

import java.io.File;

public class CustomCape extends Cape {

    private File sample;

    public CustomCape(String name, File sample, ResourceLocation cape, boolean premium, CapeCategory category) {
        super(name, cape, premium, category);
        this.sample = sample;
    }

    public File getSample() {
        return sample;
    }
}
