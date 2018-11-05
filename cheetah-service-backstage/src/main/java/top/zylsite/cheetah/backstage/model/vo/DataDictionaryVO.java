package top.zylsite.cheetah.backstage.model.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import top.zylsite.cheetah.backstage.model.master.DataDictionary;

@Data
@EqualsAndHashCode(callSuper = false)
public class DataDictionaryVO extends DataDictionary {

	private static final long serialVersionUID = 3553073411399516295L;
	
	private String dictTypeName;
	private String orderByClause;

}
