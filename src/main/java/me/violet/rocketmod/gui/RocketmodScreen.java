package me.violet.rocketmod.gui;

import me.violet.rocketmod.Rocketmod;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;

public class RocketmodScreen extends Screen {

    public RocketmodScreen(Text title) {
        super(Text.of("Rocketmod"));
    }

    protected void init() {
        this.addDrawableChild(new DelaySliderWidget(2, 2, 90, 20, Text.of("Delay"), Rocketmod.INSTANCE.getDelay() / 40));
    }
}
