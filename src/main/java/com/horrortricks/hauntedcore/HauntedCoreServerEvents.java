package com.horrortricks.hauntedcore;

import com.horrortricks.hauntedcore.presence.PresenceState;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;

@EventBusSubscriber(modid = HauntedCoreMod.MOD_ID)
public final class HauntedCoreServerEvents {
   private HauntedCoreServerEvents() {
   }

   @SubscribeEvent
   public static void onPlayerLoggedOut(PlayerLoggedOutEvent event) {
      if (event.getEntity() instanceof ServerPlayer player) {
         PresenceState.clearServerPresence(player.getUUID());
      }
   }
}
