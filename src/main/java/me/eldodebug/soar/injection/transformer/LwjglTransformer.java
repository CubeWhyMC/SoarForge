package me.eldodebug.soar.injection.transformer;

import net.minecraft.launchwrapper.IClassTransformer;
import org.jetbrains.annotations.NotNull;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

public class LwjglTransformer implements IClassTransformer {

    @Override
    public byte[] transform(@NotNull String name, String transformedName, byte[] basicClass) {
        if (name.equals("org.lwjgl.nanovg.NanoVGGLConfig")) {
            ClassReader reader = new ClassReader(basicClass);
            ClassNode node = new ClassNode();
            reader.accept(node, ClassReader.EXPAND_FRAMES);

            for (MethodNode method : node.methods) {
                if (method.name.equals("configGL")) {
                    InsnList list = new InsnList();

                    list.add(new VarInsnNode(Opcodes.LLOAD, 0));
                    list.add(new TypeInsnNode(Opcodes.NEW, "me/eldodebug/soar/injection/transformer/Lwjgl2FunctionProvider"));
                    list.add(new InsnNode(Opcodes.DUP));
                    list.add(new MethodInsnNode(
                            Opcodes.INVOKESPECIAL,
                            "me/eldodebug/soar/injection/transformer/Lwjgl2FunctionProvider",
                            "<init>",
                            "()V",
                            false
                    ));
                    list.add(new MethodInsnNode(
                            Opcodes.INVOKESTATIC,
                            "org/lwjgl/nanovg/NanoVGGLConfig",
                            "config",
                            "(JLorg/lwjgl/system/FunctionProvider;)V",
                            false
                    ));
                    list.add(new InsnNode(Opcodes.RETURN));

                    method.instructions.clear();
                    method.instructions.insert(list);
                }
            }

            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            node.accept(cw);
            return cw.toByteArray();
        }
        return basicClass;
    }
}
