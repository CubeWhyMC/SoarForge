package me.eldodebug.soar.management.mods.settings.impl;

import me.eldodebug.soar.Soar;
import me.eldodebug.soar.management.language.TranslateText;
import me.eldodebug.soar.management.mods.Mod;
import me.eldodebug.soar.management.mods.settings.Setting;

import java.io.File;

public class ImageSetting extends Setting {

    private File image;

    public ImageSetting(TranslateText nameTranslate, Mod parent) {
        super(nameTranslate, parent);

        this.image = null;

        Soar.getInstance().getModManager().addSettings(this);
    }

    @Override
    public void reset() {
        this.image = null;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }
}
