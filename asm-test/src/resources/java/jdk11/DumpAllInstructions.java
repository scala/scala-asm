// ASM: a very small and fast Java bytecode manipulation framework
// Copyright (c) 2000-2011 INRIA, France Telecom
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions
// are met:
// 1. Redistributions of source code must retain the above copyright
//    notice, this list of conditions and the following disclaimer.
// 2. Redistributions in binary form must reproduce the above copyright
//    notice, this list of conditions and the following disclaimer in the
//    documentation and/or other materials provided with the distribution.
// 3. Neither the name of the copyright holders nor the names of its
//    contributors may be used to endorse or promote products derived from
//    this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
// THE POSSIBILITY OF SUCH DAMAGE.
package jdk11;

import java.io.FileOutputStream;
import java.io.IOException;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.ConstantDynamic;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * Generates classes with all the JDK11 specific instructions.
 *
 * <p>TODO: remove this and use the JDK11 to compile equivalent classes, when it is released.
 *
 * @author Eric Bruneton
 */
public class DumpAllInstructions implements Opcodes {

  public static void main(String[] args) throws IOException {
    FileOutputStream fileOutputStream = new FileOutputStream("AllInstructions.class");
    fileOutputStream.write(dumpAllInstructions());
    fileOutputStream.close();
  }

  private static byte[] dumpAllInstructions() {
    ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
    classWriter.visit(
        V11, ACC_PUBLIC + ACC_SUPER, "jdk11/AllInstructions", null, "java/lang/Object", null);
    addDefaultConstructor(classWriter);

    MethodVisitor methodVisitor =
        classWriter.visitMethod(ACC_PUBLIC, "m", "()Ljava/lang/Object;", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitLdcInsn(
        new ConstandDynamic(
            "name",
            "Ljava/lang/Object;",
            new Handle(
                H_INVOKESTATIC,
                "HandleOwner",
                "handleField",
                "(Ljava/lang/Object;)Ljava/lang/Object;",
                false),
            new ConstantDynamic(
                "argumentName",
                "Ljava/lang/Object;",
                new Handle(
                    H_GETSTATIC,
                    "ArgumentHandleOwner",
                    "argumentHandleName",
                    "Ljava/lang/Object;",
                    false))));
    methodVisitor.visitInsn(ARETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();

    classWriter.visitEnd();
    return classWriter.toByteArray();
  }

  private static void addDefaultConstructor(final ClassWriter classWriter) {
    MethodVisitor methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
    methodVisitor.visitCode();
    methodVisitor.visitVarInsn(ALOAD, 0);
    methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
    methodVisitor.visitInsn(RETURN);
    methodVisitor.visitMaxs(0, 0);
    methodVisitor.visitEnd();
  }
}
