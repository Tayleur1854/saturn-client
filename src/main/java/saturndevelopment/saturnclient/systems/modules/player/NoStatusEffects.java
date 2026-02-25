/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package saturndevelopment.saturnclient.systems.modules.player;

import saturndevelopment.saturnclient.settings.*;
import saturndevelopment.saturnclient.settings.Setting;
import saturndevelopment.saturnclient.settings.SettingGroup;
import saturndevelopment.saturnclient.settings.StatusEffectListSetting;
import saturndevelopment.saturnclient.systems.modules.Categories;
import saturndevelopment.saturnclient.systems.modules.Module;
import net.minecraft.entity.effect.StatusEffect;

import java.util.List;

import static net.minecraft.entity.effect.StatusEffects.*;

public class NoStatusEffects extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<StatusEffect>> blockedEffects = sgGeneral.add(new StatusEffectListSetting.Builder()
        .name("blocked-effects")
        .description("Effects to block.")
        .defaultValue(
            LEVITATION.value(),
            JUMP_BOOST.value(),
            SLOW_FALLING.value(),
            DOLPHINS_GRACE.value()
        )
        .build()
    );

    public NoStatusEffects() {
        super(Categories.Player, "no-status-effects", "Blocks specified status effects.");
    }

    public boolean shouldBlock(StatusEffect effect) {
        return isActive() && blockedEffects.get().contains(effect);
    }
}
