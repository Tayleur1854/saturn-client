/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.gui.themes.meteor.widgets.pressable;

import saturndevelopment.saturnclient.gui.themes.meteor.MeteorWidget;
import saturndevelopment.saturnclient.gui.widgets.pressable.WFavorite;
import saturndevelopment.saturnclient.utils.render.color.Color;

public class WMeteorFavorite extends WFavorite implements MeteorWidget {
    public WMeteorFavorite(boolean checked) {
        super(checked);
    }

    @Override
    protected Color getColor() {
        return theme().favoriteColor.get();
    }
}
