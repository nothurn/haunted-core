package com.horrortricks.hauntedcore;

import com.horrortricks.hauntedcore.presence.PresenceState;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public final class HauntedCoreNetworking {
   private HauntedCoreNetworking() {
   }

   public static void onRegisterPayloadHandlers(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("1").optional();
      registrar.playToServer(PresenceSyncPayload.TYPE, PresenceSyncPayload.STREAM_CODEC, HauntedCoreNetworking::handlePresenceSync);
   }

   private static void handlePresenceSync(PresenceSyncPayload payload, IPayloadContext context) {
      if (context.player() instanceof ServerPlayer player) {
         PresenceState.setServerPresence(player.getUUID(), payload.name());
      }
   }

   public record PresenceSyncPayload(String name) implements CustomPacketPayload {
      public static final Type<PresenceSyncPayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath("hauntedcore", "presence_sync"));
      public static final StreamCodec<RegistryFriendlyByteBuf, PresenceSyncPayload> STREAM_CODEC = StreamCodec.composite(
         ByteBufCodecs.STRING_UTF8,
         PresenceSyncPayload::name,
         PresenceSyncPayload::new
      );

      @Override
      public Type<PresenceSyncPayload> type() {
         return TYPE;
      }
   }
}
