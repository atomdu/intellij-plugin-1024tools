package atomdu.intellij.language;

import com.intellij.openapi.fileTypes.*;
import org.jetbrains.annotations.NotNull;

public class AtomduFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(AtomduFileType.INSTANCE, "atomdu");
    }
}
