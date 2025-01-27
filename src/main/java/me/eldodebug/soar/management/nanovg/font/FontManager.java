package me.eldodebug.soar.management.nanovg.font;

import lombok.extern.slf4j.Slf4j;
import me.eldodebug.soar.utils.IOUtils;
import org.lwjgl.nanovg.NanoVG;

import java.nio.ByteBuffer;

@Slf4j
public class FontManager {

    public void init(long nvg) {
        for (Font f : Fonts.getFonts()) {
            loadFont(nvg, f);
        }
    }

    private void loadFont(long nvg, Font font) {

        if (font.isLoaded()) {
            return;
        }

        int loaded = -1;

        try {
            ByteBuffer buffer = IOUtils.resourceToByteBuffer(font.getResourceLocation());
            loaded = NanoVG.nvgCreateFontMem(nvg, font.getName(), buffer, false);
            font.setBuffer(buffer);
        } catch (Exception e) {
            log.error("Failed to load font", e);
        }

        if (loaded == -1) {
            throw new RuntimeException("Failed to init font " + font.getName());
        } else {
            font.setLoaded(true);
        }
    }
}
