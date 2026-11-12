package MainUi;

import javax.swing.ImageIcon;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Loads bundled images from the classpath first, then from common
 * locations next to the jar/exe. This keeps the app working both in
 * IntelliJ and in the packaged Windows application.
 */
public final class ResourceLoader {

    private ResourceLoader() {
    }

    public static ImageIcon loadIcon(String resourcePath) {
        String normalized = resourcePath.replace('\\', '/');

        URL url = ResourceLoader.class.getClassLoader().getResource(normalized);
        if (url != null) {
            return new ImageIcon(url);
        }

        for (Path base : candidateBases()) {
            Path file = base.resolve(normalized);
            if (Files.isRegularFile(file)) {
                return new ImageIcon(file.toAbsolutePath().toString());
            }
        }

        // Last resort: preserve the original relative-path behaviour.
        return new ImageIcon(resourcePath);
    }

    private static Set<Path> candidateBases() {
        Set<Path> bases = new LinkedHashSet<>();

        addDirectory(bases, System.getProperty("user.dir"));
        addFileParent(bases, System.getProperty("jpackage.app-path"));

        String classPath = System.getProperty("java.class.path", "");
        for (String entry : classPath.split(File.pathSeparator)) {
            addFileParent(bases, entry);
        }

        return bases;
    }

    private static void addDirectory(Set<Path> bases, String directory) {
        if (directory == null || directory.isBlank()) {
            return;
        }
        bases.add(Paths.get(directory).toAbsolutePath().normalize());
    }

    private static void addFileParent(Set<Path> bases, String file) {
        if (file == null || file.isBlank()) {
            return;
        }

        Path path = Paths.get(file).toAbsolutePath().normalize();
        if (Files.isDirectory(path)) {
            bases.add(path);
            return;
        }

        Path parent = path.getParent();
        if (parent != null) {
            bases.add(parent);
            if (parent.getParent() != null) {
                bases.add(parent.getParent());
            }
        }
    }
}
