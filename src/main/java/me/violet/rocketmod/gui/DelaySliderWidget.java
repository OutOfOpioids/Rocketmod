package me.violet.rocketmod.gui;

import me.violet.rocketmod.Rocketmod;
import me.violet.rocketmod.config.ConfigManager;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class DelaySliderWidget extends SliderWidget {
    public DelaySliderWidget(int x, int y, int width, int height, Text text, double value) {
        super(x, y, width, height, text, value);
        this.updateMessage();
    }

    private double delay() {
        return(Math.round(value * 40));
    }

    @Override
    protected void updateMessage() {
        if(value == 0) this.setMessage(Text.of("Disabled"));
        else this.setMessage(Text.literal(String.valueOf("Delay: " + Rocketmod.INSTANCE.getDelay())));
    }

    @Override
    protected void applyValue() {
        Rocketmod.INSTANCE.updateDelay(delay());
        Rocketmod.LOGGER.info("Delay set to: " + Rocketmod.INSTANCE.getDelay());
        ConfigManager.save();
        this.updateMessage();
    }
}
