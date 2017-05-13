package models.services.Check;

import java.util.List;

import models.entity.Check;
import models.services.Model.ModelService;
import models.setting.CheckYouSetting;
import play.db.ebean.Model;
import play.libs.F.Option;
import utils.ModelUtil;
import utils.OptionUtil;
import utils.PageUtil;

public class CheckModelService implements ModelService<Check>{
	
	// privateコンストラクタ
	private CheckModelService(){};
	
	// シングルトンserviceオブジェクト
	private static CheckModelService checkModelService = new CheckModelService();
	
	// serviceオブジェクトの使用
	public static CheckModelService use() {
		return checkModelService;
	}
	
	@Override
	public Option<Check> findById(Long id) {
		Option<Long> idOps = OptionUtil.apply(id);
		if(idOps.isDefined()) {
			Model.Finder<Long, Check> find = ModelUtil.getFinder(Check.class);
			return OptionUtil.apply(find.byId(id));
		} 
		return Option.None();
	}
	
	@Override
	public Option<Check> save(Check entry) {
	
		Option<Check> entryOps= OptionUtil.apply(entry);
		if(entryOps.isDefined()) {
			entry.save();
			if(OptionUtil.apply(entry.getId()).isDefined()) {
				return OptionUtil.apply(entry);
			}
		}
		return Option.None();
	}
	
	@Override
	public Option<List<Check>> findWithPage(Integer pageSource) {
		Integer page = PageUtil.rightPage(pageSource);
		Model.Finder<Long, Check> find = ModelUtil.getFinder(Check.class);
		return OptionUtil.apply(
				find.order()
					.asc("created")
					.findPagingList(CheckYouSetting.LIMIT)
					.getPage(page)
					.getList()
				);
	}
	
	public Option<Integer> getMaxPage() {
		Model.Finder<Long, Check> find = ModelUtil.getFinder(Check.class);
		return OptionUtil.apply(
				find.order()
					.asc("created")
					.findPagingList(CheckYouSetting.LIMIT)
					.getTotalPageCount()
				);
	}
}
