package dentistassistant.core.ui;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

/**
 * Utility class contains methods to display message dialogs.
 *
 * @author pietia
 */
final public class MessageHelper {

    private static final String CONNECTION_PROBLEM = "Problem z połączeniem do serwera!";
    private static final String ERROR = "Błąd";
    private static final String WARNING = "Ostrzeżenie";

    /**
     * Private constructor prevents from instantiate this class
     */
    private MessageHelper() {
    }

    /**
     * Shows message dialog. Should be used in general situations.
     *
     * @param title window title
     * @param txt   text to display
     */
    public static void messageDialogError(String title, String txt) {
        if (title == null)
            title = "";
        if (txt == null)
            txt = "";

        MessageDialog.openError(
                PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, txt);
        // NON-NLS-1
    }

    /**
     * Shows connection error (server probably is down).
     */
    public static void messageDialogErrorServerIsDown() {
        messageDialogError(ERROR, CONNECTION_PROBLEM);
    }

    /**
     * Shows information about not passed validation.
     *
     * @param txt text to display
     */
    public static void messageDialogWarningValidationNotPassed(String txt) {
        messageDialogError(WARNING, txt);
    }

}
