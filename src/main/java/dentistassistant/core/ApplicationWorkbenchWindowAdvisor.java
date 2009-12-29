package dentistassistant.core;

import dentistassistant.core.ui.IRefreshablePeriodically;
import org.apache.log4j.Logger;
import org.eclipse.swt.graphics.Point;
import org.eclipse.ui.*;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * RCP class
 *
 * @author pietia
 * @see org.eclipse.ui.application.WorkbenchWindowAdvisor
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

    private static final Logger LOG = Logger.getLogger(ApplicationWorkbenchWindowAdvisor.class);

    /**
     * {@link IPerspectiveListener} implementation that refreshes data in View objects
     * that implements IRefreshablePeriodically interface
     *
     * @author pietia
     * @see IRefreshablePeriodically
     */
    private final static class PerspectiveListenerForDentistAssistant implements IPerspectiveListener {

        private static final String REG_PLUGINS = "^dentistassistant.*";

        /**
         * Method that is invoked when perspective is switched
         *
         * @param page
         * @param perspective
         * @see IRefreshablePeriodically
         * @see IPerspectiveListener#perspectiveActivated(IWorkbenchPage, IPerspectiveDescriptor)
         */
        @Override
        public void perspectiveActivated(IWorkbenchPage page, IPerspectiveDescriptor perspective) {

            if (perspective.getId().matches(REG_PLUGINS)) {
                IViewReference[] refs = page.getViewReferences();
                for (IViewReference i : refs) {
                    LOG.debug("ViewReference's id:" + i.getId());
                    IViewPart view = i.getView(true);
                    if (view instanceof IRefreshablePeriodically) {
                        ((IRefreshablePeriodically) view).doRefreshPeriodically();
                    }
                }
            } else {
                LOG.debug("Perspective's name doesn't match: " + perspective.getId());
            }
        }

        /**
         * Stub method
         *
         * @param page
         * @param perspective
         * @param changeId
         * @see IPerspectiveListener#perspectiveChanged(IWorkbenchPage, IPerspectiveDescriptor, String)
         */
        @Override
        public void perspectiveChanged(IWorkbenchPage page, IPerspectiveDescriptor perspective,
                                       String changeId) {
            LOG.debug("Perspective changed");
        }
    }

    /**
     * Constructor passing {@link IWorkbenchWindowConfigurer} object to super class
     *
     * @param configurer
     * @see ApplicationWorkbenchWindowAdvisor
     * @see IWorkbenchWindowConfigurer
     */
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
        super(configurer);
    }

    /**
     * Method returns ActionBarAdvisor object
     *
     * @return ActionBarAdvisor object
     * @see WorkbenchWindowAdvisor#createActionBarAdvisor(IActionBarConfigurer)
     */
    @Override
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer) {
        return new ApplicationActionBarAdvisor(configurer);
    }

    /**
     * Callback method that is fired before window is opened
     *
     * @see WorkbenchWindowAdvisor#preWindowOpen()
     */
    @Override
    public void preWindowOpen() {

        applyWindowsPreferences();

        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        configurer.setInitialSize(new Point(1024, 768));
        configurer.setShowCoolBar(true);
        // configurer.setShowFastViewBars(true);
        configurer.setShowMenuBar(true);
        configurer.setShowPerspectiveBar(true);
        // configurer.setShowStatusLine(true);
        // configurer.setShowProgressIndicator(true);

    }

    /**
     * Callback method that is fired after window is opened
     *
     * @see WorkbenchWindowAdvisor#postWindowClose()
     */
    @Override
    public void postWindowOpen() {

        addPerspectiveListener();
        super.postWindowOpen();
    }

    /**
     * Helper method applying windows preferences
     */
    private void applyWindowsPreferences() {

        PlatformUI.getPreferenceStore().setValue(
                IWorkbenchPreferenceConstants.DOCK_PERSPECTIVE_BAR,
                IWorkbenchPreferenceConstants.TOP_RIGHT);

        PlatformUI.getPreferenceStore().setValue(
                IWorkbenchPreferenceConstants.SHOW_TEXT_ON_PERSPECTIVE_BAR, "FALSE");

        PlatformUI.getPreferenceStore().setValue(
                IWorkbenchPreferenceConstants.SHOW_OPEN_ON_PERSPECTIVE_BAR, "TRUE");

        PlatformUI.getPreferenceStore().setValue(
                IWorkbenchPreferenceConstants.PROMPT_WHEN_SAVEABLE_STILL_OPEN, "TRUE");
    }

    /**
     * Helper method adding perspective listener
     */
    private void addPerspectiveListener() {
        PlatformUI.getWorkbench().getActiveWorkbenchWindow().addPerspectiveListener(
                new PerspectiveListenerForDentistAssistant());
    }

}
