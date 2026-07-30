package com.horrortricks.hauntedcore.presence;

import com.horrortricks.hauntedcore.HauntedCoreConfig;
import com.horrortricks.hauntedcore.identity.FakeIdentity;
import net.minecraft.util.RandomSource;
import net.neoforged.neoforge.common.NeoForge;

public final class PresenceState {
   private static long tickCounter = 0L;
   private static boolean present = false;
   private static long plannedEndTick = -1L;

   private PresenceState() {
   }

   public static boolean isPresent() {
      return present;
   }

   public static String getFakeName() {
      return present ? FakeIdentity.get(null, null) : null;
   }

   public static void begin(RandomSource random, String currentPlayerName) {
      if (!present) {
         String name = FakeIdentity.get(random, currentPlayerName);
         present = true;
         int min = (Integer)HauntedCoreConfig.MIN_PRESENCE_DURATION_TICKS.get();
         int max = Math.max(min, (Integer)HauntedCoreConfig.MAX_PRESENCE_DURATION_TICKS.get());
         int duration = min + random.nextInt(max - min + 1);
         plannedEndTick = tickCounter + duration;
         NeoForge.EVENT_BUS.post(new PresenceStartEvent(name));
      }
   }

   public static void end() {
      if (present) {
         present = false;
         plannedEndTick = -1L;
         NeoForge.EVENT_BUS.post(new PresenceEndEvent());
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
   }
}
