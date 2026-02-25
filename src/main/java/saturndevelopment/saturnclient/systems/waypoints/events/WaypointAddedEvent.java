/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.systems.waypoints.events;

import saturndevelopment.saturnclient.systems.waypoints.Waypoint;

public record WaypointAddedEvent(Waypoint waypoint) {
}
