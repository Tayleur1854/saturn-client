/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.systems.modules.player;

import saturndevelopment.saturnclient.gui.GuiTheme;
import saturndevelopment.saturnclient.gui.widgets.WWidget;
import saturndevelopment.saturnclient.settings.DoubleSetting;
import saturndevelopment.saturnclient.settings.Setting;
import saturndevelopment.saturnclient.settings.SettingGroup;
import saturndevelopment.saturnclient.systems.modules.Categories;
import saturndevelopment.saturnclient.systems.modules.Module;
import saturndevelopment.saturnclient.utils.Utils;

public class Reach extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> blockReach = sgGeneral.add(new DoubleSetting.Builder()
        .name("extra-block-reach")
        .description("The distance to add to your block reach.")
        .sliderMax(1)
        .build()
    );

    private final Setting<Double> entityReach = sgGeneral.add(new DoubleSetting.Builder()
        .name("extra-entity-reach")
        .description("The distance to add to your entity reach.")
        .sliderMax(1)
        .build()
    );

    public Reach() {
        super(Categories.Player, "reach", "Gives you super long arms.");
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        return theme.label("Note: on vanilla servers you may give yourself up to 4 blocks of additional reach for specific actions - " +
            "interacting with block entities (chests, furnaces, etc.) or with vehicles. This does not work on paper servers.", Utils.getWindowWidth() / 3.0);
    }

    public double blockReach() {
        return isActive() ? blockReach.get() : 0;
    }

    public double entityReach() {
        return isActive() ? entityReach.get() : 0;
    }
}
