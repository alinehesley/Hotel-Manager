package Classes.helpers;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Utils {
    public interface MouseClickEvent {
        void mouseClicked(MouseEvent e);
    }

    public interface DocumentModifyEvent {
        void update(DocumentEvent e);
    }

    public static MouseAdapter fromMouseClick(MouseClickEvent ev) {
        return new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ev.mouseClicked(e);
            }
        };
    }

    public static DocumentListener fromDocumentModify(DocumentModifyEvent ev) {
        return new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                ev.update(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ev.update(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                ev.update(e);
            }
        };
    }

    public static void copyToClipboard(String text) {
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        StringSelection selection = new StringSelection(text);
        clipboard.setContents(selection, selection);
    }
}
