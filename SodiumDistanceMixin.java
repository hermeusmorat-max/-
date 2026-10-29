package dev.alisa.noverticalcull.mixin;

import net.caffeinemc.mods.sodium.client.render.chunk.RenderSection;
import net.caffeinemc.mods.sodium.client.render.chunk.RenderSectionManager;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Убирает вертикальное отсечение секций в Sodium: движение по оси Y
 * в BFS-обходе видимости никогда не отрезается по дистанции.
 * Горизонтальная отсечка остаётся как есть (радиус рендера).
 */
@Mixin(value = RenderSectionManager.class, remap = false, priority = 1200)
public class SodiumDistanceMixin {

    @Inject(method = "isWithinRenderDistance", at = @At("HEAD"), cancellable = true, remap = false)
    private void noverticalcull$allowVertical(RenderSection section, Direction dir, CallbackInfoReturnable<Boolean> cir) {
        if (dir.getAxis() == Direction.Axis.Y) {
            cir.setReturnValue(true);
        }
    }
}
