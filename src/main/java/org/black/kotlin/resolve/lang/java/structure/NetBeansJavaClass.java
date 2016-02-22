package org.black.kotlin.resolve.lang.java.structure;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.kotlin.descriptors.Visibility;
import org.jetbrains.kotlin.load.java.structure.JavaAnnotation;
import org.jetbrains.kotlin.load.java.structure.JavaClass;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaConstructor;
import org.jetbrains.kotlin.load.java.structure.JavaField;
import org.jetbrains.kotlin.load.java.structure.JavaMethod;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.jetbrains.kotlin.load.java.structure.JavaTypeSubstitutor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/**
 *
 * @author Александр
 */
public class NetBeansJavaClass extends NetBeansJavaClassifier<Element> implements JavaClass {
    
    public NetBeansJavaClass(Element javaElement){
        super(javaElement);
    }

    @Override
    public Name getName() {
        return Name.guess(getBinding().getKind().getDeclaringClass().getName());
    }

    @Override
    public Collection<JavaAnnotation> getAnnotations() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JavaAnnotation findAnnotation(FqName fqName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isDeprecatedInJavaDoc() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<JavaClass> getInnerClasses() {
//        Class[] classes = getBinding().getKind().getDeclaringClass().getDeclaredClasses();
//        classes[0].getTypeParameters();
        return null;
    }

    @Override
    public FqName getFqName() {
        return new FqName(getBinding().getKind().getDeclaringClass().getCanonicalName()); // not sure again
    }

    @Override
    public boolean isInterface() {
        return getBinding().getKind().getDeclaringClass().isInterface();
    }

    @Override
    public boolean isAnnotationType() {
        return getBinding().getKind().getDeclaringClass().isAnnotation();
    }

    @Override
    public boolean isEnum() {
        return getBinding().getKind().getDeclaringClass().isEnum();
    }

    @Override
    public JavaClass getOuterClass() {
        Class outerClass = getBinding().getKind().getDeclaringClass();
        return outerClass != null ? new NetBeansJavaClass(getBinding()) : null;
    }

    @Override
    public Collection<JavaClassifierType> getSupertypes() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<JavaMethod> getMethods() {
        Method[] methods = getBinding().getKind().getDeclaringClass().getDeclaredMethods();
        List<JavaMethod> methodsList = new ArrayList<JavaMethod>();
        for (Method method : methods){
//            if (method.getReturnType() != null)
//                methodsList.add(new NetBeansJavaMethod(method));
//TODO
        }
        
        return methodsList;
    }

    @Override
    public Collection<JavaField> getFields() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Collection<JavaConstructor> getConstructors() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JavaClassifierType getDefaultType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public OriginKind getOriginKind() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JavaType createImmediateType(JavaTypeSubstitutor substitutor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<JavaTypeParameter> getTypeParameters() {
        return null;//typeParameters(getBinding().getKind().getDeclaringClass().getTypeParameters());
    }

    @Override
    public boolean isAbstract() {
        return Modifier.isAbstract(getBinding().getKind().getDeclaringClass().getModifiers());
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(getBinding().getKind().getDeclaringClass().getModifiers());
    }

    @Override
    public boolean isFinal() {
        return Modifier.isFinal(getBinding().getKind().getDeclaringClass().getModifiers());
    }

    @Override
    public Visibility getVisibility() {
        return NetBeansJavaElementUtil.getVisibility(getBinding());
    }
    
}
