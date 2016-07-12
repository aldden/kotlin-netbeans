package org.black.kotlin.j2seprojectextension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.black.kotlin.j2seprojectextension.classpath.J2SEExtendedClassPathProvider;
import org.black.kotlin.project.KotlinClassPathProvider;
import org.black.kotlin.project.KotlinProject;
import org.black.kotlin.project.KotlinSources;
import org.netbeans.api.project.Project;
import org.netbeans.modules.java.j2seproject.J2SEProject;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.Places;
import org.openide.util.Exceptions;

/**
 *
 * @author Alexander.Baratynski
 */
public class KotlinProjectHelper {
    
    public static KotlinProjectHelper INSTANCE = new KotlinProjectHelper();
    
    private KotlinProjectHelper(){}
    
    private final Map<Project, KotlinSources> kotlinSources = new HashMap<Project, KotlinSources>();
    private final Map<Project, FileObject> lightClassesDirs = new HashMap<Project, FileObject>();
    private final Map<Project, KotlinClassPathProvider> classpaths = new HashMap<Project, KotlinClassPathProvider>();
    private final Map<Project, J2SEExtendedClassPathProvider> extendedClassPaths = new HashMap<Project, J2SEExtendedClassPathProvider>();
    
    public boolean checkProject(Project project){
        //TEMPORARY FOR TESTS. REDO IN THE FUTURE
        if (project instanceof KotlinProject) {
            return true;
        }
        
        if (!(project instanceof J2SEProject)){
            return false;
        }
        
        return true;
    }
    
    public KotlinSources getKotlinSources(Project project){
        if (!(checkProject(project))){
            return null;
        }
        
        if (!kotlinSources.containsKey(project)) {
            kotlinSources.put(project, new KotlinSources(project));
        }
        
        return kotlinSources.get(project);
    }
    
    public FileObject getLightClassesDirectory(Project project){
        if (!(checkProject(project))){
            return null;
        }
        
        if (!(lightClassesDirs.containsKey(project))) {
            lightClassesDirs.put(project, setLightClassesDir(project));
        }
        
        return lightClassesDirs.get(project);
    }
    
    private FileObject setLightClassesDir(Project project){
        if (Places.getUserDirectory() == null){
            return project.getProjectDirectory().
                    getFileObject("build").getFileObject("classes");
        }
        FileObject userDirectory = FileUtil.toFileObject(Places.getUserDirectory());
        String projectName = project.getProjectDirectory().getName();
        if (userDirectory.getFileObject(projectName) == null){
            try {
                userDirectory.createFolder(projectName);
            } catch (IOException ex) {
                Exceptions.printStackTrace(ex);
            }
        }
        return userDirectory.getFileObject(projectName);
    }
    
    public KotlinClassPathProvider getKotlinClassPathProvider(Project project){
        if (!(checkProject(project))){
            return null;
        }
        
        if (!classpaths.containsKey(project)){
            classpaths.put(project, new KotlinClassPathProvider(project));
        }
        
        return classpaths.get(project);
    }
    
    public J2SEExtendedClassPathProvider getJ2SEExtendedClassPathProvider(Project project) {
        if (!(checkProject(project))){
            return null;
        }
        
        if (!extendedClassPaths.containsKey(project)){
            extendedClassPaths.put(project, new J2SEExtendedClassPathProvider(project));
        }
        
        return extendedClassPaths.get(project);
    }
    
    public void updateJ2SEExtendedClassPathProvider(Project project) {
        extendedClassPaths.put(project, new J2SEExtendedClassPathProvider(project));
    }
    
}