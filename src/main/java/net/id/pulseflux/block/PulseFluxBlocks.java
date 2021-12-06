package net.id.pulseflux.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.id.incubus_core.util.RegistryQueue;
import net.id.pulseflux.block.pulse.BaseDiodeBlock;
import net.id.pulseflux.block.pulse.CreativePulseSourceBlock;
import net.id.pulseflux.block.transport.FluidPipeBlock;
import net.id.pulseflux.block.transport.PipeBlock;
import net.id.pulseflux.registry.PulseFluxRegistryQueues;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import static net.id.pulseflux.block.PFBlockActions.*;
import static net.id.pulseflux.PulseFlux.locate;
import static net.id.pulseflux.arrp.PulseFluxResources.*;
import static net.id.pulseflux.arrp.AssetGen.*;

public class PulseFluxBlocks {

    private static RegistryQueue.Action<Block> generateLocale(String name) {
        return ((identifier, block) -> EN_US.block(identifier, name));
    }

    private static final RegistryQueue.Action<Block> generateAssets = (id, block) -> createBlockAssets(id);

    /**
     * POWER
     */

    private static final RegistryQueue.Action<Block> generateDiodeAssets = (id, block) -> createDiodeAssets(id);

    public static final BaseDiodeBlock WORKSHOP_DIODE = add("workshop_diode", new BaseDiodeBlock(FabricBlockSettings.copy(Blocks.IRON_BLOCK)), generateDiodeAssets, generateLocale("Workshop Diode"));

    public static final CreativePulseSourceBlock CREATIVE_PULSE_SOURCE = add("creative_pulse_source", new CreativePulseSourceBlock(FabricBlockSettings.copy(Blocks.NETHERITE_BLOCK)), generateDiodeAssets, generateLocale("Creative Pulse Source"));

    /**
     * LOGISTICS
     */

    private static final RegistryQueue.Action<Block> generatePipeAssets = (id, block) -> createPipeAssets(id);

    public static final PipeBlock WOODEN_FLUID_PIPE = add("wooden_fluid_pipe", new FluidPipeBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG)), translucentRenderLayer, generatePipeAssets, generateLocale("Wooden Pipe"));

    /**
     * RESOURCES
     */

    public static final Block HSLA_STEEL_BLOCK = add("hsla_steel_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)), generateAssets, generateLocale("HSLA Steel Block"));



    public static void init() {
        PulseFluxRegistryQueues.BLOCK.register();
    }

    @SafeVarargs
    private static <V extends Block> V add(String id, V block, RegistryQueue.Action<? super V>... additionalActions) {
        return PulseFluxRegistryQueues.BLOCK.add(locate(id), block, additionalActions);
    }
}
