package me.eldodebug.soar.management.event.impl;

import me.eldodebug.soar.management.event.Event;
import net.minecraft.client.shader.ShaderGroup;

import java.util.ArrayList;
import java.util.List;

public class EventShader extends Event {

    private List<ShaderGroup> groups = new ArrayList<ShaderGroup>();

    public List<ShaderGroup> getGroups() {
        return groups;
    }
}
