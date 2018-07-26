package atomdu.intellij.language;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class AtomduFileType extends LanguageFileType {
    public static final AtomduFileType INSTANCE = new AtomduFileType();

    private AtomduFileType() {
        super(AtomduLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Simple file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Simple language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "atomdu";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return AtomduIcons.FILE;
    }
}