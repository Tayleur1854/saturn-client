/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.gui.screens.accounts;

import saturndevelopment.saturnclient.gui.GuiTheme;
import saturndevelopment.saturnclient.gui.WindowScreen;
import saturndevelopment.saturnclient.gui.widgets.containers.WHorizontalList;
import saturndevelopment.saturnclient.gui.widgets.pressable.WButton;
import saturndevelopment.saturnclient.systems.accounts.Account;
import saturndevelopment.saturnclient.systems.accounts.AccountType;
import saturndevelopment.saturnclient.systems.accounts.TokenAccount;
import saturndevelopment.saturnclient.utils.render.color.Color;

import static saturndevelopment.saturnclient.SaturnClient.mc;

public class AccountInfoScreen extends WindowScreen {
    private final Account<?> account;

    public AccountInfoScreen(GuiTheme theme, Account<?> account) {
        super(theme, account.getUsername() + " details");
        this.account = account;
    }

    @Override
    public void initWidgets() {
        TokenAccount e = (TokenAccount) account;
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        String tokenLabel = account.getType() + " token:";
        if (account.getType() == AccountType.Session) tokenLabel = "";

        WButton copy = theme.button("Copy");
        copy.action = () -> mc.keyboard.setClipboard(e.getToken());

        l.add(theme.label(tokenLabel));
        l.add(theme.label(account.getType() == AccountType.Session ? "Click to copy Token" : e.getToken()).color(Color.GRAY)).pad(5);
        l.add(copy);
    }
}
