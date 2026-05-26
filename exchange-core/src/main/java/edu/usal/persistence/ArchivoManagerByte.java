package edu.usal.persistence;

import edu.usal.exceptions.ArchivoPersistenciaException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ArchivoManagerByte<T extends Serializable> {

    private final Path path;

    public ArchivoManagerByte(String filePath) {
        this.path = Path.of(filePath);
    }

    public void guardarLista(List<T> entidades) throws ArchivoPersistenciaException {
        try {
            crearDirectoriosSiNoExisten();
            try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
                outputStream.writeObject(entidades);
            }
        } catch (IOException e) {
            throw new ArchivoPersistenciaException("Error al guardar en archivo Byte", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> leerLista() throws ArchivoPersistenciaException {
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<T>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new ArchivoPersistenciaException("Error al leer archivo Byte", e);
        }
    }

    private void crearDirectoriosSiNoExisten() throws IOException {
        Path parent = path.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }
    }
}
