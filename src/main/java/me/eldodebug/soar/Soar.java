package me.eldodebug.soar;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import me.eldodebug.soar.management.account.AccountManager;
import me.eldodebug.soar.management.cape.CapeManager;
import me.eldodebug.soar.management.changelog.ChangelogManager;
import me.eldodebug.soar.management.color.ColorManager;
import me.eldodebug.soar.management.command.CommandManager;
import me.eldodebug.soar.management.download.DownloadManager;
import me.eldodebug.soar.management.event.EventManager;
import me.eldodebug.soar.management.file.FileManager;
import me.eldodebug.soar.management.language.LanguageManager;
import me.eldodebug.soar.management.mods.ModManager;
import me.eldodebug.soar.management.mods.impl.GlobalSettingsMod;
import me.eldodebug.soar.management.music.MusicManager;
import me.eldodebug.soar.management.nanovg.NanoVGManager;
import me.eldodebug.soar.management.notification.NotificationManager;
import me.eldodebug.soar.management.profile.ProfileManager;
import me.eldodebug.soar.management.quickplay.QuickPlayManager;
import me.eldodebug.soar.management.screenshot.ScreenshotManager;
import me.eldodebug.soar.management.security.SecurityFeatureManager;
import me.eldodebug.soar.management.waypoint.WaypointManager;
import me.eldodebug.soar.utils.OptifineUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class Soar {

    private static Soar instance = null;

    public static Soar getInstance() {
        if (instance == null) {
            instance = new Soar();
        }
        return instance;
    }

    private final Minecraft mc = Minecraft.getMinecraft();
    @Getter
    private SoarAPI api;

    @Getter
    private final String name;
    @Getter
    private final String version;

    @Setter
    @Getter
    private NanoVGManager nanoVGManager;
    @Getter
    private FileManager fileManager;
    @Getter
    private DownloadManager downloadManager;
    @Getter
    private LanguageManager languageManager;
    @Getter
    private AccountManager accountManager;
    @Getter
    private EventManager eventManager;
    @Getter
    private ModManager modManager;
    @Getter
    private CapeManager capeManager;
    @Getter
    private ColorManager colorManager;
    @Getter
    private ProfileManager profileManager;
    @Getter
    private MusicManager musicManager;
    @Getter
    private CommandManager commandManager;
    @Getter
    private ScreenshotManager screenshotManager;
    @Getter
    private NotificationManager notificationManager;
    @Getter
    private SecurityFeatureManager securityFeatureManager;
    @Getter
    private QuickPlayManager quickPlayManager;
    @Getter
    private ChangelogManager changelogManager;
    @Getter
    private WaypointManager waypointManager;

    public boolean hasOptifine = false;

    public Soar() {
        name = "Soar";
        version = "7.1.2";
        try {
            Class.forName("optifine.Patcher");
            hasOptifine = true;
        } catch (ClassNotFoundException ignored) {
        }
    }

    public void postStart() {

        OptifineUtils.disableFastRender();
        this.removeOptifineZoom();

        fileManager = new FileManager();
        downloadManager = new DownloadManager();
        languageManager = new LanguageManager();
        accountManager = new AccountManager();
        eventManager = new EventManager();
        modManager = new ModManager();

        modManager.init();

        capeManager = new CapeManager();
        colorManager = new ColorManager();
        profileManager = new ProfileManager();
        musicManager = new MusicManager();

        api = new SoarAPI();
        api.init();

        commandManager = new CommandManager();
        screenshotManager = new ScreenshotManager();
        notificationManager = new NotificationManager();
        securityFeatureManager = new SecurityFeatureManager();
        quickPlayManager = new QuickPlayManager();
        changelogManager = new ChangelogManager();
        waypointManager = new WaypointManager();

        eventManager.register(new SoarHandler());

        setupLibraryPath();

        GlobalSettingsMod.getInstance().setToggled(true);
        mc.updateDisplay();
    }

    public void stop() {
        profileManager.save();
        accountManager.save();
    }

    private void removeOptifineZoom() {
        if (hasOptifine) {
            try {
                this.unregisterKeybind((KeyBinding) GameSettings.class.getField("ofKeyBindZoom").get(mc.gameSettings));
            } catch (Exception e) {
                log.error("Failed to unregister zoom key", e);
            }
        }
    }

    private void unregisterKeybind(KeyBinding key) {
        if (Arrays.asList(mc.gameSettings.keyBindings).contains(key)) {
            mc.gameSettings.keyBindings = ArrayUtils.remove(mc.gameSettings.keyBindings, Arrays.asList(mc.gameSettings.keyBindings).indexOf(key));
            key.setKeyCode(0);
        }
    }

    private void setupLibraryPath() {

        File cefDir = new File(fileManager.getExternalDir(), "cef");

        try {
            System.setProperty("jcef.path", cefDir.getCanonicalPath());
        } catch (IOException e) {
            log.error("Failed to setup jcef library path", e);
        }
    }
}
