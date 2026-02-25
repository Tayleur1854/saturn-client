/*
 * This file is part of the Saturn Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.gui.themes.meteor.widgets;

import saturndevelopment.saturnclient.gui.WidgetScreen;
import saturndevelopment.saturnclient.gui.themes.meteor.MeteorWidget;
import saturndevelopment.saturnclient.gui.widgets.WAccount;
import saturndevelopment.saturnclient.systems.accounts.Account;
import saturndevelopment.saturnclient.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements MeteorWidget {
    public WMeteorAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}
