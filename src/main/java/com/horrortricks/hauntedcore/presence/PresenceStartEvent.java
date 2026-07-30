package com.horrortricks.hauntedcore.presence;

import net.neoforged.bus.api.Event;

public final class PresenceStartEvent extends Event {
   private final String fakeName;

   public PresenceStartEvent(String fakeName) {
      this.fakeName = fakeName;
   }

   public String getFakeName() {
      return this.fakeName;
   }
}
