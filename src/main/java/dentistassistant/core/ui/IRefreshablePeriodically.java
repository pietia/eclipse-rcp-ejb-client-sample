package dentistassistant.core.ui;

/**
 * Interface must be implemented in class that objects must be refreshed periodically
 * (mainly by GUI widgets to refresh data in tables)
 *
 * @author pietia
 */
public interface IRefreshablePeriodically {
    /**
     * Method is called when object must be refreshed
     */
    public void doRefreshPeriodically();
}
