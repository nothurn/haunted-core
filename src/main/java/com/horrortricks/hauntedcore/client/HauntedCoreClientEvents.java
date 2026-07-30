package com.horrortricks.hauntedcore.client;

import com.horrortricks.hauntedcore.coordinator.EventCoordinator;
import com.horrortricks.hauntedcore.identity.FakeIdentity;
import com.horrortricks.hauntedcore.presence.PresenceState;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "hauntedcore", value = Dist.CLIENT)
public final class HauntedCoreClientEvents {
   private HauntedCoreClientEvents() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.player != null && minecraft.level != null) {
         PresenceState.tick();
         EventCoordinator.tick();
      }
   }

   @SubscribeEvent
   public static void onLoggingIn(LoggingIn event) {
      FakeIdentity.resetSession();
      PresenceState.resetSession();
      EventCoordinator.resetSession();
   }
}
