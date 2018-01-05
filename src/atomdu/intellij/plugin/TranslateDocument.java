package atomdu.intellij.plugin;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.RangeMarker;
import com.intellij.openapi.editor.event.DocumentListener;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.TextRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.beans.PropertyChangeListener;

/**
 * Created by atomdu on 2017/12/25.
 */
public class TranslateDocument {
    public String getText() {
        return null;
    }

    public String getText(@NotNull TextRange textRange) {
        return null;
    }

    public CharSequence getCharsSequence() {
        return null;
    }

    public CharSequence getImmutableCharSequence() {
        return null;
    }

    public char[] getChars() {
        return new char[0];
    }

    public int getTextLength() {
        return 0;
    }

    public int getLineCount() {
        return 0;
    }

    public int getLineNumber(int i) {
        return 0;
    }

    public int getLineStartOffset(int i) {
        return 0;
    }

    public int getLineEndOffset(int i) {
        return 0;
    }

    public void insertString(int i, @NotNull CharSequence charSequence) {

    }

    public void deleteString(int i, int i1) {

    }

    public void replaceString(int i, int i1, @NotNull CharSequence charSequence) {

    }

    public boolean isWritable() {
        return false;
    }

    public long getModificationStamp() {
        return 0;
    }

    public void fireReadOnlyModificationAttempt() {

    }

    public void addDocumentListener(@NotNull DocumentListener documentListener) {

    }

    public void addDocumentListener(@NotNull DocumentListener documentListener, @NotNull Disposable disposable) {

    }

    public void removeDocumentListener(@NotNull DocumentListener documentListener) {

    }

    public RangeMarker createRangeMarker(int i, int i1) {
        return null;
    }

    public RangeMarker createRangeMarker(int i, int i1, boolean b) {
        return null;
    }

    public void addPropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    public void removePropertyChangeListener(@NotNull PropertyChangeListener propertyChangeListener) {

    }

    public void setReadOnly(boolean b) {

    }

    public RangeMarker createGuardedBlock(int i, int i1) {
        return null;
    }

    public void removeGuardedBlock(@NotNull RangeMarker rangeMarker) {

    }

    public RangeMarker getOffsetGuard(int i) {
        return null;
    }

    public RangeMarker getRangeGuard(int i, int i1) {
        return null;
    }

    public void startGuardedBlockChecking() {

    }

    public void stopGuardedBlockChecking() {

    }

    public void setCyclicBufferSize(int i) {

    }

    public void setText(@NotNull CharSequence charSequence) {

    }

    public RangeMarker createRangeMarker(@NotNull TextRange textRange) {
        return null;
    }

    public int getLineSeparatorLength(int i) {
        return 0;
    }

    public <T> T getUserData(@NotNull Key<T> key) {
        return null;
    }

    public <T> void putUserData(@NotNull Key<T> key, @Nullable T t) {

    }
}
