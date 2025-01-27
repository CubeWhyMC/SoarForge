package me.eldodebug.soar;

import lombok.extern.slf4j.Slf4j;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Slf4j
@Mod(modid = ForgeMod.modId, name = "SoarClient", version = "unknown", clientSideOnly = true)
public class ForgeMod {
    public static final String modId = "mcforge";

    @Mod.Instance(ForgeMod.modId)
    public static ForgeMod instance;

    @Mod.EventHandler
    public void init(FMLPreInitializationEvent event) {
        log.info("FML Init");
    }
}
