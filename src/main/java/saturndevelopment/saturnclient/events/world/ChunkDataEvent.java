/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.events.world;

import net.minecraft.world.chunk.WorldChunk;
import saturndevelopment.saturnclient.utils.misc.Pool;

/**
 * @implNote Shouldn't be put in a {@link Pool} to avoid a race-condition, or in a {@link ThreadLocal} as it is shared between threads.
 * @author Crosby
 */
public record ChunkDataEvent(WorldChunk chunk) {}
