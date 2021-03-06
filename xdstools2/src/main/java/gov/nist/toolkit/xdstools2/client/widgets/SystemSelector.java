package gov.nist.toolkit.xdstools2.client.widgets;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Present systems for selection
 */
abstract public class SystemSelector extends ButtonListSelector implements IsWidget {

    public SystemSelector(String title) {
        super(title);
    }

    public SystemSelector(String title, String style) {
        super(title, style);
    }

    public void clearSelection() {
        setCurrentSelection(null);
    }

}
