package top.zylsite.cheetah.backstage.service.master;
 
import top.zylsite.cheetah.base.common.BaseService;
import top.zylsite.cheetah.backstage.model.master.UserBindInfo;

public interface IUserBindInfoService extends BaseService<UserBindInfo>{
 
	/**
	 * 查询第三方账号是否已存在，不存在插入一条，最后返回记录ID
	 * @param account
	 * @param type
	 * @return
	 */
	public Integer insertIfNotExist(UserBindInfo bindInfo);
	
	/**
	 * 查询第三方账号是否已经绑定用户信息，如果绑定了则返回用户ID
	 * @param accountId
	 * @return
	 */
	public Integer hasBindingUser(int accountId);
	
	/**
	 * 第三方账号绑定
	 * @param accountId
	 * @param userId
	 */
	public void bindUser(int accountId, int userId);
	
	/**
	 * 查看某个用户是否绑定了同类型的账号
	 * @param userId
	 * @param type
	 * @return
	 */
	public boolean hasBindSameTypeAccount(int userId, String type);
	
	/**
	 * 根据用户ID和账号类型查询绑定信息
	 * @param userId
	 * @param type
	 * @return
	 */
	public UserBindInfo queryByUserId(int userId, String type);
	
}