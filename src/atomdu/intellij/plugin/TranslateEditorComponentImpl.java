package atomdu.intellij.plugin;

import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.impl.ApplicationImpl;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.command.UndoConfirmationPolicy;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.*;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.event.DocumentEvent;
import com.intellij.openapi.editor.impl.EditorComponentImpl;
import com.intellij.openapi.editor.impl.EditorImpl;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.TextRange;
import com.intellij.util.ui.accessibility.ScreenReader;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.accessibility.AccessibleRole;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.plaf.TextUI;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;

public class TranslateEditorComponentImpl extends EditorComponentImpl {
    private final EditorImpl myEditor;
    private final ApplicationImpl myApplication;

    public TranslateEditorComponentImpl(@NotNull EditorImpl editor) {
        super(editor);
        this.myEditor = editor;
        this.enableEvents(0);
        this.enableInputMethods(false);
        this.setFocusCycleRoot(!ScreenReader.isActive());
        this.setOpaque(true);
        this.myApplication = (ApplicationImpl) ApplicationManager.getApplication();
    }

    public Dimension getPreferredSize() {
        return this.myEditor.getPreferredSize();
    }

    protected void fireResized() {
        this.processComponentEvent(new ComponentEvent(this, 101));
    }

    @NonNls
    public String toString() {
        return "EditorComponent file=" + this.myEditor.getVirtualFile();
    }

    public void updateUI() {
        this.setUI(new TranslateEditorComponentImpl.EditorAccessibilityTextUI());
        UISettings.setupEditorAntialiasing(this);
        this.invalidate();
    }

    public String getToolTipText(MouseEvent event) {
        return this.getToolTipText();
    }

    private void fireJTextComponentDocumentChange(final DocumentEvent event) {
        java.util.List<DocumentListener> listeners = ((TranslateEditorComponentImpl.EditorAccessibilityDocument) this.getDocument()).getListeners();
        if (listeners != null) {
            javax.swing.event.DocumentEvent swingEvent = new javax.swing.event.DocumentEvent() {
                public int getOffset() {
                    return event.getOffset();
                }

                public int getLength() {
                    return event.getNewLength();
                }

                public javax.swing.text.Document getDocument() {
                    return TranslateEditorComponentImpl.this.getDocument();
                }

                public EventType getType() {
                    return event.getOldLength() == 0 ? EventType.INSERT : (event.getNewLength() == 0 ? EventType.REMOVE : EventType.CHANGE);
                }

                @Nullable
                public ElementChange getChange(Element element) {
                    return null;
                }
            };
            Iterator var4 = listeners.iterator();

            while (var4.hasNext()) {
                DocumentListener listener = (DocumentListener) var4.next();
                javax.swing.event.DocumentEvent.EventType type = swingEvent.getType();
                if (type == javax.swing.event.DocumentEvent.EventType.INSERT) {
                    listener.insertUpdate(swingEvent);
                } else if (type == javax.swing.event.DocumentEvent.EventType.REMOVE) {
                    listener.removeUpdate(swingEvent);
                } else if (type == javax.swing.event.DocumentEvent.EventType.CHANGE) {
                    listener.changedUpdate(swingEvent);
                }
            }

        }
    }

    private static void notSupported() {
        throw new RuntimeException("Not supported for this text implementation");
    }
    Document document;
    public void setText(String text) {
        document =  EditorFactory.getInstance().createDocument(text);
        this.editDocumentSafely(0, document.getTextLength(), text);
    }

    private void editDocumentSafely(int offset, int length, @Nullable String text) {
        Project project = this.myEditor.getProject();
        if (FileDocumentManager.getInstance().requestWriting(document, project)) {
            CommandProcessor.getInstance().executeCommand(project, () -> {
                ApplicationManager.getApplication().runWriteAction(new DocumentRunnable(document, project) {
                    public void run() {
                        document.startGuardedBlockChecking();

                        try {
                            if (text == null) {
                                document.deleteString(offset, offset + length);
                            } else if (length == 0) {
                                document.insertString(offset, text);
                            } else {
                                document.replaceString(offset, offset + length, text);
                            }
                        } catch (ReadOnlyFragmentModificationException var5) {
                            EditorActionManager.getInstance().getReadonlyFragmentModificationHandler(document).handle(var5);
                        } finally {
                            document.stopGuardedBlockChecking();
                        }

                    }
                });
            }, "", document, UndoConfirmationPolicy.DEFAULT, document);
        }
    }

//    private class AccessibleEditorComponentImpl extends AccessibleJComponent implements AccessibleText, AccessibleEditableText, AccessibleExtendedText, com.intellij.openapi.editor.event.CaretListener, com.intellij.openapi.editor.event.DocumentListener {
//        private int myCaretPos;
//        private static final int BEFORE = -1;
//        private static final int HERE = 0;
//        private static final int AFTER = 1;
//
//        public AccessibleEditorComponentImpl() {
//            //super(TranslateEditorComponentImpl.this);
//            if (!TranslateEditorComponentImpl.this.myEditor.isDisposed()) {
//                TranslateEditorComponentImpl.this.myEditor.getCaretModel().addCaretListener(this);
//                TranslateEditorComponentImpl.this.myEditor.getDocument().addDocumentListener(this);
//                Disposer.register(TranslateEditorComponentImpl.this.myEditor.getDisposable(), new Disposable() {
//                    public void dispose() {
//                        TranslateEditorComponentImpl.this.myEditor.getCaretModel().removeCaretListener(TranslateEditorComponentImpl.AccessibleEditorComponentImpl.this);
//                        TranslateEditorComponentImpl.this.myEditor.getDocument().removeDocumentListener(TranslateEditorComponentImpl.AccessibleEditorComponentImpl.this);
//                    }
//                });
//            }
//        }
//
//        public void caretAdded(CaretEvent e) {
//        }
//
//        public void caretRemoved(CaretEvent e) {
//        }
//
//        public void beforeDocumentChange(DocumentEvent event) {
//        }
//
//        public void documentChanged(DocumentEvent event) {
//            Integer pos = Integer.valueOf(event.getOffset());
//            if (ApplicationManager.getApplication().isDispatchThread()) {
//                this.firePropertyChange("AccessibleText", (Object) null, pos);
//                if (SystemInfo.isMac) {
//                    TranslateEditorComponentImpl.this.fireJTextComponentDocumentChange(event);
//                }
//            } else {
//                ApplicationManager.getApplication().invokeLater(() -> {
//                    this.firePropertyChange("AccessibleText", (Object) null, pos);
//                    TranslateEditorComponentImpl.this.fireJTextComponentDocumentChange(event);
//                });
//            }
//
//        }
//
//        @Nullable
//        public String getAccessibleName() {
//            if (this.accessibleName != null) {
//                return this.accessibleName;
//            } else {
//                VirtualFile file = TranslateEditorComponentImpl.this.myEditor.getVirtualFile();
//                return file != null ? "Editor for " + file.getName() : "Editor";
//            }
//        }
//
//        public AccessibleRole getAccessibleRole() {
//            return SystemInfo.isMac ? TranslateEditorComponentImpl.TextAccessibleRole.TEXT_AREA : AccessibleRole.TEXT;
//        }
//
//        public AccessibleText getAccessibleText() {
//            return this;
//        }
//
//        public AccessibleEditableText getAccessibleEditableText() {
//            return this;
//        }
//
//        public AccessibleStateSet getAccessibleStateSet() {
//            AccessibleStateSet states = super.getAccessibleStateSet();
//            if (TranslateEditorComponentImpl.this.myEditor.getDocument().isWritable()) {
//                states.add(AccessibleState.EDITABLE);
//            }
//
//            states.add(AccessibleState.MULTI_LINE);
//            return states;
//        }
//
//        public int getIndexAtPoint(Point point) {
//            LogicalPosition logicalPosition = TranslateEditorComponentImpl.this.myEditor.xyToLogicalPosition(point);
//            return TranslateEditorComponentImpl.this.myEditor.logicalPositionToOffset(logicalPosition);
//        }
//
//        public Rectangle getCharacterBounds(int offset) {
//            if (offset >= 0 && offset <= TranslateEditorComponentImpl.this.myEditor.getDocument().getTextLength() - 1) {
//                LogicalPosition pos = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset);
//                Point point = TranslateEditorComponentImpl.this.myEditor.logicalPositionToXY(pos);
//                FontMetrics fontMetrics = TranslateEditorComponentImpl.this.myEditor.getFontMetrics(0);
//                char c = TranslateEditorComponentImpl.this.myEditor.getDocument().getCharsSequence().subSequence(offset, offset + 1).charAt(0);
//                return new Rectangle(point.x, point.y, fontMetrics.charWidth(c), fontMetrics.getHeight());
//            } else {
//                return null;
//            }
//        }
//
//        public int getCharCount() {
//            return TranslateEditorComponentImpl.this.myEditor.getDocument().getTextLength();
//        }
//
//        public int getCaretPosition() {
//            return TranslateEditorComponentImpl.this.myEditor.getCaretModel().getOffset();
//        }
//
//        @Nullable
//        public String getAtIndex(int part, int index) {
//            return this.getTextAtOffset(part, index, 0);
//        }
//
//        @Nullable
//        public String getAfterIndex(int part, int index) {
//            return this.getTextAtOffset(part, index, 1);
//        }
//
//        @Nullable
//        public String getBeforeIndex(int part, int index) {
//            return this.getTextAtOffset(part, index, -1);
//        }
//
//        public AttributeSet getCharacterAttribute(int index) {
//            return new SimpleAttributeSet();
//        }
//
//        public int getSelectionStart() {
//            return TranslateEditorComponentImpl.this.myEditor.getSelectionModel().getSelectionStart();
//        }
//
//        public int getSelectionEnd() {
//            return TranslateEditorComponentImpl.this.myEditor.getSelectionModel().getSelectionEnd();
//        }
//
//        @Nullable
//        public String getSelectedText() {
//            return TranslateEditorComponentImpl.this.myEditor.getSelectionModel().getSelectedText();
//        }
//
//        public void setTextContents(String s) {
//            TranslateEditorComponentImpl.this.setText(s);
//        }
//
//        public void insertTextAtIndex(int index, String s) {
//            TranslateEditorComponentImpl.this.editDocumentSafely(index, 0, s);
//        }
//
//        public String getTextRange(int startIndex, int endIndex) {
//            return TranslateEditorComponentImpl.this.myEditor.getDocument().getCharsSequence().subSequence(startIndex, endIndex).toString();
//        }
//
//        public void delete(int startIndex, int endIndex) {
//            TranslateEditorComponentImpl.this.editDocumentSafely(startIndex, endIndex - startIndex, (String) null);
//        }
//
//        public void cut(int startIndex, int endIndex) {
//            TranslateEditorComponentImpl.this.myEditor.getSelectionModel().setSelection(startIndex, endIndex);
//            DataContext dataContext = DataManager.getInstance().getDataContext(TranslateEditorComponentImpl.this);
//            CutProvider cutProvider = TranslateEditorComponentImpl.this.myEditor.getCutProvider();
//            if (cutProvider.isCutEnabled(dataContext)) {
//                cutProvider.performCut(dataContext);
//            }
//
//        }
//
//        public void paste(int startIndex) {
//            TranslateEditorComponentImpl.this.myEditor.getCaretModel().moveToOffset(startIndex);
//            DataContext dataContext = DataManager.getInstance().getDataContext(TranslateEditorComponentImpl.this);
//            PasteProvider pasteProvider = TranslateEditorComponentImpl.this.myEditor.getPasteProvider();
//            if (pasteProvider.isPasteEnabled(dataContext)) {
//                pasteProvider.performPaste(dataContext);
//            }
//
//        }
//
//        public void replaceText(int startIndex, int endIndex, String s) {
//            TranslateEditorComponentImpl.this.editDocumentSafely(startIndex, endIndex, s);
//        }
//
//        public void selectText(int startIndex, int endIndex) {
//            TranslateEditorComponentImpl.this.myEditor.getSelectionModel().setSelection(startIndex, endIndex);
//        }
//
//        public void setAttributes(int startIndex, int endIndex, AttributeSet as) {
//        }
//
//        @Nullable
//        public AccessibleTextSequence getTextSequenceAt(int part, int index) {
//            return this.getSequenceAtIndex(part, index, 0);
//        }
//
//        @Nullable
//        public AccessibleTextSequence getTextSequenceAfter(int part, int index) {
//            return this.getSequenceAtIndex(part, index, 1);
//        }
//
//        @Nullable
//        public AccessibleTextSequence getTextSequenceBefore(int part, int index) {
//            return this.getSequenceAtIndex(part, index, -1);
//        }
//
//        @Nullable
//        public Rectangle getTextBounds(int startIndex, int endIndex) {
//            LogicalPosition startPos = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(startIndex);
//            Point startPoint = TranslateEditorComponentImpl.this.myEditor.logicalPositionToXY(startPos);
//            Rectangle rectangle = new Rectangle(startPoint);
//            LogicalPosition endPos = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(endIndex);
//            Point endPoint = TranslateEditorComponentImpl.this.myEditor.logicalPositionToXY(endPos);
//            FontMetrics fontMetrics = TranslateEditorComponentImpl.this.myEditor.getFontMetrics(0);
//            char c = TranslateEditorComponentImpl.this.myEditor.getDocument().getCharsSequence().subSequence(endIndex - 1, endIndex).charAt(0);
//            endPoint.x += fontMetrics.charWidth(c);
//            endPoint.y += fontMetrics.getHeight();
//            rectangle.add(endPoint);
//            return rectangle;
//        }
//
//        @Nullable
//        private String getTextAtOffset(int type, int offset, int direction) {
//            DocumentEx document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            if (offset >= 0 && offset < document.getTextLength()) {
//                int lineStart;
//                int lineEnd;
//                switch (type) {
//                    case 1:
//                        if (offset + direction < document.getTextLength() && offset + direction >= 0) {
//                            lineStart = offset + direction;
//                            return document.getCharsSequence().subSequence(lineStart, lineStart + 1).toString();
//                        }
//                        break;
//                    case 2:
//                        lineStart = this.getWordAtOffsetStart(offset, direction);
//                        lineEnd = this.getWordAtOffsetEnd(offset, direction);
//                        if (lineStart != -1 && lineEnd != -1) {
//                            return TranslateEditorComponentImpl.this.myEditor.getDocument().getCharsSequence().subSequence(lineStart, lineEnd).toString();
//                        }
//
//                        return null;
//                    case 3:
//                        lineStart = this.getLineAtOffsetStart(offset, direction);
//                        lineEnd = this.getLineAtOffsetEnd(offset, direction);
//                        if (lineStart != -1 && lineEnd != -1) {
//                            return document.getCharsSequence().subSequence(lineStart, lineEnd).toString();
//                        }
//
//                        return null;
//                    case 4:
//                    case 5:
//                        assert false : type;
//                }
//
//                return null;
//            } else {
//                return null;
//            }
//        }
//
//        @Nullable
//        private AccessibleTextSequence getSequenceAtIndex(int type, int offset, int direction) {
//            assert direction == -1 || direction == 0 || direction == 1;
//
//            DocumentEx document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            if (offset >= 0 && offset < document.getTextLength()) {
//                int lineStart;
//                int lineEnd;
//                switch (type) {
//                    case 1:
//                        AccessibleTextSequence charSequence = null;
//                        if (offset + direction < document.getTextLength() && offset + direction >= 0) {
//                            lineStart = offset + direction;
//                            charSequence = new AccessibleTextSequence(lineStart, lineStart + 1, document.getCharsSequence().subSequence(lineStart, lineStart + 1).toString());
//                        }
//
//                        return charSequence;
//                    case 2:
//                    case 5:
//                        lineStart = this.getWordAtOffsetStart(offset, direction);
//                        lineEnd = this.getWordAtOffsetEnd(offset, direction);
//                        if (lineStart != -1 && lineEnd != -1) {
//                            return new AccessibleTextSequence(lineStart, lineEnd, document.getCharsSequence().subSequence(lineStart, lineEnd).toString());
//                        }
//
//                        return null;
//                    case 3:
//                    case 4:
//                        lineStart = this.getLineAtOffsetStart(offset, direction);
//                        lineEnd = this.getLineAtOffsetEnd(offset, direction);
//                        if (lineStart != -1 && lineEnd != -1) {
//                            return new AccessibleTextSequence(lineStart, lineEnd, document.getCharsSequence().subSequence(lineStart, lineEnd).toString());
//                        }
//
//                        return null;
//                    default:
//                        return null;
//                }
//            } else {
//                return null;
//            }
//        }
//
//        private int getLineAtOffsetStart(int offset) {
//            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            if (offset == 0) {
//                return 0;
//            } else {
//                int lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                return document.getLineStartOffset(lineNumber);
//            }
//        }
//
//        private int moveLineOffset(int offset, int direction) {
//            int lineNumber;
//            DocumentEx document;
//            if (direction == 1) {
//                lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                ++lineNumber;
//                document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//                return lineNumber == document.getLineCount() ? -1 : document.getLineStartOffset(lineNumber);
//            } else if (direction == -1) {
//                lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                --lineNumber;
//                if (lineNumber < 0) {
//                    return -1;
//                } else {
//                    document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//                    return document.getLineStartOffset(lineNumber);
//                }
//            } else {
//                assert direction == 0;
//
//                return offset;
//            }
//        }
//
//        private int getLineAtOffsetStart(int offset, int direction) {
//            offset = this.moveLineOffset(offset, direction);
//            return offset == -1 ? -1 : this.getLineAtOffsetStart(offset);
//        }
//
//        private int getLineAtOffsetEnd(int offset) {
//            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            if (offset == 0) {
//                return 0;
//            } else {
//                int lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                return document.getLineEndOffset(lineNumber);
//            }
//        }
//
//        private int getLineAtOffsetEnd(int offset, int direction) {
//            offset = this.moveLineOffset(offset, direction);
//            return offset == -1 ? -1 : this.getLineAtOffsetEnd(offset);
//        }
//
//        private int moveWordOffset(int offset, int direction) {
//            DocumentEx document;
//            CharSequence text;
//            int newOffsetx;
//            if (direction == 1) {
//                document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//                text = document.getCharsSequence();
//                newOffsetx = document.getTextLength();
//                int newOffset = offset - 1;
//
//                boolean camelx;
//                for (camelx = TranslateEditorComponentImpl.this.myEditor.getSettings().isCamelWords(); newOffset < newOffsetx && !EditorActionUtil.isWordEnd(text, newOffset, camelx); ++newOffset) {
//                    ;
//                }
//
//                ++newOffset;
//
//                while (newOffset < newOffsetx) {
//                    if (EditorActionUtil.isWordStart(text, newOffset, camelx)) {
//                        return newOffset;
//                    }
//
//                    ++newOffset;
//                }
//
//                return -1;
//            } else if (direction != -1) {
//                assert direction == 0;
//
//                return offset;
//            } else {
//                document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//                text = document.getCharsSequence();
//                newOffsetx = offset - 1;
//
//                boolean camel;
//                for (camel = TranslateEditorComponentImpl.this.myEditor.getSettings().isCamelWords(); newOffsetx >= 0 && !EditorActionUtil.isWordStart(text, newOffsetx, camel); --newOffsetx) {
//                    ;
//                }
//
//                --newOffsetx;
//
//                while (newOffsetx >= 0) {
//                    if (EditorActionUtil.isWordEnd(text, newOffsetx, camel)) {
//                        return newOffsetx;
//                    }
//
//                    --newOffsetx;
//                }
//
//                return -1;
//            }
//        }
//
//        private int getWordAtOffsetStart(int offset, int direction) {
//            offset = this.moveWordOffset(offset, direction);
//            return offset == -1 ? -1 : this.getWordAtOffsetStart(offset);
//        }
//
//        private int getWordAtOffsetEnd(int offset, int direction) {
//            offset = this.moveWordOffset(offset, direction);
//            return offset == -1 ? -1 : this.getWordAtOffsetEnd(offset);
//        }
//
//        private int getWordAtOffsetStart(int offset) {
//            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            if (offset == 0) {
//                return 0;
//            } else {
//                int lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                CharSequence text = document.getCharsSequence();
//                int newOffset = offset - 1;
//                int minOffset = lineNumber > 0 ? document.getLineEndOffset(lineNumber - 1) : 0;
//
//                for (boolean camel = TranslateEditorComponentImpl.this.myEditor.getSettings().isCamelWords(); newOffset > minOffset && !EditorActionUtil.isWordStart(text, newOffset, camel); --newOffset) {
//                    ;
//                }
//
//                return newOffset;
//            }
//        }
//
//        private int getWordAtOffsetEnd(int offset) {
//            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
//            CharSequence text = document.getCharsSequence();
//            if (offset < document.getTextLength() - 1 && document.getLineCount() != 0) {
//                int newOffset = offset + 1;
//                int lineNumber = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset).line;
//                int maxOffset = document.getLineEndOffset(lineNumber);
//                if (newOffset > maxOffset) {
//                    if (lineNumber + 1 >= document.getLineCount()) {
//                        return offset;
//                    }
//
//                    maxOffset = document.getLineEndOffset(lineNumber + 1);
//                }
//
//                for (boolean camel = TranslateEditorComponentImpl.this.myEditor.getSettings().isCamelWords(); newOffset < maxOffset && !EditorActionUtil.isWordEnd(text, newOffset, camel); ++newOffset) {
//                    ;
//                }
//
//                return newOffset;
//            } else {
//                return offset;
//            }
//        }
//    }

    private static class TextAccessibleRole extends AccessibleRole {
        private static final AccessibleRole TEXT_AREA = new TranslateEditorComponentImpl.TextAccessibleRole("textarea");

        private TextAccessibleRole(String key) {
            super(key);
        }
    }

    private class EditorAccessibilityTextUI extends TextUI {
        private EditorAccessibilityTextUI() {
        }

        @Nullable
        public Rectangle modelToView(JTextComponent tc, int offset) throws BadLocationException {
            LogicalPosition pos = TranslateEditorComponentImpl.this.myEditor.offsetToLogicalPosition(offset);
            Point point = TranslateEditorComponentImpl.this.myEditor.logicalPositionToXY(pos);
            FontMetrics fontMetrics = TranslateEditorComponentImpl.this.myEditor.getFontMetrics(0);
            char c = TranslateEditorComponentImpl.this.myEditor.getDocument().getCharsSequence().subSequence(offset, offset + 1).charAt(0);
            return new Rectangle(point.x, point.y, fontMetrics.charWidth(c), fontMetrics.getHeight());
        }

        public int viewToModel(JTextComponent tc, Point pt) {
            LogicalPosition logicalPosition = TranslateEditorComponentImpl.this.myEditor.xyToLogicalPosition(pt);
            return TranslateEditorComponentImpl.this.myEditor.logicalPositionToOffset(logicalPosition);
        }

        @Nullable
        public Rectangle modelToView(JTextComponent tc, int pos, Position.Bias ignored) throws BadLocationException {
            return this.modelToView(tc, pos);
        }

        public int viewToModel(JTextComponent tc, Point pt, Position.Bias[] ignored) {
            return this.viewToModel(tc, pt);
        }

        public int getNextVisualPositionFrom(JTextComponent t, int pos, Position.Bias b, int direction, Position.Bias[] biasRet) throws BadLocationException {
            TranslateEditorComponentImpl.notSupported();
            return 0;
        }

        public void damageRange(JTextComponent t, int p0, int p1) {
            TranslateEditorComponentImpl.this.myEditor.repaint(p0, p1);
        }

        public void damageRange(JTextComponent t, int p0, int p1, Position.Bias ignored1, Position.Bias ignored2) {
            this.damageRange(t, p0, p1);
        }

        @Nullable
        public EditorKit getEditorKit(JTextComponent t) {
            TranslateEditorComponentImpl.notSupported();
            return null;
        }

        @Nullable
        public View getRootView(JTextComponent t) {
            TranslateEditorComponentImpl.notSupported();
            return null;
        }
    }

    private class EditorAccessibilityCaret implements javax.swing.text.Caret {
        private EditorAccessibilityCaret() {
        }

        public void install(JTextComponent jTextComponent) {
        }

        public void deinstall(JTextComponent jTextComponent) {
        }

        public void paint(Graphics graphics) {
        }

        public void addChangeListener(ChangeListener changeListener) {
        }

        public void removeChangeListener(ChangeListener changeListener) {
        }

        public boolean isVisible() {
            return true;
        }

        public void setVisible(boolean visible) {
        }

        public boolean isSelectionVisible() {
            return true;
        }

        public void setSelectionVisible(boolean visible) {
        }

        public void setMagicCaretPosition(Point point) {
        }

        @Nullable
        public Point getMagicCaretPosition() {
            return null;
        }

        public void setBlinkRate(int rate) {
        }

        public int getBlinkRate() {
            return 250;
        }

        public int getDot() {
            return TranslateEditorComponentImpl.this.myEditor.getCaretModel().getOffset();
        }

        public int getMark() {
            return TranslateEditorComponentImpl.this.myEditor.getSelectionModel().getSelectionStart();
        }

        public void setDot(int offset) {
            if (!TranslateEditorComponentImpl.this.myEditor.isDisposed()) {
                TranslateEditorComponentImpl.this.myEditor.getCaretModel().moveToOffset(offset);
            }

        }

        public void moveDot(int offset) {
            if (!TranslateEditorComponentImpl.this.myEditor.isDisposed()) {
                TranslateEditorComponentImpl.this.myEditor.getCaretModel().moveToOffset(offset);
            }

        }
    }

    private class EditorAccessibilityDocument implements javax.swing.text.Document, Element {
        private java.util.List<DocumentListener> myListeners;

        private EditorAccessibilityDocument() {
        }

        @Nullable
        public java.util.List<DocumentListener> getListeners() {
            return this.myListeners;
        }

        public int getLength() {
            return TranslateEditorComponentImpl.this.myEditor.getDocument().getTextLength();
        }

        public void addDocumentListener(DocumentListener documentListener) {
            if (this.myListeners == null) {
                this.myListeners = new ArrayList(2);
            }

            this.myListeners.add(documentListener);
        }

        public void removeDocumentListener(DocumentListener documentListener) {
            if (this.myListeners != null) {
                this.myListeners.remove(documentListener);
            }

        }

        public void addUndoableEditListener(UndoableEditListener undoableEditListener) {
        }

        public void removeUndoableEditListener(UndoableEditListener undoableEditListener) {
        }

        @Nullable
        public Object getProperty(Object o) {
            return null;
        }

        public void putProperty(Object o, Object o1) {
        }

        public void remove(int offset, int length) throws BadLocationException {
            TranslateEditorComponentImpl.this.editDocumentSafely(offset, length, (String) null);
        }

        public void insertString(int offset, String text, AttributeSet attributeSet) throws BadLocationException {
            TranslateEditorComponentImpl.this.editDocumentSafely(offset, 0, text);
        }

        public String getText(final int offset, final int length) throws BadLocationException {
            return (String) ApplicationManager.getApplication().runReadAction(new Computable<String>() {
                public String compute() {
                    return TranslateEditorComponentImpl.this.myEditor.getDocument().getText(new TextRange(offset, offset + length));
                }
            });
        }

        public void getText(int offset, int length, Segment segment) throws BadLocationException {
            char[] s = this.getText(offset, length).toCharArray();
            segment.array = s;
            segment.offset = 0;
            segment.count = s.length;
        }

        @Nullable
        public Position getStartPosition() {
            TranslateEditorComponentImpl.notSupported();
            return null;
        }

        @Nullable
        public Position getEndPosition() {
            TranslateEditorComponentImpl.notSupported();
            return null;
        }

        @Nullable
        public Position createPosition(int i) throws BadLocationException {
            TranslateEditorComponentImpl.notSupported();
            return null;
        }

        public Element[] getRootElements() {
            return new Element[]{this};
        }

        public Element getDefaultRootElement() {
            return this;
        }

        public void render(Runnable runnable) {
            ApplicationManager.getApplication().runReadAction(runnable);
        }

        public javax.swing.text.Document getDocument() {
            return this;
        }

        @Nullable
        public Element getParentElement() {
            return null;
        }

        @Nullable
        public String getName() {
            return null;
        }

        @Nullable
        public AttributeSet getAttributes() {
            return null;
        }

        public int getStartOffset() {
            return 0;
        }

        public int getEndOffset() {
            return this.getLength();
        }

        public int getElementIndex(int i) {
            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
            return document.getLineNumber(i);
        }

        public int getElementCount() {
            com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
            return document.getLineCount();
        }

        public Element getElement(final int i) {
            return new Element() {
                public javax.swing.text.Document getDocument() {
                    return TranslateEditorComponentImpl.EditorAccessibilityDocument.this;
                }

                public Element getParentElement() {
                    return TranslateEditorComponentImpl.EditorAccessibilityDocument.this;
                }

                @Nullable
                public String getName() {
                    return null;
                }

                @Nullable
                public AttributeSet getAttributes() {
                    return null;
                }

                public int getStartOffset() {
                    com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
                    return document.getLineStartOffset(i);
                }

                public int getEndOffset() {
                    com.intellij.openapi.editor.Document document = TranslateEditorComponentImpl.this.myEditor.getDocument();
                    return document.getLineEndOffset(i);
                }

                public int getElementIndex(int ix) {
                    return 0;
                }

                public int getElementCount() {
                    return 0;
                }

                @Nullable
                public Element getElement(int ix) {
                    return null;
                }

                public boolean isLeaf() {
                    return true;
                }
            };
        }

        public boolean isLeaf() {
            return false;
        }
    }
}
