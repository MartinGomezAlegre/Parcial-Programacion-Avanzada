package edu.usal.persistence;

import edu.usal.exceptions.ArchivoPersistenciaException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public abstract class ArchivoManagerString<T> {

    private final Path path;

    protected ArchivoManagerString(String filePath) {
        this.path = Path.of(filePath);
    }

    protected abstract String convertirARegistro(T entidad);

    protected abstract T convertirDesdeRegistro(String registro);

    public void guardarRegistro(T entidad) throws ArchivoPersistenciaException {
        try {
            crearDirectoriosSiNoExisten();
            Files.writeString(
                    path,
                    convertirARegistro(entidad) + System.lineSeparator(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND
            );
        } catch (IOException e) {
            throw new ArchivoPersistenciaException("Error al guardar en archivo String", e);
        }
    }

    public List<T> leerRegistros() throws ArchivoPersistenciaException {
        List<T> entidades = new ArrayList<>();

        if (!Files.exists(path)) {
            return entidades;
        }

        try {
            List<String> lineas = Files.readAllLines(path);
            for (String linea : lineas) {
                if (!linea.isBlank()) {
                    entidades.add(convertirDesdeRegistro(linea));
                }
            }
            return entidades;
        } catch (IOException e) {
            throw new ArchivoPersistenciaException("Error al leer archivo String", e);
        }
    }

    public void reescribirRegistros(List<T> entidades) throws ArchivoPersistenciaException {
        try {
            crearDirectoriosSiNoExisten();
            List<String> lineas = new ArrayList<>();
            for (T entidad : entidades) {
                lineas.add(convertirARegistro(entidad));
            }
            Files.write(path, lineas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new ArchivoPersistenciaException("Error al reescribir archivo String", e);
        }
    }

    private void crearDirectoriosSiNoExisten() throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
