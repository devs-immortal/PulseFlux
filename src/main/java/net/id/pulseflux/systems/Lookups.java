package net.id.pulseflux.systems;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.fabricmc.fabric.api.lookup.v1.item.ItemApiLookup;
import net.id.incubus_core.IncubusCore;
import net.id.pulseflux.PulseFlux;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// This is here for the move of pulse stuff from incubus core to pulseFlux
// It may be better replaced by something else, maybe.
public class Lookups {
    public static final BlockApiLookup<PulseIo, @NotNull Direction> PULSE =
            BlockApiLookup.get(PulseFlux.locate("pulse_system_b"), PulseIo.class, Direction.class);

    public static final ItemApiLookup<PulseIo, @Nullable Void> ITEM_PULSE =
            ItemApiLookup.get(PulseFlux.locate("pulse_system_i"), PulseIo.class, Void.class);
}
