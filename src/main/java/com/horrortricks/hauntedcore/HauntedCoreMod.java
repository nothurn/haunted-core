package com.horrortricks.hauntedcore;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import org.slf4j.Logger;

@Mod("hauntedcore")
public class HauntedCoreMod {
   public static final String MOD_ID = "hauntedcore";
   private static final Logger LOGGER = LogUtils.getLogger();

   public HauntedCoreMod(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.CLIENT, HauntedCoreConfig.SPEC);
      LOGGER.info("Haunted Core initialized.");
   }
}
