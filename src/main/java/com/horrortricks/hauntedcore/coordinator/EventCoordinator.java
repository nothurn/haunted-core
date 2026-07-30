package com.horrortricks.hauntedcore.coordinator;

import com.horrortricks.hauntedcore.HauntedCoreConfig;

public final class EventCoordinator {
   private static final long NEVER = -4611686018427387904L;
   private static long tickCounter = 0L;
   private static long lastEventTick = -4611686018427387904L;

   private EventCoordinator() {
   }

   public static void reportEvent() {
      lastEventTick = tickCounter;
   }

   public static boolean isQuietPeriodOver() {
      long cooldownTicks = ((Integer)HauntedCoreConfig.GLOBAL_COOLDOWN_SECONDS.get()).intValue() * 20L;
      return tickCounter - lastEventTick >= cooldownTicks;
   }

   public static void tick() {
      tickCounter++;
   }

   public static void resetSession() {
      tickCounter = 0L;
      lastEventTick = -4611686018427387904L;
   }
}
