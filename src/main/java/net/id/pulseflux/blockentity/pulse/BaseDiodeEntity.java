package net.id.pulseflux.blockentity.pulse;

import com.google.common.collect.ImmutableList;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.id.incubus_core.systems.Material;
import net.id.pulseflux.blockentity.PulseBlockEntity;
import net.id.pulseflux.blockentity.PulseFluxBlockEntities;
import net.id.pulseflux.systems.PulseIo;
import net.id.pulseflux.block.property.DirectionalIoProperty;
import net.id.pulseflux.block.pulse.BaseDiodeBlock;
import net.id.pulseflux.systems.IoProvider;
import net.id.pulseflux.util.LogisticsHelper;
import net.id.pulseflux.util.RelativeObjectData;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static net.id.pulseflux.block.pulse.BaseDiodeBlock.*;

public class BaseDiodeEntity extends PulseBlockEntity implements IoProvider {

    @NotNull private Optional<RelativeObjectData<PulseIo>> child = Optional.empty();
    private int renderTicks = -25;


    public BaseDiodeEntity(Material material, BlockPos pos, BlockState state) {
        super(PulseFluxBlockEntities.WORKSHOP_DIODE_TYPE, material, pos, state, 80);
    }

    @Override
    protected boolean initialize(World world, BlockPos pos, BlockState state) {
        child = getIoDir(state, IoType.OUTPUT).flatMap(dir -> LogisticsHelper.seekPulseIo(IoType.INPUT, world, pos, dir));
        return true;
    }

    @Override
    protected void tick(BlockPos pos, BlockState state) {
        if(renderTicks < getMaxRenderProgress())
            renderTicks++;

        if(allowTick()) {
            if(child.isEmpty() || !child.get().isValid()) {
                child = getIoDir(state, IoType.OUTPUT).flatMap(dir -> LogisticsHelper.seekPulseIo(IoType.INPUT, world, pos, dir));
            }
        }
    }

    @Override
    public @NotNull IoType getIoCapabilities(Direction direction) {
        return getCachedState().get(DirectionalIoProperty.IO_PROPERTIES.get(direction));
    }

    public static FabricBlockEntityTypeBuilder.Factory<BaseDiodeEntity> factory(Material material) {
        return ((blockPos, blockState) -> new BaseDiodeEntity(material, blockPos, blockState));
    }

    @Override
    public @NotNull List<Direction> getInputs(Type type) {
        return BaseDiodeBlock.getIoDir(getCachedState(), IoType.INPUT)
                .map(ImmutableList::of)
                .orElse(ImmutableList.of());
    }

    @Override
    public @NotNull List<Direction> getOutputs(Type type) {
        return BaseDiodeBlock.getIoDir(getCachedState(), IoType.OUTPUT)
                .map(ImmutableList::of)
                .orElse(ImmutableList.of());
    }

    @Override
    public @NotNull List<RelativeObjectData<PulseIo>> getChildren() {
        return child.map(List::of).orElse(Collections.emptyList());
    }

    @Override
    public int getRenderProgress() {
        return Math.abs(renderTicks);
    }

    @Override
    public @NotNull Category getDeviceCategory() {
        return Category.CONNECTOR;
    }

    @Override
    public void load(NbtCompound nbt) {
        super.load(nbt);
        renderTicks = nbt.getInt("renderProgress");

    }

    @Override
    public void save(NbtCompound nbt) {
        nbt.putInt("renderProgress", renderTicks);
        super.save(nbt);
    }
}
