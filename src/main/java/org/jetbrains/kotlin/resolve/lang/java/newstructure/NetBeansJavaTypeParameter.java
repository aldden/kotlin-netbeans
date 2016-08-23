/**
 * *****************************************************************************
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ******************************************************************************
 */
package org.jetbrains.kotlin.resolve.lang.java.newstructure;

import java.util.Collection;
import java.util.Collections;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.lang.java.NBElementUtils;
import org.netbeans.api.java.source.TypeMirrorHandle;
import org.netbeans.api.project.Project;

/**
 *
 * @author Alexander.Baratynski
 */
public class NetBeansJavaTypeParameter extends NetBeansJavaClassifier implements JavaTypeParameter {

    private final TypeMirrorHandle handle;
    
    public NetBeansJavaTypeParameter(FqName fqName, Project project, TypeMirrorHandle handle) {
        super(fqName, project);
        this.handle = handle;
    }
    
    @Override
    public Collection<JavaClassifierType> getUpperBounds() {
        return NBElementUtils.getUpperBounds(handle, getProject());
    }
    
    @Override
    public Collection<JavaAnnotation> getAnnotations() {
        return Collections.emptyList();
    }

    @Override
    public JavaAnnotation findAnnotation(FqName fqname) {
        return null;
    }
    
}
