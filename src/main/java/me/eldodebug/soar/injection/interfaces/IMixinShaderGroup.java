package me.eldodebug.soar.injection.interfaces;

import net.minecraft.client.shader.Shader;

import java.util.List;

public interface IMixinShaderGroup {
    List<Shader> getListShaders();
}
