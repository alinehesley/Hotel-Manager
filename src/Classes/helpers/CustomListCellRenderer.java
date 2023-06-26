package Classes.helpers;

import javax.swing.*;
import java.awt.*;

public abstract class CustomListCellRenderer extends DefaultListCellRenderer {
    public abstract String getDisplayText(Object value);

    @Override
    public Component getListCellRendererComponent(
            JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        String displayText = getDisplayText(value);
        return super.getListCellRendererComponent(list, displayText, index, isSelected, cellHasFocus);
    }
}
