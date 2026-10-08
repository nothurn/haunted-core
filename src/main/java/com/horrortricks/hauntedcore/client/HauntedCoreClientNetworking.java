package com.horrortricks.hauntedcore.client;

import com.horrortricks.hauntedcore.HauntedCoreNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.registration.NetworkRegistry;

public final class HauntedCoreClientNetworking {
   private HauntedCoreClientNetworking() {
   }

   public static void syncPresence(String name) {
      ClientPacketListener connection = Minecraft.getInstance().getConnection();
      if (connection != null && NetworkRegistry.hasChannel(connection, HauntedCoreNetworking.PresenceSyncPayload.TYPE.id())) {
         PacketDistributor.sendToServer(new HauntedCoreNetworking.PresenceSyncPayload(name == null ? "" : name));
      }
   }
}
