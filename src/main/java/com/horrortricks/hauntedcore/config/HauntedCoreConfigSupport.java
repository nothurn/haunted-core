package com.horrortricks.hauntedcore.config;

import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public final class HauntedCoreConfigSupport {
   public static final String GENERAL_CATEGORY = "general";

   private HauntedCoreConfigSupport() {
   }

   public static void pushGeneralCategory(Builder builder) {
      builder.push("general");
   }
}
