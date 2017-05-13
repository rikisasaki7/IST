import play.GlobalSettings;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;

/**
 * Globalクラス
 */
public class Global extends GlobalSettings {

    /**
     * CSRFフィルター
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    public <T extends EssentialFilter> Class<T>[] filters() {
        return new Class[]{
                CSRFFilter.class
        };
    }

}
