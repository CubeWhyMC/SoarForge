package me.eldodebug.soar.ui.comp.impl;

import me.eldodebug.soar.Soar;
import me.eldodebug.soar.management.color.AccentColor;
import me.eldodebug.soar.management.color.ColorManager;
import me.eldodebug.soar.management.color.palette.ColorPalette;
import me.eldodebug.soar.management.color.palette.ColorType;
import me.eldodebug.soar.management.file.FileManager;
import me.eldodebug.soar.management.language.TranslateText;
import me.eldodebug.soar.management.mods.settings.impl.ImageSetting;
import me.eldodebug.soar.management.nanovg.NanoVGManager;
import me.eldodebug.soar.management.nanovg.font.Fonts;
import me.eldodebug.soar.management.nanovg.font.Icon;
import me.eldodebug.soar.ui.comp.Comp;
import me.eldodebug.soar.utils.Multithreading;
import me.eldodebug.soar.utils.file.FileUtils;
import me.eldodebug.soar.utils.mouse.MouseUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CompImageSelect extends Comp {

    private ImageSetting imageSetting;

    public CompImageSelect(float x, float y, ImageSetting imageSetting) {
        super(x, y);
        this.imageSetting = imageSetting;
    }

    public CompImageSelect(ImageSetting imageSetting) {
        super(0, 0);
        this.imageSetting = imageSetting;
    }

    @Override
    public void draw(int mouseX, int mouseY, float partialTicks) {

        Soar instance = Soar.getInstance();
        NanoVGManager nvg = instance.getNanoVGManager();
        ColorManager colorManager = instance.getColorManager();
        AccentColor accentColor = colorManager.getCurrentColor();
        ColorPalette palette = colorManager.getPalette();

        String name = imageSetting.getImage() == null ? TranslateText.NONE.getText() : imageSetting.getImage().getName();
        float nameWidth = nvg.getTextWidth(name, 9, Fonts.REGULAR);

        nvg.drawGradientRoundedRect(this.getX(), this.getY(), 16, 16, 4, accentColor.getColor1(), accentColor.getColor2());
        nvg.drawText(name, this.getX() - nameWidth - 5, this.getY() + 4, palette.getFontColor(ColorType.DARK), 9, Fonts.REGULAR);
        nvg.drawCenteredText(Icon.FOLDER, this.getX() + 8, this.getY() + 2.5F, Color.WHITE, 10, Fonts.ICON);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {

        if (MouseUtils.isInside(mouseX, mouseY, this.getX(), this.getY(), 16, 16) && mouseButton == 0) {

            Multithreading.runAsync(() -> {

                File image = FileUtils.selectImageFile();

                if (image != null) {

                    FileManager fileManager = Soar.getInstance().getFileManager();
                    File cacheDir = new File(fileManager.getCacheDir(), "custom-image");

                    fileManager.createDir(cacheDir);

                    File newImage = new File(cacheDir, image.getName());

                    try {
                        FileUtils.copyFile(image, newImage);
                    } catch (IOException e) {
                    }

                    imageSetting.setImage(newImage);
                }
            });
        }
    }
}
