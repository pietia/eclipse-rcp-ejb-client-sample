package dentistassistant.core.ui;

import dentistassistant.core.exceptions.NotUniqueJPAEntityException;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

import java.util.Iterator;

/**
 * Abstract class extends {@link TitleAreaDialog} and provides ability
 * to edit or add entities in GUI dialog with validations.
 *
 * @author pietia
 * @param <T> Type of entity
 */
public abstract class AbstractDialogEditor<T> extends TitleAreaDialog {

    private static final String MSG_ERRORS_IN_FORM = "Formularz zawiera błedy:";
    private static final String MSG_CLOSE_FORM_Q = "Czy zamknąć formularz?";
    private static final String MSG_CONFIRM = "Potwierdzenie";
    private static final String MSG_ADD = "Dodaj";
    private static final String MSG_SAVE = "Zapisz";

    protected final DialogMode MODE;
    protected boolean isCorrect;
    protected final T VALUE;
    protected Iterator<T> iterator;

    /**
     * Klazz (type info) used to instantiate validator
     * which is used to validate VALUE entity of T type
     */
    protected final Class<? extends AbstractValidatorMethod<T>> VALIDATOR_CLASS;
    /**
     * @see dentistassistant.core.ui.AbstractDialogEditor.AbstractValidatorMethod
     */
    protected final AbstractValidatorMethod<T> VALIDATOR_METHOD;

    /**
     * Enum describes if dialog is in EDIT or ADD mode
     * (adding new entity or editing entity)
     *
     * @author pietia
     */
    public static enum DialogMode {
        EDIT, ADD,
    }

    /**
     * Abstract class represents validator fired to validate
     * entities of T type
     *
     * @author pietia
     * @param <T> Type of objects that {@link dentistassistant.core.ui.AbstractDialogEditor.AbstractValidatorMethod} can validate
     */
    public static abstract class AbstractValidatorMethod<T> {
        protected String message = "";
        protected T object;
        protected boolean unique = true;
        protected Iterator<T> localIterator;

        /**
         * @return true if entity has valid data (all fields are correct)
         */
        public abstract boolean validate();

        /**
         * @throws dentistassistant.core.exceptions.NotUniqueJPAEntityException
         *          if entity exists in database
         */
        public abstract void validateUnique() throws NotUniqueJPAEntityException;

        /**
         * Sets {@link Iterator} pointed to List<T> of entites in main table/form
         *
         * @param iterator
         */
        public void setIterator(final Iterator<T> iterator) {
            this.localIterator = iterator;
        }

        /**
         * @return true if object is unique
         */
        public boolean isUnique() {
            return unique;
        }

        /**
         * Sets object
         *
         * @param object
         */
        public void setObject(final T object) {
            this.object = object;
        }

        /**
         * Returns error message
         *
         * @return error message
         */
        public String getMessage() {
            return message;
        }
    }

    /**
     * @param shell          RCP window's handler
     * @param validatorClass Klazz (type info) used to instantiate validator
     *                       which is used to validate value passed as parameter
     * @param iterator       iterator got from List<T> of objects in main table/form
     * @param mode           EDIT or ADD mode
     * @param value          entity object (object to edit or "empty" entity to add)
     * @throws RuntimeException when instantiating VALIDATOR_METHOD object failes (InstantiationException or
     *                          IllegalAccessException is thrown by Class#newInstance())
     */
    public AbstractDialogEditor(Shell shell, final Class<? extends AbstractValidatorMethod<T>> validatorClass,
                                final Iterator<T> iterator, final AbstractDialogEditor.DialogMode mode, final T value) {

        super(shell);

        this.VALIDATOR_CLASS = validatorClass; // to be honest - not needed
        this.MODE = mode;
        this.VALUE = value;
        this.isCorrect = false;
        this.iterator = iterator;

        try {
            this.VALIDATOR_METHOD = validatorClass.newInstance();
        } catch (InstantiationException e) {
            //XXX AbstractValidatorMethod<T> has default constructor
            // so it will be always instantiate
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            //XXX AbstractValidatorMethod<T> has default constructor
            // so it will be always instantiate
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates buttons bar
     *
     * @param parent parental component
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        String msg = MSG_SAVE;
        if (MODE == AbstractDialogEditor.DialogMode.ADD) {
            msg = MSG_ADD;
        }
        createButton(parent, IDialogConstants.YES_ID, msg, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
    }

    /**
     * Method fired to handle pressed button (any button)
     * If it wasn't {@link IDialogConstants#CANCEL_ID} button
     * validation is fired using {@link dentistassistant.core.ui.AbstractDialogEditor.AbstractValidatorMethod}
     *
     * @param buttonId button's id
     */
    @Override
    protected void buttonPressed(int buttonId) {

        if (buttonId == IDialogConstants.CANCEL_ID) {
            boolean confirm = MessageDialog.openConfirm(getShell(), MSG_CONFIRM,
                    MSG_CLOSE_FORM_Q);
            if (confirm) {
                setReturnCode(buttonId);
                close();
            }

        } else {
            VALIDATOR_METHOD.setIterator(iterator);
            VALIDATOR_METHOD.setObject(getData());

            if (VALIDATOR_METHOD.validate()) {
                isCorrect = true;
                setReturnCode(buttonId);
                close();
            } else {
                String msg = VALIDATOR_METHOD.getMessage();
                isCorrect = false;
                MessageDialog.openInformation(getShell(), MSG_ERRORS_IN_FORM, msg);
            }
        }
    }

    /**
     * Brings data from dialog's form and creates object of T type
     *
     * @return T object
     */
    protected abstract T getData();

    /**
     * @return VALUE object
     */
    public T getValue() {
        if (isCorrect)
            return VALUE;
        return null;
    }

}