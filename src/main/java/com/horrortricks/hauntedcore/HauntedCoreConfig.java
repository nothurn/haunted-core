package com.horrortricks.hauntedcore;

import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.DoubleValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;

public final class HauntedCoreConfig {
   private static final Builder BUILDER = new Builder();
   public static final BooleanValue MASTER_ENABLED = BUILDER.comment(
         new String[]{
            "Master kill switch for the whole HorrorTricks family's shared behavior (fake presence,",
            "family-wide event spacing). Individual mods still have their own enabled switch too - this",
            "one gates the shared core underneath all of them."
         }
      )
      .define("masterEnabled", true);
   public static final DoubleValue GLOBAL_INTENSITY = BUILDER.comment(
         new String[]{
            "Multiplier consumer mods read to scale their own event frequency up or down.",
            "1.0 is unchanged, 0.0 effectively silences frequency-based events, 2.0 doubles them.",
            "Each consumer mod decides for itself exactly how (or whether) it applies this."
         }
      )
      .defineInRange("globalIntensity", 1.0, 0.0, 2.0);
   public static final IntValue GLOBAL_COOLDOWN_SECONDS = BUILDER.comment(
         new String[]{
            "Minimum seconds between any two HorrorTricks family events (see EventCoordinator) - default",
            "of 90 keeps two different mods' events from ever landing right on top of each other."
         }
      )
      .defineInRange("globalCooldownSeconds", 90, 0, Integer.MAX_VALUE);
   public static final IntValue MIN_PRESENCE_DURATION_TICKS = BUILDER.comment(
         new String[]{
            "Minimum ticks (20 ticks/second) the shared fake player identity stays \"present\" once a",
            "presence window begins. Default of 12000 is 10 minutes."
         }
      )
      .defineInRange("minPresenceDurationTicks", 12000, 20, Integer.MAX_VALUE);
   public static final IntValue MAX_PRESENCE_DURATION_TICKS = BUILDER.comment(
         new String[]{
            "Maximum ticks (20 ticks/second) the shared fake player identity stays \"present\" once a",
            "presence window begins. Default of 48000 is 40 minutes."
         }
      )
      .defineInRange("maxPresenceDurationTicks", 48000, 20, Integer.MAX_VALUE);
   public static final ModConfigSpec SPEC = BUILDER.build();

   private HauntedCoreConfig() {
   }

   static {
      BUILDER.push("general");
      BUILDER.pop();
      BUILDER.push("presence");
      BUILDER.pop();
   }
}
