package com.horrortricks.hauntedcore.presence;

import com.horrortricks.hauntedcore.HauntedCoreConfig;
import com.horrortricks.hauntedcore.identity.FakeIdentity;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.NeoForge;

public final class PresenceState {
   private static long tickCounter = 0L;
   private static boolean present = false;
   private static long plannedEndTick = -1L;
   private static String activeName;
   private static final Map<UUID, String> SERVER_ACTIVE_NAMES = new ConcurrentHashMap<>();

   private PresenceState() {
   }

   public static boolean isPresent() {
      return present;
   }

   public static String getFakeName() {
      return present ? activeName : null;
   }

   public static boolean isPresent(UUID playerId) {
      return SERVER_ACTIVE_NAMES.containsKey(playerId) || present;
   }

   public static String getFakeName(UUID playerId) {
      String remoteName = SERVER_ACTIVE_NAMES.get(playerId);
      return remoteName != null ? remoteName : getFakeName();
   }

   public static void setServerPresence(UUID playerId, String name) {
      String normalized = name == null ? "" : name.trim();
      if (normalized.isEmpty()) {
         SERVER_ACTIVE_NAMES.remove(playerId);
      } else if (normalized.length() <= 32) {
         SERVER_ACTIVE_NAMES.put(playerId, normalized);
      }
   }

   public static void clearServerPresence(UUID playerId) {
      SERVER_ACTIVE_NAMES.remove(playerId);
   }

   public static void begin(RandomSource random, String currentPlayerName) {
      if (!present) {
         activeName = FakeIdentity.get(random, currentPlayerName);
         present = true;
         int min = (Integer)HauntedCoreConfig.MIN_PRESENCE_DURATION_TICKS.get();
         int max = Math.max(min, (Integer)HauntedCoreConfig.MAX_PRESENCE_DURATION_TICKS.get());
         int duration = randomInclusive(random, min, max);
         plannedEndTick = tickCounter + duration;
         NeoForge.EVENT_BUS.post(new PresenceStartEvent(activeName));
      }
   }

   public static void end() {
      if (present) {
         present = false;
         plannedEndTick = -1L;
         NeoForge.EVENT_BUS.post(new PresenceEndEvent());
         activeName = null;
      }
   }

   public static void tick() {
      tickCounter++;
      if (present && tickCounter >= plannedEndTick) {
         end();
      }
   }

   public static void resetSession() {
      tickCounter = 0L;
      present = false;
      plannedEndTick = -1L;
      activeName = null;
   }

   private static int randomInclusive(RandomSource random, int min, int max) {
      long range = (long)max - min + 1L;
      return min + (int)Math.floorMod(random.nextLong(), range);
   }
}
