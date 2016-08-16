/*
 * Copyright (c) 2007 Mockito contributors
 * This program is made available under the terms of the MIT License.
 */
package org.mockito.internal.util.reflection;

import static org.mockito.internal.util.reflection.AccessibilityChanger.enableAccess;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class LenientCopyTool {

    FieldCopier fieldCopier = new FieldCopier();

    public <T> void copyToMock(T from, T mock) {
        copy(from, mock, from.getClass());
    }

    public <T> void copyToRealObject(T from, T to) {
        copy(from, to, from.getClass());
    }

    private <T> void copy(T from, T to, Class<?> fromClazz) {
        while (fromClazz != Object.class) {
            copyValues(from, to, fromClazz);
            fromClazz = fromClazz.getSuperclass();
        }
    }

    private <T> void copyValues(T from, T mock, Class<?> classFrom) {
        Field[] fields = classFrom.getDeclaredFields();

        for (Field field : fields) {
            // ignore static fields
            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            AccessibilityChanger accessChange = null;
            try {
                accessChange = enableAccess(field);
                fieldCopier.copyValue(from, mock, field);
            } catch (Throwable t) {
                //Ignore - be lenient - if some field cannot be copied then let's be it
            } finally {
                if (accessChange != null)
                    accessChange.undo();
            }
        }
    }
}