package dml.maven;

import java.io.IOException;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import org.apache.maven.artifact.Artifact;

public class DmlMojoUtils {

    public static List<String> readDmlFilePathsFromArtifact(Set<Artifact> artifactSet) {
        List<String> dmlFilePaths = new ArrayList<String>();
        for(Artifact artifact : artifactSet) {
            String absolutePath = artifact.getFile().getAbsolutePath();
            try {
                JarFile jarFile = new JarFile(absolutePath);
                for(Enumeration<JarEntry> enumeration = jarFile.entries(); enumeration.hasMoreElements();) {
                    JarEntry jarEntry = enumeration.nextElement();
                    if(jarEntry.getName().endsWith(".dml")) {
                        dmlFilePaths.add("jar:file:"+absolutePath+"!/"+jarEntry.getName());
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        Collections.reverse(dmlFilePaths);
        return dmlFilePaths;
    }
}
