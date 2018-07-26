package atomdu.intellij.language;

import com.intellij.lang.Language;

public class AtomduLanguage extends Language {
    public static final AtomduLanguage INSTANCE = new AtomduLanguage();

    private AtomduLanguage() {
        super("atomdu");
    }
}
