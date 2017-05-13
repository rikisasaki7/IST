package models.services.Model;

import java.util.List;

import play.db.ebean.Model;
import play.libs.F.Option;

public interface ModelService<T extends Model> {

	public Option<T> findById(Long id);
	public Option<T> save(T entity);
	public Option<List<T>> findWithPage(Integer pageSource);
}
