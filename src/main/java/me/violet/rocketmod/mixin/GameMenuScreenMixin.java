package me.violet.rocketmod.mixin;

import me.violet.rocketmod.Rocketmod;
import me.violet.rocketmod.gui.RocketmodScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.violet.rocketmod.Rocketmod.mc;

@Mixin(GameMenuScreen.class)
public abstract class GameMenuScreenMixin extends Screen {
    protected GameMenuScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "initWidgets")
    private void initWidgetsHook(CallbackInfo callbackInfo) {
        int width = mc.getWindow().getWidth() / 2;
        int height = mc.getWindow().getHeight() / 2;
        this.addDrawableChild(ButtonWidget.builder(Text.of("Rocketmod"), button -> {
            this.client.setScreen(new RocketmodScreen(Text.of("Rocketmod")));
        }).position(width - 92, height - 22).size(90, 20).build());
    }
}
